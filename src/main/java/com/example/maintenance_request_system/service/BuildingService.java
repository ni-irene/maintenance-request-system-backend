package com.example.maintenance_request_system.service;

import com.example.maintenance_request_system.entity.Building;
import com.example.maintenance_request_system.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;

    public String save(Building building) {
        buildingRepository.save(building);
        return "Building saved successfully";
    }

    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    public Optional<Building> findById(UUID id) {
        return buildingRepository.findById(id);
    }

    public String update(UUID id, Building updated) {
        Optional<Building> existing = buildingRepository.findById(id);
        if (existing.isEmpty()) {
            return "The building with that id does not exist";
        }
        Building building = existing.get();
        building.setBuildingNumber(updated.getBuildingNumber());
        building.setName(updated.getName());
        buildingRepository.save(building);
        return "Building updated successfully";
    }

    public String delete(UUID id) {
        Optional<Building> existing = buildingRepository.findById(id);
        if (existing.isEmpty()) {
            return "The building with that id does not exist";
        }
        buildingRepository.deleteById(id);
        return "Building deleted successfully";
    }

    public List<Building> findFreeBuildings() {
        return buildingRepository.findFreeBuildings();
    }
}
