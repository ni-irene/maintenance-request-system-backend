package com.example.maintenance_request_system.service;

import com.example.maintenance_request_system.entity.Requester;
import com.example.maintenance_request_system.repository.RequesterRepository;
import com.example.maintenance_request_system.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequesterService {

    @Autowired
    RequesterRepository requesterRepository;

    @Autowired
    TechnicianRepository technicianRepository;

    public String save(Requester requester) {
        requesterRepository.save(requester);
        return "Requester saved successfully";
    }

    public List<Requester> findAll() {
        return requesterRepository.findAll();
    }

    public Optional<Requester> findById(UUID id) {
        return requesterRepository.findById(id);
    }

    public String update(UUID id, Requester updated) {
        Optional<Requester> existing = requesterRepository.findById(id);
        if (existing.isEmpty()) {
            return "The requester with that id does not exist";
        }
        Requester requester = existing.get();
        requester.setFirstName(updated.getFirstName());
        requester.setLastName(updated.getLastName());
        requester.setStaffNumber(updated.getStaffNumber());
        requester.setEmail(updated.getEmail());
        requester.setPhone(updated.getPhone());
        requesterRepository.save(requester);
        return "Requester updated successfully";
    }

    public String delete(UUID id) {
        Optional<Requester> existing = requesterRepository.findById(id);
        if (existing.isEmpty()) {
            return "The requester with that id does not exist";
        }
        requesterRepository.deleteById(id);
        return "Requester deleted successfully";
    }

    public List<Requester> findByLastName(String lastName) {
        return requesterRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public String checkTechnicianExists(UUID technicianId) {
        if (!technicianRepository.existsById(technicianId)) {
            return "The technician with that id does not exist";
        }
        return "";
    }

    public List<Requester> getRequestersByTechnician(UUID technicianId) {
        return requesterRepository.findRequestersByTechnicianId(technicianId);
    }
}
