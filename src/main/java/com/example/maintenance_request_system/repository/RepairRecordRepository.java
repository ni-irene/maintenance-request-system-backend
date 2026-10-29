package com.example.maintenance_request_system.repository;

import com.example.maintenance_request_system.entity.MaintenanceRequest;
import com.example.maintenance_request_system.entity.RepairRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RepairRecordRepository extends JpaRepository<RepairRecord, UUID> {

    @Query("SELECT r FROM RepairRecord r WHERE r.maintenanceRequest.requester.staffNumber = :staffNumber ORDER BY r.recordDate DESC")
    List<RepairRecord> findByRequesterStaffNumber(@Param("staffNumber") String staffNumber);

    boolean existsByMaintenanceRequest(MaintenanceRequest maintenanceRequest);
}
