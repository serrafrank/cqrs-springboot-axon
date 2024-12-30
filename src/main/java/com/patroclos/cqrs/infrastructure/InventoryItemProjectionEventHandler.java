package com.patroclos.cqrs.infrastructure;

import com.patroclos.cqrs.domain.InventoryItemCreatedEvent;
import com.patroclos.cqrs.domain.InventoryItemStockAdjustedEvent;
import com.patroclos.cqrs.entity.InventoryItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class InventoryItemProjectionEventHandler {

    private final InventoryItemRepository repository;


    @EventHandler
    public void on(InventoryItemCreatedEvent event) {
        log.debug("Handling an Inventory Item creation command {}", event.id());
        InventoryItem item = new InventoryItem(
                event.id(),
                event.initialStock(),
                event.name(),
                event.created()
        );
        this.repository.save(item);
    }

    @EventHandler
    public void on(InventoryItemStockAdjustedEvent event) {
        log.debug("Handling an inventory stock adjustment (increase in stock) command {}", event.id());

        InventoryItem item = this.repository.findById(event.id())
                .orElseThrow(() -> new NullPointerException("Could not find Inventory Item with id: [ " + event.id() + " ]"));

        item.setStock(item.getStock());
        this.repository.save(item);
    }
}
