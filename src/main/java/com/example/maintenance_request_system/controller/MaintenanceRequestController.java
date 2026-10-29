package com.example.maintenance_request_system.controller;

import com.example.maintenance_request_system.entity.MaintenanceRequest;
import com.example.maintenance_request_system.entity.RequestStatus;
import com.example.maintenance_request_system.service.MaintenanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/maintenance-requests")
public class MaintenanceRequestController {

    @Autowired
    MaintenanceRequestService maintenanceRequestService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody MaintenanceRequest request) {
        String message = maintenanceRequestService.save(request);
        if (message.equals("Maintenance request date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if (message.equals("Technician already has a request assigned on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MaintenanceRequest>> findAll() {
        return ResponseEntity.ok(maintenanceRequestService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<MaintenanceRequest> request = maintenanceRequestService.findById(id);
        if (request.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The maintenance request with that id does not exist");
        }
        return ResponseEntity.ok(request.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody MaintenanceRequest request) {
        String message = maintenanceRequestService.update(id, request);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = maintenanceRequestService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.contains("repair records")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<MaintenanceRequest>> findByStatus(@RequestParam RequestStatus status) {
        return ResponseEntity.ok(maintenanceRequestService.findByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<MaintenanceRequest>> findBetween(@RequestParam String start,
                                                                @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(maintenanceRequestService.findBetweenDates(startDate, endDate));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam RequestStatus status) {
        String message = maintenanceRequestService.updateStatus(id, status);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("Cannot change status")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> statsByStatus() {
        return ResponseEntity.ok(maintenanceRequestService.countByStatus());
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID technicianId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        String message = maintenanceRequestService.cancelDay(technicianId, localDate);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<MaintenanceRequest>> findPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "requestDate,desc") String sort) {
        String[] parts = sort.split(",");
        String field = parts[0];
        Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return ResponseEntity.ok(maintenanceRequestService.findPage(pageable));
    }
}
