package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.DocumentHeader;
import by.a1.unauthorizeddeliveries.entity.Posting;
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

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class PostingRepositoryTest {
    @Autowired
    private PostingRepository repository;
    @Autowired
    private DocumentHeaderRepository headerRepository;
    @Autowired
    private UserRepository userRepository;
    private Posting posting;
    @BeforeEach
    void setup() {
        DocumentHeader header = headerRepository.save(DocumentHeader.builder()
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
                .contractDate(LocalDate.of(2000, 10, 10))
                .postingDate(LocalDate.of(2000, 10, 10))
                .authorizeDelivery(true)
                .build());
        posting = repository.save(Posting.builder().header(header).build());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void existsPostingByHeaderPostingNumberShouldReturnTrue() {
        Assertions.assertThat(repository.existsPostingByHeader_PostingNumber(posting.getHeader().getPostingNumber()))
                .isTrue();
    }
    @Test
    void existsPostingByHeaderPostingNumberShouldReturnFalse() {
        Assertions.assertThat(repository.existsPostingByHeader_PostingNumber(0))
                .isFalse();
    }
    @Test
    void findByHeaderPostingNumberShouldReturnEntity() {
        Assertions.assertThat(repository.findByHeader_PostingNumber(posting.getHeader().getPostingNumber()))
                .isEqualTo(posting);
    }
    @Test
    void findByHeaderPostingNumberShouldReturnNull() {
        Assertions.assertThat(repository.findByHeader_PostingNumber(0))
                .isNull();
    }
}