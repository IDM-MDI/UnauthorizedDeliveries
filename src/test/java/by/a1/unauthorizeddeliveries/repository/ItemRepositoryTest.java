package by.a1.unauthorizeddeliveries.repository;

import by.a1.unauthorizeddeliveries.entity.Item;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ItemRepositoryTest {
    @Autowired
    private ItemRepository repository;
    private List<Item> itemList;
    @BeforeEach
    void setup() {
        itemList = repository.saveAll(
                List.of(
                        Item.builder()
                                .description("description")
                                .amount(100)
                                .currency("BYN")
                                .status(StatusModel.ACTIVE.name())
                                .build(),
                        Item.builder()
                                .description("description")
                                .amount(200)
                                .currency("BYN")
                                .status(StatusModel.ACTIVE.name())
                                .build(),
                        Item.builder()
                                .description("description")
                                .amount(300)
                                .currency("BYN")
                                .status(StatusModel.ACTIVE.name())
                                .build()
                )
        );
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll(itemList);
    }

    @Test
    void findByDescriptionAndAmountShouldReturnEntity() {
        Item item = itemList.get(0);
        Assertions.assertThat(repository.findByDescriptionAndAmount(item.getDescription(),item.getAmount()))
                .isPresent();
    }
    @Test
    void findByDescriptionAndAmountShouldReturnEmpty() {
        Item item = itemList.get(0);
        Assertions.assertThat(repository.findByDescriptionAndAmount(item.getDescription(),item.getAmount()+1000))
                .isNotPresent();
    }

    @Test
    void findByStatusShouldReturnEntities() {
        List<Item> result = repository.findByStatus(StatusModel.ACTIVE.name(), PageRequest.of(0, 3, Sort.by("id")));
        Assertions.assertThat(result)
                .isNotEmpty()
                .containsAll(itemList);
    }

    @Test
    void findByStatusShouldReturnEmptyList() {
        List<Item> result = repository.findByStatus(StatusModel.ACTIVE.name(), PageRequest.of(100, 3, Sort.by("id")));
        Assertions.assertThat(result)
                .isEmpty();
    }

    @Test
    void findByIdAndStatusShouldReturnEntity() {
        Item item = itemList.get(0);
        Assertions.assertThat(repository.findByIdAndStatus(item.getId(),item.getStatus()))
                .isPresent();
    }
    @Test
    void findByIdAndStatusShouldReturnEmpty() {
        Assertions.assertThat(repository.findByIdAndStatus(0,"test"))
                .isNotPresent();
    }
    @Test
    void setStatusShouldCorrect() {
        Item item = itemList.get(0);
        repository.setStatus(item.getId(),StatusModel.DELETED.name());
        Optional<Item> result = repository.findById(item.getId());

        Assertions.assertThat(result.get().getStatus())
                .isEqualTo(StatusModel.DELETED.name());
    }
}