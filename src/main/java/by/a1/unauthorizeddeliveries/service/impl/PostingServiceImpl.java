package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.Posting;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.model.PostingResponseDTO;
import by.a1.unauthorizeddeliveries.repository.PostingRepository;
import by.a1.unauthorizeddeliveries.service.DocumentHeaderService;
import by.a1.unauthorizeddeliveries.service.MaterialService;
import by.a1.unauthorizeddeliveries.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.a1.unauthorizeddeliveries.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static by.a1.unauthorizeddeliveries.util.SortDirectionUtil.getDirection;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement(proxyTargetClass = true)
public class PostingServiceImpl implements PostingService {
    private final DocumentHeaderService documentHeaderService;
    private final MaterialService materialService;
    private final PostingRepository repository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public List<PostingResponseDTO> findPostings(int page, int size, String filter, String direction) {
        return repository.findAll(PageRequest.of(page,size, getDirection(Sort.by(filter),direction)))
                .stream()
                .map(this::mapWithMaterials)
                .toList();
    }

    @Override
    @Transactional
    public PostingResponseDTO findPosting(long id) {
        return repository.findById(id)
                .map(this::mapWithMaterials)
                .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()));
    }

    @Override
    @Transactional
    public List<PostingResponseDTO> findPostings(PostingRequestDTO posting) {
        return repository.findAll(Example.of(mapper.map(posting, Posting.class)))
                .stream()
                .map(this::mapWithMaterials)
                .toList();
    }

    @Override
    @Transactional
    public PostingResponseDTO savePosting(PostingRequestDTO posting) {
        return isPostNumberExist(posting) ? createExistPosting(posting) : createNewPosting(posting);
    }

    @Override
    @Transactional
    public void deletePosting(long id) {
        if(!repository.existsById(id)) {
            throw new ServiceException(ENTITY_NOT_FOUND.toString());
        }
        repository.deleteById(id);
    }

    private PostingResponseDTO createNewPosting(PostingRequestDTO posting) {
        posting.setDocumentHeader(documentHeaderService.save(posting.getDocumentHeader()));
        Posting savedEntity = repository.save(mapper.map(posting,Posting.class));
        PostingRequestDTO savedDTO = mapper.map(savedEntity, PostingRequestDTO.class);
        savedDTO.setMaterial(posting.getMaterial());
        materialService.save(savedDTO);
        return findPosting(savedEntity.getId());
    }

    private PostingResponseDTO createExistPosting(PostingRequestDTO posting) {
        Posting byPostingNumber = repository.findByHeader_PostingNumber(posting.getDocumentHeader().getPostingNumber());
        PostingRequestDTO byPostingNumberDTO = mapper.map(byPostingNumber, PostingRequestDTO.class);
        byPostingNumberDTO.setMaterial(posting.getMaterial());
        materialService.save(byPostingNumberDTO);
        return findPosting(byPostingNumber.getId());
    }

    private boolean isPostNumberExist(PostingRequestDTO posting) {
        return repository.existsPostingByHeader_PostingNumber(posting.getDocumentHeader().getPostingNumber());
    }

    private PostingResponseDTO mapWithMaterials(Posting posting) {
        PostingResponseDTO entity = mapper.map(posting, PostingResponseDTO.class);
        entity.setMaterials(materialService.findMaterials(posting.getId()));
        return entity;
    }
}
