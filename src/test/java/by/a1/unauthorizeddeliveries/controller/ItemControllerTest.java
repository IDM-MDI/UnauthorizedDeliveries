package by.a1.unauthorizeddeliveries.controller;

import by.a1.unauthorizeddeliveries.UnauthorizedDeliveriesApplication;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.model.StatusModel;
import by.a1.unauthorizeddeliveries.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UnauthorizedDeliveriesApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest {
    private static final int PAGE = 0;
    private static final int SIZE = 10;
    private static final String FILTER = "id";
    private static final String DIRECTION = "asc";
    private static final MediaType HAL_JSON = MediaType.valueOf("application/hal+json");
    private static final MediaType TEXT_PLAIN = MediaType.parseMediaType("text/plain;charset=UTF-8");
    @MockBean
    private ItemService itemService;
    @Autowired
    private ItemController itemController;
    @Autowired
    private MockMvc mockMvc;

    private List<ItemDTO> itemDTOS;
    private ObjectMapper mapper;
    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        itemDTOS = List.of(
                ItemDTO.builder()
                        .id(1L)
                        .amount(100)
                        .description("des")
                        .status(StatusModel.ACTIVE.name())
                        .currency("BYN")
                        .build(),
                ItemDTO.builder()
                        .id(2L)
                        .amount(200)
                        .description("des")
                        .status(StatusModel.ACTIVE.name())
                        .currency("BYN")
                        .build(),
                ItemDTO.builder()
                        .id(3L)
                        .amount(300)
                        .description("des")
                        .status(StatusModel.ACTIVE.name())
                        .currency("BYN")
                        .build()
        );
    }

    @Test
    void findItemsShouldReturnEntityList() throws Exception {
        ItemDTO item = itemDTOS.get(0);
        when(itemService.findItems(PAGE,SIZE,FILTER, DIRECTION))
                .thenReturn(itemDTOS);
        mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(item.getId()))
                .andExpect(jsonPath("$.[0].description").value(item.getDescription()))
                .andExpect(jsonPath("$.[0].amount").value(item.getAmount()))
                .andExpect(jsonPath("$.[0].currency").value(item.getCurrency()))
                .andExpect(jsonPath("$.[0].status").value(item.getStatus()));
    }

    @Test
    void findItemShouldReturnEntity() throws Exception {
        ItemDTO item = itemDTOS.get(0);
        when(itemService.findItem(item.getId()))
                .thenReturn(item);
        mockMvc.perform(get("/api/v1/items/" + item.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.amount").value(item.getAmount()))
                .andExpect(jsonPath("$.currency").value(item.getCurrency()))
                .andExpect(jsonPath("$.status").value(item.getStatus()));
    }
    @Test
    void methodShouldThrowMethodNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/ITEMS"))
                .andExpect(status().isNotFound());
    }
    @Test
    void findItemShouldReturnEntityList() throws Exception {
        ItemDTO search = ItemDTO.builder()
                .amount(100)
                .description("des")
                .currency("BYN")
                .build();
        ItemDTO item = itemDTOS.get(0);
        when(itemService.findItems(search))
                .thenReturn(itemDTOS);
        mockMvc.perform(get("/api/v1/items/search?amount=" + search.getAmount()
                        + "&description=" + search.getDescription()
                        + "&currency=" + search.getCurrency()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(item.getId()))
                .andExpect(jsonPath("$.[0].description").value(item.getDescription()))
                .andExpect(jsonPath("$.[0].amount").value(item.getAmount()))
                .andExpect(jsonPath("$.[0].currency").value(item.getCurrency()))
                .andExpect(jsonPath("$.[0].status").value(item.getStatus()));
    }
    @Test
    void saveItemShouldReturnEntity() throws Exception {
        ItemDTO item = itemDTOS.get(0);
        when(itemService.saveItem(any(ItemDTO.class)))
                .thenReturn(item);

        mockMvc.perform(post("/api/v1/items")
                        .contentType(HAL_JSON)
                        .content(mapper.writeValueAsString(item))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.amount").value(item.getAmount()))
                .andExpect(jsonPath("$.currency").value(item.getCurrency()))
                .andExpect(jsonPath("$.status").value(item.getStatus()));
    }

    @Test
    void updateItemShouldReturnEntity() throws Exception {
        ItemDTO item = itemDTOS.get(0);
        when(itemService.updateItem(eq(item.getId()),any(ItemDTO.class)))
                .thenReturn(item);

        mockMvc.perform(put("/api/v1/items/" + item.getId())
                        .contentType(HAL_JSON)
                        .content(mapper.writeValueAsString(item))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.amount").value(item.getAmount()))
                .andExpect(jsonPath("$.currency").value(item.getCurrency()))
                .andExpect(jsonPath("$.status").value(item.getStatus()));
    }

    @Test
    void deleteItemsShouldReturnOK() throws Exception {
        ItemDTO item = itemDTOS.get(0);
        doNothing()
                .when(itemService)
                .deleteItem(item.getId());

        mockMvc.perform(delete("/api/v1/items/" + item.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("The Item successfully was deleted"));
    }
}