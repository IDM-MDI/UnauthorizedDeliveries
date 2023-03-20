package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.DocumentHeader;
import by.a1.unauthorizeddeliveries.entity.Material;
import by.a1.unauthorizeddeliveries.entity.Posting;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.DocumentHeaderDTO;
import by.a1.unauthorizeddeliveries.model.MaterialDTO;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.model.PostingResponseDTO;
import by.a1.unauthorizeddeliveries.repository.PostingRepository;
import by.a1.unauthorizeddeliveries.service.DocumentHeaderService;
import by.a1.unauthorizeddeliveries.service.MaterialService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static by.a1.unauthorizeddeliveries.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PostingServiceImplTest {
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private static final String FILTER = "id";
    private static final String DIRECTION = "asc";
    @InjectMocks
    private PostingServiceImpl postingService;
    @Mock
    private DocumentHeaderService documentHeaderService;
    @Mock
    private MaterialService materialService;
    @Mock
    private PostingRepository repository;
    @Mock
    private ModelMapper mapper;

    private List<Posting> postings;
    private List<PostingRequestDTO> postingRequestDTOS;
    private List<PostingResponseDTO> postingResponseDTOS;
    private DocumentHeader header;
    private DocumentHeaderDTO headerDTO;
    private List<Material> materials;
    private List<MaterialDTO> materialDTOS;

    @BeforeEach
    void setup() {
        header = DocumentHeader.builder()
                .id(1L)
                .postingNumber(123L)
                .build();
        headerDTO = DocumentHeaderDTO.builder()
                .id(1L)
                .postingNumber(123L)
                .build();
        postings = List.of(
                Posting.builder()
                        .id(1L)
                        .header(header)
                        .build(),
                Posting.builder()
                        .id(2L)
                        .header(header)
                        .build(),
                Posting.builder()
                        .id(3L)
                        .header(header)
                        .build()
        );
        materials = List.of(
                Material.builder()
                        .id(1L)
                        .posting(postings.get(0))
                        .build(),
                Material.builder()
                        .id(2L)
                        .posting(postings.get(0))
                        .build(),
                Material.builder()
                        .id(3L)
                        .posting(postings.get(0))
                        .build()
        );
        materialDTOS = List.of(
                MaterialDTO.builder()
                        .id(1L)
                        .build(),
                MaterialDTO.builder()
                        .id(2L)
                        .build(),
                MaterialDTO.builder()
                        .id(3L)
                        .build()
        );
        postingRequestDTOS = List.of(
                PostingRequestDTO.builder()
                        .id(1L)
                        .documentHeader(headerDTO)
                        .material(materialDTOS.get(0))
                        .build(),
                PostingRequestDTO.builder()
                        .id(2L)
                        .documentHeader(headerDTO)
                        .material(materialDTOS.get(0))
                        .build(),
                PostingRequestDTO.builder()
                        .id(3L)
                        .documentHeader(headerDTO)
                        .material(materialDTOS.get(0))
                        .build()
        );
        postingResponseDTOS = List.of(
                PostingResponseDTO.builder()
                        .id(1L)
                        .documentHeader(headerDTO)
                        .materials(materialDTOS)
                        .build(),
                PostingResponseDTO.builder()
                        .id(2L)
                        .documentHeader(headerDTO)
                        .materials(materialDTOS)
                        .build(),
                PostingResponseDTO.builder()
                        .id(3L)
                        .documentHeader(headerDTO)
                        .materials(materialDTOS)
                        .build()
        );
    }

    @Test
    void findPostingsShouldReturnListOfPostingResponseDTO() {
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(postings));
        when(mapper.map(any(Posting.class),eq(PostingResponseDTO.class)))
                .thenReturn(postingResponseDTOS.get(0));
        when(materialService.findMaterials(postingResponseDTOS.get(0).getId()))
                .thenReturn(materialDTOS);

        List<PostingResponseDTO> result = postingService.findPostings(PAGE, SIZE, FILTER, DIRECTION);

        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(postings.size());
    }

    @Test
    void findPostingShouldReturnPostingResponseDTO() {
        when(repository.findById(postingRequestDTOS.get(0).getId()))
                .thenReturn(Optional.of(postings.get(0)));
        when(mapper.map(postings.get(0),PostingResponseDTO.class))
                .thenReturn(postingResponseDTOS.get(0));
        when(materialService.findMaterials(postingResponseDTOS.get(0).getId()))
                .thenReturn(materialDTOS);

        PostingResponseDTO result = postingService.findPosting(postings.get(0).getId());

        Assertions.assertThat(result.getId()).isEqualTo(postings.get(0).getId());
    }

    @Test
    void findPostingShouldThrowServiceExceptionWhenPostingNotFound() {
        when(repository.findById(postingRequestDTOS.get(0).getId()))
                .thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> postingService.findPosting(postingRequestDTOS.get(0).getId()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining(ENTITY_NOT_FOUND.toString());
    }

    @Test
    void findPostingsByPostingRequestDTOShouldReturnListOfPostingResponseDTO() {
        when(repository.findAll(any(Example.class)))
                .thenReturn(postings);
        when(mapper.map(postingRequestDTOS.get(0),Posting.class))
                .thenReturn(postings.get(0));
        when(mapper.map(any(Posting.class),eq(PostingResponseDTO.class)))
                .thenReturn(postingResponseDTOS.get(0));
        when(materialService.findMaterials(postingResponseDTOS.get(0).getId()))
                .thenReturn(materialDTOS);

        List<PostingResponseDTO> result = postingService.findPostings(postingRequestDTOS.get(0));

        Assertions.assertThat(result)
                .isNotEmpty()
                .hasSize(postings.size());
    }

    @Test
    void savePostingShouldSaveNewPosting() {
        when(repository.existsPostingByHeader_PostingNumber(postingRequestDTOS.get(0).getDocumentHeader().getPostingNumber()))
                .thenReturn(false);
        when(documentHeaderService.save(headerDTO))
                .thenReturn(headerDTO);
        when(mapper.map(postingRequestDTOS.get(0),Posting.class))
                .thenReturn(postings.get(0));
        when(repository.save(postings.get(0)))
                .thenReturn(postings.get(0));

        when(mapper.map(postings.get(0),PostingRequestDTO.class))
                .thenReturn(postingRequestDTOS.get(0));
        when(materialService.save(postingRequestDTOS.get(0)))
                .thenReturn(materialDTOS.get(0));
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(postings.get(0)));
        when(mapper.map(postings.get(0),PostingResponseDTO.class))
                .thenReturn(postingResponseDTOS.get(0));
        when(materialService.findMaterials(postingResponseDTOS.get(0).getId()))
                .thenReturn(materialDTOS);

        PostingResponseDTO result = postingService.savePosting(postingRequestDTOS.get(0));

        Assertions.assertThat(result.getId())
                .isEqualTo(postingResponseDTOS.get(0).getId());
    }

    @Test
    void savePostingShouldSaveExistingPosting() {
        doReturn(true).when(repository)
                .existsPostingByHeader_PostingNumber(postingRequestDTOS.get(0).getDocumentHeader().getPostingNumber());
        when(repository.findByHeader_PostingNumber(anyLong())).thenReturn(postings.get(0));
        when(mapper.map(postings.get(0),PostingRequestDTO.class))
                .thenReturn(postingRequestDTOS.get(0));
        when(materialService.save(postingRequestDTOS.get(0)))
                .thenReturn(materialDTOS.get(0));
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(postings.get(0)));
        when(mapper.map(postings.get(0),PostingResponseDTO.class))
                .thenReturn(postingResponseDTOS.get(0));
        when(materialService.findMaterials(postingResponseDTOS.get(0).getId()))
                .thenReturn(materialDTOS);

        PostingResponseDTO result = postingService.savePosting(postingRequestDTOS.get(0));

        Assertions.assertThat(result.getId())
                .isEqualTo(postingResponseDTOS.get(0).getId());
    }
    @Test
    void deletePostingShouldDeleteExistingPosting() {
        when(repository.existsById(postings.get(0).getId()))
                .thenReturn(true);
        doNothing().when(repository)
                .deleteById(postings.get(0).getId());

        postingService.deletePosting(postings.get(0).getId());

        verify(repository).existsById(postings.get(0).getId());
        verify(repository).deleteById(postings.get(0).getId());
    }

    @Test
    void deletePostingShouldThrowExceptionForNonExistingPosting() {
        when(repository.existsById(anyLong()))
                .thenReturn(false);
        Assertions.assertThatThrownBy(() -> postingService.deletePosting(0))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining(ENTITY_NOT_FOUND.toString());
    }
}