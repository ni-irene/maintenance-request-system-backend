package com.example.maintenance_request_system.controller;

import com.example.maintenance_request_system.entity.RepairRecord;
import com.example.maintenance_request_system.service.RepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/repair-records")
public class RepairRecordController {

    @Autowired
    RepairRecordService repairRecordService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID maintenanceRequestId,
                                       @RequestBody RepairRecord record) {
        String message = repairRecordService.save(maintenanceRequestId, record);
        if (message.equals("The maintenance request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("Repair records are only for RESOLVED maintenance requests")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RepairRecord>> findAll() {
        return ResponseEntity.ok(repairRecordService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<RepairRecord> record = repairRecordService.findById(id);
        if (record.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The repair record with that id does not exist");
        }
        return ResponseEntity.ok(record.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody RepairRecord record) {
        String message = repairRecordService.update(id, record);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = repairRecordService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/requester/{staffNumber}")
    public ResponseEntity<List<RepairRecord>> findByRequester(@PathVariable String staffNumber) {
        return ResponseEntity.ok(repairRecordService.findByRequesterStaffNumber(staffNumber));
    }
}
