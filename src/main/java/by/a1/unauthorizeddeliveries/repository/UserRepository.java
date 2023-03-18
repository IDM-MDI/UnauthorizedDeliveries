package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    List<User> findByStatus(String status, PageRequest pageRequest);
    Optional<User> findByUsernameAndStatus(String username, String status);
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.status = :status, u.active = false where u.username = :username")
    void setStatus(@Param("username") String username, @Param("status") String status);
}
