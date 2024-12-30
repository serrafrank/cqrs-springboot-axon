package com.patroclos.cqrs.domain;

import com.patroclos.cqrs.entity.InventoryItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@NoArgsConstructor
@Getter
@Aggregate(snapshotTriggerDefinition = "inventoryAggregateSnapshotTriggerDefinition")
public class InventoryItemAggregate extends InventoryItem {

    @CommandHandler
    public InventoryItemAggregate(InventoryItemCreateCommand command) {
        AggregateLifecycle.apply(
                new InventoryItemCreatedEvent(
                        command.id(),
                        command.initialStock(),
                        command.name()
                )
        );
    }

    @EventSourcingHandler
    public void on(InventoryItemCreatedEvent event) {
        super.id = event.id();
        super.name = event.name();
        super.stock = event.initialStock();
        super.created = event.created();
    }

    @CommandHandler
    public void handle(InventoryItemCommand command) {
        AggregateLifecycle.apply(
                new InventoryItemStockAdjustmentEvent(
                        command.id(),
                        command.stock(),
                        command.type()
                )
        );
    }

    @EventSourcingHandler
    public void on(InventoryItemStockAdjustmentEvent event) {
        try {
            adjustStock(event.type(), event.stockAdjustment());
            AggregateLifecycle.apply(new InventoryItemStockAdjustedEvent(id, stock));
        }catch (Exception e){
            throw new CommandExecutionException(e.getMessage(), e);
        }
    }
}
