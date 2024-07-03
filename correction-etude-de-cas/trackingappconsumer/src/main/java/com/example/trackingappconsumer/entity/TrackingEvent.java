package com.example.trackingappconsumer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TrackingEvent {
    @Id
    private String idColis;
    private String timestampScan;
    private String lieuScan;
    private String etatColis;
}


