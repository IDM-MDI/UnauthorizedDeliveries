package by.a1.unauthorizeddeliveries.controller;

import by.a1.unauthorizeddeliveries.UnauthorizedDeliveriesApplication;
import by.a1.unauthorizeddeliveries.model.DocumentHeaderDTO;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.model.MaterialDTO;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.model.PostingResponseDTO;
import by.a1.unauthorizeddeliveries.model.StatusModel;
import by.a1.unauthorizeddeliveries.service.PostingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UnauthorizedDeliveriesApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostingControllerTest {
    private static final int PAGE = 0;
    private static final int SIZE = 10;
    private static final String FILTER = "id";
    private static final String DIRECTION = "asc";

    private static final MediaType HAL_JSON = MediaType.valueOf("application/hal+json");
    private static final MediaType TEXT_PLAIN = MediaType.parseMediaType("text/plain;charset=UTF-8");

    @MockBean
    private PostingService postingService;
    @Autowired
    private PostingController postingController;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private List<PostingResponseDTO> responseDTOS;
    private PostingRequestDTO requestDTO;
    @BeforeEach
    void setup() {
        mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        DocumentHeaderDTO header = DocumentHeaderDTO.builder()
                .id(1L)
                .username("user")
                .postingNumber(123L)
                .authorizeDelivery(true)
                .postingDate(LocalDate.of(2000,10,10))
                .contractDate(LocalDate.of(2000,10,10))
                .build();
        ItemDTO itemDTO = ItemDTO.builder()
                .id(1L)
                .amount(100)
                .currency("BYN")
                .description("des")
                .status(StatusModel.ACTIVE.name())
                .build();
        List<MaterialDTO> materialDTOS = List.of(
                MaterialDTO.builder()
                        .id(1L)
                        .itemID(1L)
                        .item(itemDTO)
                        .measurementUnit("pc")
                        .quantity(1)
                        .build(),
                MaterialDTO.builder()
                        .id(2L)
                        .itemID(1L)
                        .item(itemDTO)
                        .measurementUnit("pc")
                        .quantity(1)
                        .build(),
                MaterialDTO.builder()
                        .id(3L)
                        .itemID(1L)
                        .item(itemDTO)
                        .measurementUnit("pc")
                        .quantity(1)
                        .build()
        );
        requestDTO = PostingRequestDTO.builder()
                .id(1L)
                .material(materialDTOS.get(0))
                .documentHeader(header)
                .build();
        responseDTOS = List.of(
                PostingResponseDTO.builder()
                        .id(1L)
                        .documentHeader(header)
                        .materials(materialDTOS)
                        .build(),
                PostingResponseDTO.builder()
                        .id(2L)
                        .documentHeader(header)
                        .materials(materialDTOS)
                        .build(),
                PostingResponseDTO.builder()
                        .id(3L)
                        .documentHeader(header)
                        .materials(materialDTOS)
                        .build()
        );
    }

    @Test
    void findPostingsShouldReturnEntities() throws Exception {
        PostingResponseDTO responseDTO = responseDTOS.get(0);
        when(postingService.findPostings(PAGE,SIZE,FILTER, DIRECTION))
                .thenReturn(responseDTOS);
        mockMvc.perform(get("/api/v1/postings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.[0].documentHeader.id").value(responseDTO.getDocumentHeader().getId()))
                .andExpect(jsonPath("$.[0].materials[0].id").value(responseDTO.getMaterials().get(0).getId()))
                .andExpect(jsonPath("$.[0].materials[0].item.id").value(responseDTO.getMaterials().get(0).getItem().getId()));
    }

    @Test
    void findPostingShouldReturnEntity() throws Exception {
        PostingResponseDTO responseDTO = responseDTOS.get(0);
        when(postingService.findPosting(responseDTO.getId()))
                .thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/postings/" + responseDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.documentHeader.id").value(responseDTO.getDocumentHeader().getId()))
                .andExpect(jsonPath("$.materials[0].id").value(responseDTO.getMaterials().get(0).getId()))
                .andExpect(jsonPath("$.materials[0].item.id").value(responseDTO.getMaterials().get(0).getItem().getId()));
    }

    @Test
    void findPostingShouldReturnEntities() throws Exception {
        PostingResponseDTO responseDTO = responseDTOS.get(0);
        when(postingService.findPostings(any(PostingRequestDTO.class)))
                .thenReturn(responseDTOS);

        mockMvc.perform(get("/api/v1/postings/search?postingNumber=" + requestDTO.getDocumentHeader().getPostingNumber()
                        + "&username=" + requestDTO.getDocumentHeader().getUsername()
                        + "&quantity=" + requestDTO.getMaterial().getQuantity())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.[0].documentHeader.id").value(responseDTO.getDocumentHeader().getId()))
                .andExpect(jsonPath("$.[0].materials[0].id").value(responseDTO.getMaterials().get(0).getId()))
                .andExpect(jsonPath("$.[0].materials[0].item.id").value(responseDTO.getMaterials().get(0).getItem().getId()));
    }

    @Test
    void savePostingShouldReturnEntity() throws Exception {
        PostingResponseDTO responseDTO = responseDTOS.get(0);
        when(postingService.savePosting(any(PostingRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/postings")
                        .contentType(HAL_JSON)
                        .content(mapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.documentHeader.id").value(responseDTO.getDocumentHeader().getId()))
                .andExpect(jsonPath("$.materials[0].id").value(responseDTO.getMaterials().get(0).getId()))
                .andExpect(jsonPath("$.materials[0].item.id").value(responseDTO.getMaterials().get(0).getItem().getId()));
    }

    @Test
    void deletePostingsShouldReturnOK() throws Exception {
        PostingResponseDTO responseDTO = responseDTOS.get(0);
        doNothing()
                .when(postingService)
                .deletePosting(responseDTO.getId());

        mockMvc.perform(delete("/api/v1/postings/" + responseDTO.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("The Posting successfully was deleted"));
    }
}