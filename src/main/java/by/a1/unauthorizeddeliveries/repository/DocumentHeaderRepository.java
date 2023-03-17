package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.DocumentHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentHeaderRepository extends JpaRepository<DocumentHeader,Long> {
    boolean existsByPostingNumber(long postingNumber);
    DocumentHeader findByPostingNumber(long postingNumber);
}
