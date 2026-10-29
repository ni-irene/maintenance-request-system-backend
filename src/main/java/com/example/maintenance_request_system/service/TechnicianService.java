package com.example.maintenance_request_system.service;

import com.example.maintenance_request_system.entity.Building;
import com.example.maintenance_request_system.entity.Technician;
import com.example.maintenance_request_system.entity.Trade;
import com.example.maintenance_request_system.repository.BuildingRepository;
import com.example.maintenance_request_system.repository.TechnicianRepository;
import com.example.maintenance_request_system.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TechnicianService {

    @Autowired
    TechnicianRepository technicianRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    TradeRepository tradeRepository;

    public String save(Technician technician) {
        technicianRepository.save(technician);
        return "Technician saved successfully";
    }

    public List<Technician> findAll() {
        return technicianRepository.findAll();
    }

    public Optional<Technician> findById(UUID id) {
        return technicianRepository.findById(id);
    }

    public String update(UUID id, Technician updated) {
        Optional<Technician> existing = technicianRepository.findById(id);
        if (existing.isEmpty()) {
            return "The technician with that id does not exist";
        }
        Technician technician = existing.get();
        technician.setFirstName(updated.getFirstName());
        technician.setLastName(updated.getLastName());
        technician.setPhone(updated.getPhone());
        technician.setBuilding(updated.getBuilding());
        technicianRepository.save(technician);
        return "Technician updated successfully";
    }

    public String delete(UUID id) {
        Optional<Technician> existing = technicianRepository.findById(id);
        if (existing.isEmpty()) {
            return "The technician with that id does not exist";
        }
        technicianRepository.deleteById(id);
        return "Technician deleted successfully";
    }

    public List<Technician> findByTradeName(String name) {
        return technicianRepository.findByTradeName(name);
    }

    public String addTrade(UUID technicianId, UUID tradeId) {
        Optional<Technician> techOpt = technicianRepository.findById(technicianId);
        if (techOpt.isEmpty()) {
            return "The technician with that id does not exist";
        }
        Optional<Trade> tradeOpt = tradeRepository.findById(tradeId);
        if (tradeOpt.isEmpty()) {
            return "That trade does not exist";
        }
        Technician technician = techOpt.get();
        Trade trade = tradeOpt.get();
        if (technician.getTrades().contains(trade)) {
            return "The technician already has that trade";
        }
        technician.getTrades().add(trade);
        technicianRepository.save(technician);
        return "Trade added to the technician";
    }

    public String changeBuilding(UUID technicianId, int buildingNumber) {
        Optional<Technician> techOpt = technicianRepository.findById(technicianId);
        if (techOpt.isEmpty()) {
            return "The technician with that id does not exist";
        }
        Optional<Building> buildingOpt = buildingRepository.findByBuildingNumber(buildingNumber);
        if (buildingOpt.isEmpty()) {
            return "The building with that buildingNumber does not exist";
        }
        Technician technician = techOpt.get();
        Building newBuilding = buildingOpt.get();
        UUID currentBuildingId = technician.getBuilding() != null ? technician.getBuilding().getId() : null;
        if (technicianRepository.existsByBuilding(newBuilding) && !newBuilding.getId().equals(currentBuildingId)) {
            return "That building is taken by another technician";
        }
        technician.setBuilding(newBuilding);
        technicianRepository.save(technician);
        return "Building changed successfully";
    }
}
