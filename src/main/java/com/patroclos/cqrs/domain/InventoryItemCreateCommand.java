package com.patroclos.cqrs.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record InventoryItemCreateCommand(@TargetAggregateIdentifier UUID id,
                                         Integer initialStock,
                                         String name,
                                         LocalDateTime created) {

}
