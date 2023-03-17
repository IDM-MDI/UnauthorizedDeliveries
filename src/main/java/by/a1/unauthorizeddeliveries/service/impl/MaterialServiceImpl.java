package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.Material;
import by.a1.unauthorizeddeliveries.entity.Posting;
import by.a1.unauthorizeddeliveries.model.MaterialDTO;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.repository.MaterialRepository;
import by.a1.unauthorizeddeliveries.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository repository;
    private final ModelMapper mapper;
    @Override
    public MaterialDTO save(PostingRequestDTO posting) {
        Material materialEntity = mapper.map(posting.getMaterial(), Material.class);
        materialEntity.setPosting(mapper.map(posting, Posting.class));
        return mapper.map(repository.save(materialEntity), MaterialDTO.class);
    }

    @Override
    public List<MaterialDTO> findMaterials(long id) {
        return repository.findByPosting_Id(id)
                .stream()
                .map(material -> mapper.map(material, MaterialDTO.class))
                .toList();
    }
}
