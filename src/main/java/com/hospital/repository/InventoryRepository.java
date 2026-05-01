package com.hospital.repository;

import com.hospital.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {

    // Find items expiring on or before a given date string (YYYY-MM-DD comparison works lexicographically)
    @Query("{ 'expirationDate': { $lte: ?0 } }")
    List<Inventory> findItemsNearExpiration(String thresholdDate);
}
