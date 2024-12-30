package com.patroclos.cqrs.presentation;

import com.patroclos.cqrs.domain.Event;
import com.patroclos.cqrs.domain.FindAllItemEventsQuery;
import com.patroclos.cqrs.domain.FindInventoryItemQuery;
import com.patroclos.cqrs.entity.InventoryItem;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/inventory")
public class InventoryQueryController {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    private final QueryGateway queryGateway;


    @GetMapping("/{id}")
    public CompletableFuture<InventoryItemResponse> findById(
            @PathVariable("id") UUID id,
            @Parameter(
                    name = "dateTime",
                    description = "Fetch item state up until this specific date and time. (" + DATETIME_FORMAT + ")",
                    example = "2040-01-01 10:00"
            )
            @RequestParam(name = "dateTime", required = false)
            @DateTimeFormat(pattern = DATETIME_FORMAT) LocalDateTime dateTime) {

        return this.queryGateway.query(
                        new FindInventoryItemQuery(id, dateTime),
                        ResponseTypes.instanceOf(InventoryItem.class)
                )
                .thenApply(InventoryItemResponse::new);
    }


    @GetMapping("/{id}/events")
    public CompletableFuture<List<Object>> listEventsForAccount(@PathVariable(value = "id") UUID id) {
        return this.queryGateway.query(
                new FindAllItemEventsQuery(id),
                ResponseTypes.multipleInstancesOf(Object.class)
        );
    }

    public record InventoryItemResponse(UUID id, Integer stock, String name, LocalDateTime created) {
        public InventoryItemResponse(InventoryItem item) {
            this(item.getId(), item.getStock(), item.getName(), item.getCreated());
        }
    }
}
