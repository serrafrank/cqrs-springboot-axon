package com.patroclos.cqrs.entity;

import com.patroclos.cqrs.domain.StockAdjustmentType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.modelling.command.AggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryItem {

    @AggregateIdentifier
    @Id
    protected UUID id;
    protected Integer stock;
    protected String name;
    protected LocalDateTime created;

    public void adjustStock(StockAdjustmentType type, int stockAdjustment) {
        switch (type) {
            case Add -> addStock(stockAdjustment);
            case Remove -> removeStock(stockAdjustment);
            default -> throw new IllegalArgumentException("Stock adjustment type not supported");
        }
    }

    private void addStock(int stockAdjustment) {
        stock += stockAdjustment;
    }

    private void removeStock( int stockAdjustment) {
        if (stock - stockAdjustment < 0) {
            throw new CommandExecutionException("Value not valid", new IllegalArgumentException("Cannot deduct from stock if stock if stock adjustment > current stock"));
        }
        stock -= stockAdjustment;
    }
}
