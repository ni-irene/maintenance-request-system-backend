package com.example.maintenance_request_system.repository;

import com.example.maintenance_request_system.entity.Requester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RequesterRepository extends JpaRepository<Requester, UUID> {

    List<Requester> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT mr.requester FROM MaintenanceRequest mr WHERE mr.technician.id = :technicianId")
    List<Requester> findRequestersByTechnicianId(@Param("technicianId") UUID technicianId);
}
