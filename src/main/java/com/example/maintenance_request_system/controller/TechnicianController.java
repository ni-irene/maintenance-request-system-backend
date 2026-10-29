package com.example.maintenance_request_system.controller;

import com.example.maintenance_request_system.entity.Technician;
import com.example.maintenance_request_system.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {

    @Autowired
    TechnicianService technicianService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Technician technician) {
        String message = technicianService.save(technician);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Technician>> findAll() {
        return ResponseEntity.ok(technicianService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Technician> technician = technicianService.findById(id);
        if (technician.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The technician with that id does not exist");
        }
        return ResponseEntity.ok(technician.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Technician technician) {
        String message = technicianService.update(id, technician);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = technicianService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-trade")
    public ResponseEntity<List<Technician>> findByTrade(@RequestParam String name) {
        return ResponseEntity.ok(technicianService.findByTradeName(name));
    }

    @PutMapping("/{technicianId}/trades/{tradeId}")
    public ResponseEntity<String> addTrade(@PathVariable UUID technicianId, @PathVariable UUID tradeId) {
        String message = technicianService.addTrade(technicianId, tradeId);
        if (message.equals("The technician with that id does not exist") || message.equals("That trade does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("The technician already has that trade")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{technicianId}/building")
    public ResponseEntity<String> changeBuilding(@PathVariable UUID technicianId,
                                                 @RequestParam int buildingNumber) {
        String message = technicianService.changeBuilding(technicianId, buildingNumber);
        if (message.equals("The technician with that id does not exist")
                || message.equals("The building with that buildingNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("That building is taken by another technician")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
