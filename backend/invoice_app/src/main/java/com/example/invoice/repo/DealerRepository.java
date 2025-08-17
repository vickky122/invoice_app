package com.example.invoice.repo;

import com.example.invoice.model.Dealer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class DealerRepository {

    private final Map<String, Dealer> dealers = new HashMap<>();

    public DealerRepository() {
        // Seed sample dealers
        dealers.put("D001", new Dealer(
                "D001",
                "Dealers Auto Center",
                "123 Market St, City, State",
                "sales@dealersautocenter.com",
                "+1-555-0101",
                "GST-123456789"
        ));
        dealers.put("D002", new Dealer(
                "D002",
                "Premium Motors",
                "456 Elm Ave, Town, State",
                "info@premiummotors.com",
                "+1-555-0202",
                "GST-987654321"
        ));
    }

    public Optional<Dealer> findById(String id) {
        return Optional.ofNullable(dealers.get(id));
    }
}
