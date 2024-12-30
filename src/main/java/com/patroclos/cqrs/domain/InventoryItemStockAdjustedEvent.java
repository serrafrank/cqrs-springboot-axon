package com.patroclos.cqrs.domain;

import java.time.LocalDateTime;
import java.util.UUID;


public record InventoryItemStockAdjustedEvent(UUID id, Integer stock, LocalDateTime created) implements Event {

    public InventoryItemStockAdjustedEvent(UUID id, Integer stock) {
        this(id, stock, LocalDateTime.now());
    }

}
