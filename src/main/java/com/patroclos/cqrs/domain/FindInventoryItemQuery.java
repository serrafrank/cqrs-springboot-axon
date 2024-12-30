package com.patroclos.cqrs.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record FindInventoryItemQuery(UUID id, LocalDateTime dateTime) {
}
