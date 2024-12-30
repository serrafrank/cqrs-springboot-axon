package com.patroclos.cqrs.infrastructure;

import com.patroclos.cqrs.domain.*;
import com.patroclos.cqrs.entity.InventoryItem;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryQueryHandler {

    private final EventStore eventStore;
    private final InventoryItemRepository repository;


    @QueryHandler
    public List<Event> listEventsForItem(FindAllItemEventsQuery query) {
        return listEventsForItem(query.id());
    }



    @QueryHandler
    public InventoryItem findByIdAndDate(FindInventoryItemQuery query) {
        log.debug("Handling FindInventoryItemQuery query: {}", query);

        return query.dateTime() == null
                ? this.repository.findById(query.id()).orElse(null)
                : findByIdAndDate(query.id(), query.dateTime());

    }


    private InventoryItem findByIdAndDate(UUID id, LocalDateTime dateTime) {
        InventoryItem item = new InventoryItem();

        listEventsForItem(id)
                .stream()
                .filter(event -> dateTime == null || event.created().isBefore(dateTime))
                .forEach(event -> {
                    if (event instanceof InventoryItemCreatedEvent e) {
                        item.setName(e.name());
                        item.setId(e.id());
                        item.setStock(e.initialStock());
                        item.setCreated(e.created());
                    } else if (event instanceof InventoryItemStockAdjustedEvent e){
                        item.setStock(e.stock());
                    }
                });

        return item;
    }


    private List<Event> listEventsForItem(UUID id) {
        return this.eventStore
                .readEvents(id.toString(), 0)
                .asStream()
                .map(Message::getPayload)
                .filter(event -> event instanceof Event)
                .map(event -> (Event) event)
                .collect(Collectors.toList());
    }
}
