package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.User;
import by.a1.unauthorizeddeliveries.model.StatusModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    private List<User> users;
    @BeforeEach
    void setup() {
        users = repository.saveAll(
             List.of(
                     User.builder()
                             .username("user1")
                             .application("app")
                             .department("dep")
                             .job("job")
                             .status(StatusModel.ACTIVE.name())
                             .active(true)
                             .build(),
                     User.builder()
                             .username("user2")
                             .application("app")
                             .department("dep")
                             .job("job")
                             .status(StatusModel.ACTIVE.name())
                             .active(true)
                             .build(),
                     User.builder()
                             .username("user3")
                             .application("app")
                             .department("dep")
                             .job("job")
                             .status(StatusModel.ACTIVE.name())
                             .active(true)
                             .build()
             )
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByStatusShouldReturnEntities() {
        List<User> result = repository.findByStatus(StatusModel.ACTIVE.name(), PageRequest.of(0, 3, Sort.by("username")));
        Assertions.assertThat(result)
                .isNotEmpty()
                .containsAll(users);
    }
    @Test
    void findByStatusShouldReturnEmptyList() {
        List<User> result = repository.findByStatus(StatusModel.ACTIVE.name(), PageRequest.of(100, 3, Sort.by("username")));
        Assertions.assertThat(result)
                .isEmpty();
    }
    @Test
    void findByUsernameAndStatusShouldReturnEntity() {
        User user = users.get(0);
        Assertions.assertThat(repository.findByUsernameAndStatus(user.getUsername(), user.getStatus()))
                .isPresent();
    }

    @Test
    void findByUsernameAndStatusShouldReturnEmpty() {
        Assertions.assertThat(repository.findByUsernameAndStatus("test","test"))
                .isNotPresent();
    }

    @Test
    void setStatusShouldCorrect() {
        User user = users.get(0);
        repository.setStatus(user.getUsername(),StatusModel.DELETED.name());
        Optional<User> result = repository.findById(user.getUsername());

        Assertions.assertThat(result.get().getStatus())
                .isEqualTo(StatusModel.DELETED.name());
    }
}