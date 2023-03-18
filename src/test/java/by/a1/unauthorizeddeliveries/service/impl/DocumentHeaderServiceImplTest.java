package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.DocumentHeader;
import by.a1.unauthorizeddeliveries.entity.User;
import by.a1.unauthorizeddeliveries.model.DocumentHeaderDTO;
import by.a1.unauthorizeddeliveries.model.UserDTO;
import by.a1.unauthorizeddeliveries.repository.DocumentHeaderRepository;
import by.a1.unauthorizeddeliveries.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentHeaderServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private DocumentHeaderRepository repository;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private DocumentHeaderServiceImpl documentHeaderService;
    private User user;
    private UserDTO userDTO;
    private DocumentHeader header;
    private DocumentHeaderDTO headerDTO;
    @BeforeEach
    void setup() {
        user = User.builder()
                .username("user1")
                .active(true)
                .build();
        userDTO = UserDTO.builder()
                .username("user1")
                .active(true)
                .build();
        header = DocumentHeader.builder()
                .id(1L)
                .username(user)
                .postingNumber(123L)
                .authorizeDelivery(true)
                .build();
        headerDTO = DocumentHeaderDTO.builder()
                .id(1L)
                .postingNumber(123L)
                .authorizeDelivery(true)
                .username(user.getUsername())
                .build();
    }

    @Test
    void saveShouldSaveNewHeaderWhenHeaderDoesNotExist() {
        when(userService.findUser(user.getUsername())).thenReturn(userDTO);
        when(repository.existsByPostingNumber(123L)).thenReturn(false);
        when(mapper.map(headerDTO, DocumentHeader.class)).thenReturn(header);
        when(mapper.map(header, DocumentHeaderDTO.class)).thenReturn(headerDTO);
        when(repository.save(header)).thenReturn(header);

        DocumentHeaderDTO result = documentHeaderService.save(headerDTO);

        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(headerDTO);
    }

    @Test
    void saveShouldReturnExistingHeaderWhenHeaderExists() {
        when(userService.findUser(user.getUsername())).thenReturn(userDTO);
        when(repository.existsByPostingNumber(123L)).thenReturn(true);
        when(repository.findByPostingNumber(123L)).thenReturn(header);
        when(mapper.map(header, DocumentHeaderDTO.class)).thenReturn(headerDTO);

        DocumentHeaderDTO result = documentHeaderService.save(headerDTO);

        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(headerDTO);
    }

}