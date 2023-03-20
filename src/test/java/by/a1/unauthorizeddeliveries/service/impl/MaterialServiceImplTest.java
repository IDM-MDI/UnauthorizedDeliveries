package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.Item;
import by.a1.unauthorizeddeliveries.entity.Material;
import by.a1.unauthorizeddeliveries.entity.Posting;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.model.MaterialDTO;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.repository.MaterialRepository;
import by.a1.unauthorizeddeliveries.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaterialServiceImplTest {
    @InjectMocks
    private MaterialServiceImpl materialService;
    @Mock
    private ItemService itemService;
    @Mock
    private MaterialRepository materialRepository;
    @Mock
    private ModelMapper modelMapper;

    private PostingRequestDTO postingRequestDTO;
    private Posting posting;
    private List<Material> materials;
    private List<MaterialDTO> materialDTOS;
    private Item item;
    private ItemDTO itemDTO;
    @BeforeEach
    void setup() {
        item = Item.builder()
                .id(1L)
                .build();
        itemDTO = ItemDTO.builder()
                .id(1L)
                .build();
        materials = List.of(
                Material.builder()
                        .id(1L)
                        .item(item)
                        .build(),
                Material.builder()
                        .id(2L)
                        .item(item)
                        .build(),
                Material.builder()
                        .id(3L)
                        .item(item)
                        .build()
        );
        materialDTOS = List.of(
                MaterialDTO.builder()
                        .id(1L)
                        .item(itemDTO)
                        .build(),
                MaterialDTO.builder()
                        .id(2L)
                        .item(itemDTO)
                        .build(),
                MaterialDTO.builder()
                        .id(3L)
                        .item(itemDTO)
                        .build()
        );
        postingRequestDTO = PostingRequestDTO.builder()
                .id(1L)
                .material(materialDTOS.get(0))
                .build();
        posting = Posting.builder()
                .id(1L)
                .build();
    }

    @Test
    void saveByIDShouldReturnMaterialDTO() {
        postingRequestDTO = PostingRequestDTO.builder()
                .material(MaterialDTO.builder()
                        .id(1L)
                        .itemID(1L)
                        .build())
                .build();
        when(modelMapper.map(postingRequestDTO.getMaterial(), Material.class)).thenReturn(materials.get(0));
        when(modelMapper.map(postingRequestDTO, Posting.class)).thenReturn(posting);
        when(itemService.findItem(postingRequestDTO.getMaterial().getItemID())).thenReturn(itemDTO);
        when(modelMapper.map(itemDTO, Item.class)).thenReturn(item);
        when(materialRepository.save(materials.get(0))).thenReturn(materials.get(0));
        when(modelMapper.map(materials.get(0), MaterialDTO.class)).thenReturn(materialDTOS.get(0));

        MaterialDTO result = materialService.save(postingRequestDTO);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void saveByItemShouldReturnMaterialDTO() {
        when(modelMapper.map(postingRequestDTO.getMaterial(), Material.class)).thenReturn(materials.get(0));
        when(modelMapper.map(postingRequestDTO, Posting.class)).thenReturn(posting);
        when(materialRepository.save(materials.get(0))).thenReturn(materials.get(0));
        when(modelMapper.map(materials.get(0), MaterialDTO.class)).thenReturn(materialDTOS.get(0));

        MaterialDTO result = materialService.save(postingRequestDTO);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void findMaterialsShouldReturnListOfMaterialDTO() {
        long postingId = 1L;
        when(materialRepository.findByPosting_Id(postingId)).thenReturn(materials);
        when(modelMapper.map(materials.get(0), MaterialDTO.class)).thenReturn(materialDTOS.get(0));

        List<MaterialDTO> result = materialService.findMaterials(postingId);

        Assertions.assertThat(result).isNotNull();
    }
}