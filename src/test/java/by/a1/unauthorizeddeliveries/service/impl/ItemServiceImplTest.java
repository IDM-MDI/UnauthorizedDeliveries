package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.Item;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.model.StatusModel;
import by.a1.unauthorizeddeliveries.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static by.a1.unauthorizeddeliveries.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private static final String FILTER = "id";
    private static final String DIRECTION = "asc";
    private ItemServiceImpl itemService;
    private ItemRepository itemRepositoryMock;
    private List<Item> items;
    private List<ItemDTO> itemDTOS;
    @BeforeEach
    public void setup() {
        itemRepositoryMock = mock(ItemRepository.class);
        itemService = new ItemServiceImpl(itemRepositoryMock,new ModelMapper());
        items = List.of(
                Item.builder()
                        .id(1L)
                        .description("test description 1")
                        .amount(100)
                        .currency("BYN")
                        .status(StatusModel.ACTIVE.name())
                        .build(),
                Item.builder()
                        .id(2L)
                        .description("test description 1")
                        .amount(100)
                        .currency("BYN")
                        .status(StatusModel.ACTIVE.name())
                        .build(),
                Item.builder()
                        .id(3L)
                        .description("test description 1")
                        .amount(100)
                        .currency("BYN")
                        .status(StatusModel.ACTIVE.name())
                        .build()
        );
        itemDTOS = List.of(
                ItemDTO.builder()
                        .id(1L)
                        .description("test description 1")
                        .amount(100)
                        .currency("BYN")
                        .status(StatusModel.ACTIVE.name())
                        .build(),
                ItemDTO.builder()
                        .id(2L)
                        .description("test description 1")
                        .amount(100)
                        .currency("BYN")
                        .status(StatusModel.ACTIVE.name())
                        .build(),
                ItemDTO.builder()
                        .id(3L)
                        .description("test description 1")
                        .amount(100)
                        .currency("BYN")
                        .status(StatusModel.ACTIVE.name())
                        .build()
        );
    }

    @Test
    void findItemsShouldReturnListOfItemDTO() {
        when(itemRepositoryMock.findByStatus(anyString(), any(PageRequest.class))).thenReturn(items);

        List<ItemDTO> result = itemService.findItems(PAGE,SIZE,FILTER,DIRECTION);

        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(itemDTOS.size());
    }

    @Test
    void findItemShouldReturnItemDTO() {
        when(itemRepositoryMock.findByIdAndStatus(anyLong(), anyString())).thenReturn(Optional.of(items.get(0)));

        ItemDTO result = itemService.findItem(items.get(0).getId());

        Assertions.assertThat(result)
                .isEqualTo(itemDTOS.get(0));
    }

    @Test
    void findItemsShouldReturnItemList() {
        when(itemRepositoryMock.findAll(any(Example.class)))
                .thenReturn(items);

        List<ItemDTO> result = itemService.findItems(itemDTOS.get(0));

        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(itemDTOS.size());
    }

    @Test
    void findItemShouldThrowServiceExceptionWhenItemNotFound() {
        when(itemRepositoryMock.findByIdAndStatus(anyLong(), anyString())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> itemService.findItem(items.get(0).getId()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining(ENTITY_NOT_FOUND.toString());
    }
    @Test
    void saveItemShouldReturnSavedItemDTO() {
        when(itemRepositoryMock.findByDescriptionAndAmount(any(String.class), any(Integer.class)))
                .thenReturn(Optional.empty());
        when(itemRepositoryMock.save(any(Item.class))).thenReturn(items.get(0));

        // when
        ItemDTO savedItemDTO = itemService.saveItem(itemDTOS.get(0));

        // then
        Assertions.assertThat(savedItemDTO).isNotNull().isInstanceOf(ItemDTO.class);
    }

    @Test
    void updateItemShouldReturnUpdatedItemDTO() {
        doReturn(Optional.empty())
                .when(itemRepositoryMock)
                .findByDescriptionAndAmount(anyString(), anyInt());
        when(itemRepositoryMock.save(any(Item.class))).thenReturn(items.get(0));

        ItemDTO updatedItemDTO = itemService.updateItem(itemDTOS.get(0).getId(), itemDTOS.get(0));

        Assertions.assertThat(updatedItemDTO)
                .isEqualTo(itemDTOS.get(0));
    }

    @Test
    void deleteItemShouldThrowServiceException() {
        when(itemRepositoryMock.existsById(any(Long.class))).thenReturn(false);

        Assertions.assertThatThrownBy(() -> itemService.deleteItem(itemDTOS.get(0).getId()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining(ENTITY_NOT_FOUND.toString());
    }

    @Test
    void deleteItemShouldSetStatusToDeleted() {
        when(itemRepositoryMock.existsById(itemDTOS.get(0).getId()))
                .thenReturn(true);

        itemService.deleteItem(itemDTOS.get(0).getId());

        verify(itemRepositoryMock, times(1))
                .setStatus(any(Long.class), eq(StatusModel.DELETED.name()));
    }
}