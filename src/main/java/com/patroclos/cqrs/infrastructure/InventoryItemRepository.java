package com.patroclos.cqrs.infrastructure;

import com.patroclos.cqrs.entity.InventoryItem;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, UUID> {
}
