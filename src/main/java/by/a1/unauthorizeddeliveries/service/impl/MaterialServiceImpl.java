package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.Item;
import by.a1.unauthorizeddeliveries.entity.Material;
import by.a1.unauthorizeddeliveries.entity.Posting;
import by.a1.unauthorizeddeliveries.model.MaterialDTO;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.repository.MaterialRepository;
import by.a1.unauthorizeddeliveries.service.ItemService;
import by.a1.unauthorizeddeliveries.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    private final ItemService itemService;
    private final MaterialRepository repository;
    private final ModelMapper mapper;
    @Override
    @Transactional
    public MaterialDTO save(PostingRequestDTO posting) {
        return Objects.isNull(posting.getMaterial().getItem()) ? saveByID(posting) : saveByItem(posting);
    }

    @Override
    public List<MaterialDTO> findMaterials(long id) {
        return repository.findByPosting_Id(id)
                .stream()
                .map(material -> mapper.map(material, MaterialDTO.class))
                .toList();
    }
    private MaterialDTO saveByID(PostingRequestDTO posting) {
        Material materialEntity = mapper.map(posting.getMaterial(), Material.class);
        materialEntity.setPosting(mapper.map(posting, Posting.class));
        materialEntity.setItem(mapper.map(itemService.findItem(posting.getMaterial().getItemID()), Item.class));
        return mapper.map(repository.save(materialEntity), MaterialDTO.class);
    }
    private MaterialDTO saveByItem(PostingRequestDTO posting) {
        Material materialEntity = mapper.map(posting.getMaterial(), Material.class);
        materialEntity.setPosting(mapper.map(posting, Posting.class));
        return mapper.map(repository.save(materialEntity), MaterialDTO.class);
    }
}
