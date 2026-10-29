package com.example.maintenance_request_system.controller;

import com.example.maintenance_request_system.entity.Trade;
import com.example.maintenance_request_system.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    TradeService tradeService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Trade trade) {
        String message = tradeService.save(trade);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trade>> findAll() {
        return ResponseEntity.ok(tradeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Trade> trade = tradeService.findById(id);
        if (trade.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The trade with that id does not exist");
        }
        return ResponseEntity.ok(trade.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Trade trade) {
        String message = tradeService.update(id, trade);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = tradeService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Trade>> findUnused() {
        return ResponseEntity.ok(tradeService.findUnusedTrades());
    }
}
