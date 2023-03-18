package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.Item;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Optional<Item> findByDescriptionAndAmount(String description,int amount);
    List<Item> findByStatus(String status, PageRequest pageRequest);
    Optional<Item> findByIdAndStatus(long id, String status);
    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.status = :status where i.id = :id")
    void setStatus(@Param("id") long id, @Param("status") String status);
}
