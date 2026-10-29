package com.example.maintenance_request_system.service;

import com.example.maintenance_request_system.entity.MaintenanceRequest;
import com.example.maintenance_request_system.entity.RequestStatus;
import com.example.maintenance_request_system.repository.MaintenanceRequestRepository;
import com.example.maintenance_request_system.repository.RepairRecordRepository;
import com.example.maintenance_request_system.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaintenanceRequestService {

    @Autowired
    MaintenanceRequestRepository maintenanceRequestRepository;

    @Autowired
    TechnicianRepository technicianRepository;

    @Autowired
    RepairRecordRepository repairRecordRepository;

    public String save(MaintenanceRequest request) {
        if (request.getRequestDate().isBefore(LocalDate.now())) {
            return "Maintenance request date cannot be in the past";
        }
        if (request.getTechnician() != null) {
            boolean conflict = maintenanceRequestRepository
                    .existsByTechnicianAndRequestDateAndStatusNot(
                            request.getTechnician(),
                            request.getRequestDate(),
                            RequestStatus.REJECTED);
            if (conflict) {
                return "Technician already has a request assigned on that date";
            }
        }
        request.setStatus(RequestStatus.SUBMITTED);
        maintenanceRequestRepository.save(request);
        return "Maintenance request saved successfully";
    }

    public List<MaintenanceRequest> findAll() {
        return maintenanceRequestRepository.findAll();
    }

    public Optional<MaintenanceRequest> findById(UUID id) {
        return maintenanceRequestRepository.findById(id);
    }

    public String update(UUID id, MaintenanceRequest updated) {
        Optional<MaintenanceRequest> existing = maintenanceRequestRepository.findById(id);
        if (existing.isEmpty()) {
            return "The maintenance request with that id does not exist";
        }
        MaintenanceRequest request = existing.get();
        request.setRequestDate(updated.getRequestDate());
        request.setLocation(updated.getLocation());
        request.setPriority(updated.getPriority());
        request.setRequester(updated.getRequester());
        request.setTechnician(updated.getTechnician());
        maintenanceRequestRepository.save(request);
        return "Maintenance request updated successfully";
    }

    public String delete(UUID id) {
        Optional<MaintenanceRequest> existing = maintenanceRequestRepository.findById(id);
        if (existing.isEmpty()) {
            return "The maintenance request with that id does not exist";
        }
        if (repairRecordRepository.existsByMaintenanceRequest(existing.get())) {
            return "That maintenance request has repair records, delete them first";
        }
        maintenanceRequestRepository.deleteById(id);
        return "Maintenance request deleted successfully";
    }

    public List<MaintenanceRequest> findByStatus(RequestStatus status) {
        return maintenanceRequestRepository.findByStatusOrderByRequestDateAsc(status);
    }

    public List<MaintenanceRequest> findBetweenDates(LocalDate start, LocalDate end) {
        return maintenanceRequestRepository.findByRequestDateBetweenOrderByRequestDateAsc(start, end);
    }

    public String updateStatus(UUID id, RequestStatus newStatus) {
        Optional<MaintenanceRequest> existing = maintenanceRequestRepository.findById(id);
        if (existing.isEmpty()) {
            return "The maintenance request with that id does not exist";
        }
        MaintenanceRequest request = existing.get();
        RequestStatus current = request.getStatus();
        boolean allowed = false;
        if (current == RequestStatus.SUBMITTED) {
            if (newStatus == RequestStatus.ASSIGNED || newStatus == RequestStatus.REJECTED) {
                allowed = true;
            }
        } else if (current == RequestStatus.ASSIGNED) {
            if (newStatus == RequestStatus.RESOLVED || newStatus == RequestStatus.REJECTED) {
                allowed = true;
            }
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        request.setStatus(newStatus);
        maintenanceRequestRepository.save(request);
        return "Maintenance request status updated successfully";
    }

    public List<Object[]> countByStatus() {
        return maintenanceRequestRepository.countByStatus();
    }

    @Transactional
    public String cancelDay(UUID technicianId, LocalDate date) {
        if (!technicianRepository.existsById(technicianId)) {
            return "The technician with that id does not exist";
        }
        int count = maintenanceRequestRepository.cancelDay(technicianId, date);
        return count + " maintenance requests cancelled";
    }

    public Page<MaintenanceRequest> findPage(Pageable pageable) {
        return maintenanceRequestRepository.findAll(pageable);
    }
}
