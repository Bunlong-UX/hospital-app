package com.hospital.service;

import com.hospital.model.Inventory;
import com.hospital.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(String id) {
        return inventoryRepository.findById(id);
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(String id) {
        inventoryRepository.deleteById(id);
    }

    // Report: items near expiration (within next 30 days)
    public List<Inventory> getItemsNearExpiration() {
        String thresholdDate = LocalDate.now().plusDays(30).toString(); // YYYY-MM-DD
        return inventoryRepository.findItemsNearExpiration(thresholdDate);
    }
}
