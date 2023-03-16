package by.a1.unauthorizeddeliveries.service;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.model.PostingResponseDTO;

import java.util.List;

public interface PostingService {
    List<PostingResponseDTO> findPostings(int page, int size, String filter, String direction);

    PostingResponseDTO findPosting(long id) throws ServiceException;

    List<PostingResponseDTO> findPostings(PostingRequestDTO posting);

    PostingResponseDTO savePosting(PostingRequestDTO posting);

    void deletePosting(long id) throws ServiceException;
}
