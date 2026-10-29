package com.example.maintenance_request_system.repository;

import com.example.maintenance_request_system.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TradeRepository extends JpaRepository<Trade, UUID> {

    @Query("SELECT t FROM Trade t WHERE t.technicians IS EMPTY")
    List<Trade> findUnusedTrades();
}
