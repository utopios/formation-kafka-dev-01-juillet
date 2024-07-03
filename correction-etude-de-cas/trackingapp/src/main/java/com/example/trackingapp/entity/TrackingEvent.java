package com.example.trackingapp.entity;

import lombok.Data;

@Data
public class TrackingEvent {
    private String idColis;
    private String timestampScan;
    private String lieuScan;
    private String etatColis;
}
