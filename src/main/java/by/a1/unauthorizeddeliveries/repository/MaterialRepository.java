package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material,Long> {
    List<Material> findByPosting_Id(long id);
}
