package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.DocumentHeader;
import by.a1.unauthorizeddeliveries.model.DocumentHeaderDTO;
import by.a1.unauthorizeddeliveries.repository.DocumentHeaderRepository;
import by.a1.unauthorizeddeliveries.service.DocumentHeaderService;
import by.a1.unauthorizeddeliveries.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentHeaderServiceImpl implements DocumentHeaderService {
    private final UserService userService;
    private final DocumentHeaderRepository repository;
    private final ModelMapper mapper;
    @Override
    public DocumentHeaderDTO save(DocumentHeaderDTO header) {
        header.setAuthorizeDelivery(userService.findUser(header.getUsername()).getActive());
        return repository.existsByPostingNumber(header.getPostingNumber()) ?
                mapper.map(repository.findByPostingNumber(header.getPostingNumber()), DocumentHeaderDTO.class)
                :
                mapper.map(repository.save(mapper.map(header, DocumentHeader.class)), DocumentHeaderDTO.class);
    }
}
