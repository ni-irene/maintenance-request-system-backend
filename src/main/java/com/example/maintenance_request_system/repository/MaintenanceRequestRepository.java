package com.example.maintenance_request_system.repository;

import com.example.maintenance_request_system.entity.MaintenanceRequest;
import com.example.maintenance_request_system.entity.RequestStatus;
import com.example.maintenance_request_system.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID> {

    List<MaintenanceRequest> findByStatusOrderByRequestDateAsc(RequestStatus status);

    List<MaintenanceRequest> findByRequestDateBetweenOrderByRequestDateAsc(LocalDate start, LocalDate end);

    boolean existsByTechnicianAndRequestDateAndStatusNot(Technician technician, LocalDate requestDate, RequestStatus status);

    @Query("SELECT mr.status, COUNT(mr) FROM MaintenanceRequest mr GROUP BY mr.status")
    List<Object[]> countByStatus();

    @Modifying
    @Query("UPDATE MaintenanceRequest mr SET mr.status = com.example.maintenance_request_system.entity.RequestStatus.REJECTED " +
            "WHERE mr.technician.id = :technicianId AND mr.requestDate = :date " +
            "AND mr.status <> com.example.maintenance_request_system.entity.RequestStatus.RESOLVED")
    int cancelDay(@Param("technicianId") UUID technicianId, @Param("date") LocalDate date);
}
