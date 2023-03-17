package by.a1.unauthorizeddeliveries.service;

import by.a1.unauthorizeddeliveries.model.MaterialDTO;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;

import java.util.List;

public interface MaterialService {
    MaterialDTO save(PostingRequestDTO material);
    List<MaterialDTO> findMaterials(long id);
}
