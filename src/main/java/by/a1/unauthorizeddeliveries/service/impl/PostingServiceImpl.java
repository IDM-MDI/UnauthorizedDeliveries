package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.Item;
import by.a1.unauthorizeddeliveries.entity.Material;
import by.a1.unauthorizeddeliveries.entity.Posting;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.model.PostingResponseDTO;
import by.a1.unauthorizeddeliveries.repository.PostingRepository;
import by.a1.unauthorizeddeliveries.service.PostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.a1.unauthorizeddeliveries.util.SortDirectionUtil.getDirection;

@Service
public class PostingServiceImpl implements PostingService {
    private final PostingRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public PostingServiceImpl(PostingRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        mapper.createTypeMap(PostingRequestDTO.class, Posting.class)
                .addMappings(mapping ->
                        mapping.map(source -> List.of(mapper.map(source.getMaterial(), Material.class)),
                        Posting::setMaterials)
                );
    }

    @Override
    public List<PostingResponseDTO> findPostings(int page, int size, String filter, String direction) {
        return repository.findAll(PageRequest.of(page,size, getDirection(Sort.by(filter),direction)))
                .stream()
                .map(posting -> mapper.map(posting, PostingResponseDTO.class))
                .toList();
    }

    @Override
    public PostingResponseDTO findPosting(long id) throws ServiceException {
        return repository.findById(id)
                .map(posting -> mapper.map(posting, PostingResponseDTO.class))
                .orElseThrow(() -> new ServiceException("User not found"));
    }

    @Override
    public List<PostingResponseDTO> findPostings(PostingRequestDTO posting) {
        return repository.findAll(Example.of(mapper.map(posting, Posting.class)))
                .stream()
                .map(p -> mapper.map(p, PostingResponseDTO.class))
                .toList();
    }

    @Override
    public PostingResponseDTO savePosting(PostingRequestDTO posting) {
        return isPostNumberExist(posting) ? createExistPosting(posting) : createNewPosting(posting);
    }

    @Override
    public void deletePosting(long id) throws ServiceException {
        if(!repository.existsById(id)) {
            throw new ServiceException();
        }
        repository.deleteById(id);
    }

    private PostingResponseDTO createNewPosting(PostingRequestDTO posting) {
        Posting savedPosting = repository.save(mapper.map(posting, Posting.class));
        return mapper.map(savedPosting, PostingResponseDTO.class);
    }

    private PostingResponseDTO createExistPosting(PostingRequestDTO posting) {
        Posting byPostingNumber = repository.findByHeader_PostingNumber(posting.getDocumentHeader().getPostingNumber());
        byPostingNumber.getMaterials()
                .add(mapper.map(posting.getMaterial(), Material.class));
        return mapper.map(byPostingNumber, PostingResponseDTO.class);
    }

    private boolean isPostNumberExist(PostingRequestDTO posting) {
        return repository.existsPostingByHeader_PostingNumber(posting.getDocumentHeader().getPostingNumber());
    }
}
