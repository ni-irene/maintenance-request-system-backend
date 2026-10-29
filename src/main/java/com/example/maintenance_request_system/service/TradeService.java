package com.example.maintenance_request_system.service;

import com.example.maintenance_request_system.entity.Trade;
import com.example.maintenance_request_system.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    public String save(Trade trade) {
        tradeRepository.save(trade);
        return "Trade saved successfully";
    }

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> findById(UUID id) {
        return tradeRepository.findById(id);
    }

    public String update(UUID id, Trade updated) {
        Optional<Trade> existing = tradeRepository.findById(id);
        if (existing.isEmpty()) {
            return "The trade with that id does not exist";
        }
        Trade trade = existing.get();
        trade.setName(updated.getName());
        tradeRepository.save(trade);
        return "Trade updated successfully";
    }

    public String delete(UUID id) {
        Optional<Trade> existing = tradeRepository.findById(id);
        if (existing.isEmpty()) {
            return "The trade with that id does not exist";
        }
        tradeRepository.deleteById(id);
        return "Trade deleted successfully";
    }

    public List<Trade> findUnusedTrades() {
        return tradeRepository.findUnusedTrades();
    }
}
