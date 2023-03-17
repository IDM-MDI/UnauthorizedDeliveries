package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting,Long> {
    boolean existsPostingByHeader_PostingNumber(long postingNumber);
    Posting findByHeader_PostingNumber(long postingNumber);
}
