package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.DocumentHeader;
import by.a1.unauthorizeddeliveries.entity.Item;
import by.a1.unauthorizeddeliveries.entity.Material;
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
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class MaterialRepositoryTest {
    @Autowired
    private MaterialRepository repository;
    @Autowired
    private PostingRepository postingRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private DocumentHeaderRepository documentHeaderRepository;
    @Autowired
    private UserRepository userRepository;
    private List<Material> materialList;
    @BeforeEach
    void setup() {
        DocumentHeader header = documentHeaderRepository.save(DocumentHeader.builder()
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
        Item item = itemRepository.save(
                Item.builder()
                        .description("description")
                        .amount(100)
                        .currency("BYN")
                        .status(StatusModel.ACTIVE.name())
                        .build()
        );
        Posting posting = postingRepository.save(
                Posting.builder()
                        .header(header)
                        .build()
        );
        materialList = repository.saveAll(
                List.of(
                        Material.builder()
                                .itemPosition(1L)
                                .quantity(1)
                                .posting(posting)
                                .measurementUnit("pc")
                                .item(item)
                                .build(),
                        Material.builder()
                                .itemPosition(1L)
                                .quantity(1)
                                .posting(posting)
                                .measurementUnit("pc")
                                .item(item)
                                .build(),
                        Material.builder()
                                .itemPosition(1L)
                                .quantity(1)
                                .posting(posting)
                                .measurementUnit("pc")
                                .item(item)
                                .build()
                )
        );
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void findByPostingIdShouldReturnEntities() {
        List<Material> result = repository.findByPosting_Id(materialList.get(0).getPosting().getId());

        Assertions.assertThat(result)
                .containsAll(materialList);
    }
    @Test
    void findByPostingIdShouldReturnEmptyList() {
        List<Material> result = repository.findByPosting_Id(0);

        Assertions.assertThat(result)
                .isEmpty();
    }
}