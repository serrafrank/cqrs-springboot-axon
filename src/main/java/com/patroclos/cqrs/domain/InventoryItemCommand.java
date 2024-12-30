package com.patroclos.cqrs.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


public record InventoryItemCommand(@TargetAggregateIdentifier UUID id,
                                   Integer stock,
                                   StockAdjustmentType type,
                                   LocalDateTime created) {

}
