package com.example.maintenance_request_system.repository;

import com.example.maintenance_request_system.entity.Building;
import com.example.maintenance_request_system.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TechnicianRepository extends JpaRepository<Technician, UUID> {

    @Query("SELECT t FROM Technician t JOIN t.trades c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Technician> findByTradeName(@Param("name") String name);

    boolean existsByBuilding(Building building);
}
