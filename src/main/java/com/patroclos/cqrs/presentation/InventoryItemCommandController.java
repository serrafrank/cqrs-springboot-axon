package com.patroclos.cqrs.presentation;


import com.patroclos.cqrs.domain.InventoryItemCommand;
import com.patroclos.cqrs.domain.InventoryItemCreateCommand;
import com.patroclos.cqrs.domain.StockAdjustmentType;
import com.patroclos.cqrs.entity.InventoryItem;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/inventoryitems")
@AllArgsConstructor
public class InventoryItemCommandController {

    private final CommandGateway commandGateway;

    @PostMapping(value = "/create")
    public CompletableFuture<InventoryItem> createItem(@Validated @RequestBody InventoryItemCreationRequest itemCreateDTO) {
        return this.commandGateway.send(new InventoryItemCreateCommand(
                UUID.randomUUID(),
                itemCreateDTO.initialStock(),
                itemCreateDTO.name(),
                LocalDateTime.now()
        ));
    }

    @PutMapping(value = "/stockadjustment/{id}")
    public CompletableFuture<String> stockAdjustment(@PathVariable(value = "id") String id,
                                                     @Validated @RequestBody InventoryItemStockRequest inventoryItemStockRequest) {
        return this.commandGateway.send(new InventoryItemCommand(
                UUID.fromString(id),
                inventoryItemStockRequest.stockAdjustment(),
                inventoryItemStockRequest.type(),
                LocalDateTime.now()
        ));
    }


    public record InventoryItemCreationRequest(@PositiveOrZero Integer initialStock, String name) {
    }
    public record InventoryItemStockRequest(@PositiveOrZero Integer stockAdjustment, StockAdjustmentType type) {
    }
}
