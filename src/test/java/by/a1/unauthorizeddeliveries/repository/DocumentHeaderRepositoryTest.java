package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.DocumentHeader;
import by.a1.unauthorizeddeliveries.entity.User;
import by.a1.unauthorizeddeliveries.model.StatusModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@DataJpaTest
@ActiveProfiles("test")
class DocumentHeaderRepositoryTest {
    @Autowired
    private DocumentHeaderRepository repository;
    @Autowired
    private UserRepository userRepository;
    private DocumentHeader header;
    @BeforeEach
    void setup() {
        header = repository.save(DocumentHeader.builder()
                .id(1L)
                .username(
                        userRepository.save(
                                User.builder()
                                        .username("user")
                                        .application("app")
                                        .department("dep")
                                        .job("job")
                                        .status(StatusModel.ACTIVE.name())
                                        .active(true)
                                        .build()
                        )
                )
                .postingNumber(123L)
                .contractDate(LocalDate.of(2000,10,10))
                .postingDate(LocalDate.of(2000,10,10))
                .authorizeDelivery(true)
                .build());
    }

    @AfterEach
    void tearDown() {
        repository.deleteById(1L);
    }
    @Test
    void existsByPostingNumberShouldReturnTrue() {
        Assertions.assertThat(repository.existsByPostingNumber(header.getPostingNumber()))
                .isTrue();
    }
    @Test
    void existsByPostingNumberShouldReturnFalse() {
        Assertions.assertThat(repository.existsByPostingNumber(0L))
                .isFalse();
    }

    @Test
    void findByPostingNumberShouldReturnEntity() {
        Assertions.assertThat(repository.findByPostingNumber(header.getPostingNumber()))
                .isEqualTo(header);
    }
    @Test
    void findByPostingNumberShouldReturnNull() {
        Assertions.assertThat(repository.findByPostingNumber(0L))
                .isNull();
    }
}