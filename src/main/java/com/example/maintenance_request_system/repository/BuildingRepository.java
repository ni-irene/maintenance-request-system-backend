package com.example.maintenance_request_system.repository;

import com.example.maintenance_request_system.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BuildingRepository extends JpaRepository<Building, UUID> {

    Optional<Building> findByBuildingNumber(int buildingNumber);

    @Query("SELECT b FROM Building b WHERE NOT EXISTS (SELECT t FROM Technician t WHERE t.building = b) ORDER BY b.buildingNumber ASC")
    List<Building> findFreeBuildings();
}
