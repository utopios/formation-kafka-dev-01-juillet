package com.example.trackingappconsumer.repository;

import com.example.trackingappconsumer.entity.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, String> {
}
