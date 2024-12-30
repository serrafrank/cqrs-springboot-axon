package com.patroclos.cqrs.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryItemCreatedEvent(
        UUID id,
        Integer initialStock,
        String name,
        LocalDateTime created
) implements Event {

    public InventoryItemCreatedEvent(
            UUID id,
            Integer initialStock,
            String name) {
        this(id, initialStock, name, LocalDateTime.now());
    }

}
