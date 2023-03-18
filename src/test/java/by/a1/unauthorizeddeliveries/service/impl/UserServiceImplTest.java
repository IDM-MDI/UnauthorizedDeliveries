package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.User;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.StatusModel;
import by.a1.unauthorizeddeliveries.model.UserDTO;
import by.a1.unauthorizeddeliveries.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static by.a1.unauthorizeddeliveries.exception.ExceptionStatus.USER_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final int PAGE = 0;

    private static final int SIZE = 5;
    private static final String FILTER = "username";
    private static final String DIRECTION = "asc";
    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;
    private List<UserDTO> userDTOS;
    private List<User> users;

    @BeforeEach
    void setup() {
        users = Arrays.asList(
                User.builder()
                        .username("test user 1")
                        .status(StatusModel.ACTIVE.name())
                        .active(true)
                        .build(),
                User.builder()
                        .username("test user 2")
                        .status(StatusModel.ACTIVE.name())
                        .active(true)
                        .build(),
                User.builder()
                        .username("test user 3")
                        .status(StatusModel.ACTIVE.name())
                        .active(true)
                        .build()
        );
        userDTOS = Arrays.asList(
                UserDTO.builder()
                        .username("test user 1")
                        .status(StatusModel.ACTIVE.name())
                        .active(true)
                        .build(),
                UserDTO.builder()
                        .username("test user 2")
                        .status(StatusModel.ACTIVE.name())
                        .active(true)
                        .build(),
                UserDTO.builder()
                        .username("test user 3")
                        .status(StatusModel.ACTIVE.name())
                        .active(true)
                        .build()
        );
    }

    @Test
    void findUsersShouldReturnListOfUserDTOs() {
        when(repository.findByStatus(anyString(), any(PageRequest.class))).thenReturn(users);
        IntStream.range(0,userDTOS.size())
                        .forEach(i ->
                                when(mapper.map(users.get(i), UserDTO.class))
                                        .thenReturn(userDTOS.get(i))
                        );

        List<UserDTO> userDTOs = userService.findUsers(PAGE, SIZE, FILTER, DIRECTION);

        Assertions.assertThat(userDTOs).hasSize(users.size());
    }

    @Test
    void findUsersWithUserDTOShouldReturnListOfUserDTOs() {
        when(mapper.map(userDTOS.get(0), User.class)).thenReturn(users.get(0));
        when(repository.findAll(any(Example.class))).thenReturn(users);
        when(mapper.map(users.get(0), UserDTO.class)).thenReturn(userDTOS.get(0));

        List<UserDTO> userDTOs = userService.findUsers(userDTOS.get(0));

        Assertions.assertThat(userDTOs).hasSize(3);
    }

    @Test
    void findUserShouldReturnUserDTO() {
        when(repository.findByUsernameAndStatus(anyString(), anyString())).thenReturn(Optional.of(users.get(0)));
        when(mapper.map(users.get(0), UserDTO.class)).thenReturn(userDTOS.get(0));

        UserDTO userDTO = userService.findUser(users.get(0).getUsername());

        Assertions.assertThat(userDTO).isNotNull();
    }

    @Test
    void findUserShouldThrowExceptionWhenUserNotFound() {
        when(repository.findByUsernameAndStatus(anyString(), anyString())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.findUser(users.get(0).getUsername()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining(USER_NOT_FOUND.toString());
    }

    @Test
    void saveUserShouldReturnUserDTO() {
        when(mapper.map(userDTOS.get(0), User.class)).thenReturn(users.get(0));
        when(repository.save(users.get(0))).thenReturn(users.get(0));
        when(mapper.map(users.get(0), UserDTO.class)).thenReturn(userDTOS.get(0));

        UserDTO savedUserDTO = userService.saveUser(userDTOS.get(0));

        Assertions.assertThat(savedUserDTO).isNotNull();
    }

    @Test
    void updateUserShouldReturnUserDTO() {
        when(mapper.map(userDTOS.get(0), User.class)).thenReturn(users.get(0));
        when(repository.save(users.get(0))).thenReturn(users.get(0));
        when(mapper.map(users.get(0), UserDTO.class)).thenReturn(userDTOS.get(0));

        UserDTO updatedUserDTO = userService.updateUser(userDTOS.get(0).getUsername(), userDTOS.get(0));

        Assertions.assertThat(updatedUserDTO).isNotNull();
    }

    @Test
    void deleteUserShouldSetStatusToDelete() {
        when(repository.existsById(anyString()))
                .thenReturn(true);
        doNothing().when(repository).setStatus(anyString(),anyString());

        userService.deleteUser(userDTOS.get(0).getUsername());

        verify(repository)
                .existsById(anyString());
        verify(repository)
                .setStatus(anyString(),anyString());
    }
    @Test
    void deleteUserShouldThrowServiceException() {
        when(repository.existsById(anyString()))
                .thenReturn(false);
        Assertions.assertThatThrownBy(() -> userService.deleteUser(userDTOS.get(0).getUsername()))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining(USER_NOT_FOUND.toString());
    }
}