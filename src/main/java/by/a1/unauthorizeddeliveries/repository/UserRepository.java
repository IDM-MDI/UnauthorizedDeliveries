package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
