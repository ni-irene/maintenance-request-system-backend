package com.example.maintenance_request_system.controller;

import com.example.maintenance_request_system.entity.Requester;
import com.example.maintenance_request_system.service.RequesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/requesters")
public class RequesterController {

    @Autowired
    RequesterService requesterService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Requester requester) {
        String message = requesterService.save(requester);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Requester>> findAll() {
        return ResponseEntity.ok(requesterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Requester> requester = requesterService.findById(id);
        if (requester.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requester with that id does not exist");
        }
        return ResponseEntity.ok(requester.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Requester requester) {
        String message = requesterService.update(id, requester);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = requesterService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-lastname")
    public ResponseEntity<List<Requester>> findByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(requesterService.findByLastName(lastName));
    }

    @GetMapping("/of-technician/{technicianId}")
    public ResponseEntity<?> findByTechnician(@PathVariable UUID technicianId) {
        String error = requesterService.checkTechnicianExists(technicianId);
        if (!error.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        List<Requester> requesters = requesterService.getRequestersByTechnician(technicianId);
        return ResponseEntity.ok(requesters);
    }
}
