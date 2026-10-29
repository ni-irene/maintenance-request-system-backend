package com.example.maintenance_request_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "repair_record")
public class RepairRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String partReplaced;
    private String workDone;
    private int hoursSpent;
    private LocalDate recordDate;

    @ManyToOne
    @JoinColumn(name = "maintenance_request_id")
    private MaintenanceRequest maintenanceRequest;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPartReplaced() {
        return partReplaced;
    }

    public void setPartReplaced(String partReplaced) {
        this.partReplaced = partReplaced;
    }

    public String getWorkDone() {
        return workDone;
    }

    public void setWorkDone(String workDone) {
        this.workDone = workDone;
    }

    public int getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(int hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public MaintenanceRequest getMaintenanceRequest() {
        return maintenanceRequest;
    }

    public void setMaintenanceRequest(MaintenanceRequest maintenanceRequest) {
        this.maintenanceRequest = maintenanceRequest;
    }
}
