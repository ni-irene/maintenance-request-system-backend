package com.example.maintenance_request_system.service;

import com.example.maintenance_request_system.entity.RequestStatus;
import com.example.maintenance_request_system.repository.BuildingRepository;
import com.example.maintenance_request_system.repository.MaintenanceRequestRepository;
import com.example.maintenance_request_system.repository.RepairRecordRepository;
import com.example.maintenance_request_system.repository.RequesterRepository;
import com.example.maintenance_request_system.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    RequesterRepository requesterRepository;

    @Autowired
    TechnicianRepository technicianRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    MaintenanceRequestRepository maintenanceRequestRepository;

    @Autowired
    RepairRecordRepository repairRecordRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("requesters", requesterRepository.count());
        summary.put("technicians", technicianRepository.count());
        summary.put("freeBuildings", (long) buildingRepository.findFreeBuildings().size());
        summary.put("requestsToday", (long) maintenanceRequestRepository
                .findByRequestDateBetweenOrderByRequestDateAsc(LocalDate.now(), LocalDate.now()).size());
        summary.put("submitted", (long) maintenanceRequestRepository
                .findByStatusOrderByRequestDateAsc(RequestStatus.SUBMITTED).size());
        summary.put("repairRecords", repairRecordRepository.count());
        return summary;
    }
}
