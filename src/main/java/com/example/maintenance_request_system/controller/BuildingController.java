package com.example.maintenance_request_system.controller;

import com.example.maintenance_request_system.entity.Building;
import com.example.maintenance_request_system.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Building building) {
        String message = buildingService.save(building);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Building>> findAll() {
        return ResponseEntity.ok(buildingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Building> building = buildingService.findById(id);
        if (building.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The building with that id does not exist");
        }
        return ResponseEntity.ok(building.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Building building) {
        String message = buildingService.update(id, building);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = buildingService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/free")
    public ResponseEntity<List<Building>> findFreeBuildings() {
        return ResponseEntity.ok(buildingService.findFreeBuildings());
    }
}
