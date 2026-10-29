package com.example.maintenance_request_system.service;

import com.example.maintenance_request_system.entity.MaintenanceRequest;
import com.example.maintenance_request_system.entity.RepairRecord;
import com.example.maintenance_request_system.entity.RequestStatus;
import com.example.maintenance_request_system.repository.MaintenanceRequestRepository;
import com.example.maintenance_request_system.repository.RepairRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RepairRecordService {

    @Autowired
    RepairRecordRepository repairRecordRepository;

    @Autowired
    MaintenanceRequestRepository maintenanceRequestRepository;

    public String save(UUID maintenanceRequestId, RepairRecord record) {
        Optional<MaintenanceRequest> requestOpt = maintenanceRequestRepository.findById(maintenanceRequestId);
        if (requestOpt.isEmpty()) {
            return "The maintenance request with that id does not exist";
        }
        MaintenanceRequest mr = requestOpt.get();
        if (mr.getStatus() != RequestStatus.RESOLVED) {
            return "Repair records are only for RESOLVED maintenance requests";
        }
        record.setRecordDate(LocalDate.now());
        record.setMaintenanceRequest(mr);
        repairRecordRepository.save(record);
        return "Repair record saved successfully";
    }

    public List<RepairRecord> findAll() {
        return repairRecordRepository.findAll();
    }

    public Optional<RepairRecord> findById(UUID id) {
        return repairRecordRepository.findById(id);
    }

    public String update(UUID id, RepairRecord updated) {
        Optional<RepairRecord> existing = repairRecordRepository.findById(id);
        if (existing.isEmpty()) {
            return "The repair record with that id does not exist";
        }
        RepairRecord record = existing.get();
        record.setPartReplaced(updated.getPartReplaced());
        record.setWorkDone(updated.getWorkDone());
        record.setHoursSpent(updated.getHoursSpent());
        repairRecordRepository.save(record);
        return "Repair record updated successfully";
    }

    public String delete(UUID id) {
        Optional<RepairRecord> existing = repairRecordRepository.findById(id);
        if (existing.isEmpty()) {
            return "The repair record with that id does not exist";
        }
        repairRecordRepository.deleteById(id);
        return "Repair record deleted successfully";
    }

    public List<RepairRecord> findByRequesterStaffNumber(String staffNumber) {
        return repairRecordRepository.findByRequesterStaffNumber(staffNumber);
    }
}
