package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.User;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.StatusModel;
import by.a1.unauthorizeddeliveries.model.UserDTO;
import by.a1.unauthorizeddeliveries.repository.UserRepository;
import by.a1.unauthorizeddeliveries.service.UserService;
import by.a1.unauthorizeddeliveries.util.ExampleUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.a1.unauthorizeddeliveries.exception.ExceptionStatus.USER_NOT_FOUND;
import static by.a1.unauthorizeddeliveries.util.SortDirectionUtil.getDirection;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement(proxyTargetClass = true)
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;
    @Override
    public List<UserDTO> findUsers(int page, int size, String filter, String direction) {
        return repository.findByStatus(StatusModel.ACTIVE.name(), PageRequest.of(page,size, getDirection(Sort.by(filter),direction)))
                .stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public List<UserDTO> findUsers(UserDTO user) {
        return repository.findAll(Example.of(mapper.map(user, User.class), ExampleUtil.ENTITY_SEARCH_MATCHER))
                .stream()
                .map(u -> mapper.map(u, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO findUser(String username) {
        return repository.findByUsernameAndStatus(username,StatusModel.ACTIVE.name())
                .map(user -> mapper.map(user, UserDTO.class))
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND.toString()));
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        setDefaultUser(user);
        User savedUser = repository.save(mapper.map(user, User.class));
        return mapper.map(savedUser,UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String username, UserDTO user) {
        user.setUsername(username);
        return saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        if(!repository.existsById(username)) {
            throw new ServiceException(USER_NOT_FOUND.toString());
        }
        repository.setStatus(username, StatusModel.DELETED.name());
    }
    private void setDefaultUser(UserDTO user) {
        user.setActive(true);
        user.setStatus(StatusModel.ACTIVE.name());
    }
}
