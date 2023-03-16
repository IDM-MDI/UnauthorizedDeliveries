package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.model.PostingResponseDTO;
import by.a1.unauthorizeddeliveries.service.PostingService;

import java.util.List;

public class PostingServiceImpl implements PostingService {
    @Override
    public List<PostingResponseDTO> findPostings(int page, int size, String filter, String direction) {
        return null;
    }

    @Override
    public PostingResponseDTO findPosting(long id) {
        return null;
    }

    @Override
    public List<PostingResponseDTO> findPostings(PostingResponseDTO posting) {
        return null;
    }

    @Override
    public PostingResponseDTO savePosting(PostingRequestDTO posting) {
        return null;
    }

    @Override
    public void deletePosting(long id) {

    }
}
