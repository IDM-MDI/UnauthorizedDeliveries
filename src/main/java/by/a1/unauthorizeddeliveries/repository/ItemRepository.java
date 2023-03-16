package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
