package com.patroclos.cqrs.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryItemStockAdjustmentEvent(UUID id, Integer stockAdjustment,
                                                StockAdjustmentType type, LocalDateTime created) implements Event {

    public InventoryItemStockAdjustmentEvent(UUID id, Integer stockAdjustment, StockAdjustmentType type) {
        this(id, stockAdjustment, type, LocalDateTime.now());
    }
}
