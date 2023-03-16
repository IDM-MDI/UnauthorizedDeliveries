package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.User;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.UserDTO;
import by.a1.unauthorizeddeliveries.repository.UserRepository;
import by.a1.unauthorizeddeliveries.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.a1.unauthorizeddeliveries.util.SortDirectionUtil.getDirection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;
    @Override
    public List<UserDTO> findUsers(int page, int size, String filter, String direction) {
        return repository.findAll(PageRequest.of(page,size, getDirection(Sort.by(filter),direction)))
                .stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public List<UserDTO> findUsers(UserDTO user) {
        return repository.findAll(Example.of(mapper.map(user, User.class)))
                .stream()
                .map(u -> mapper.map(u, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO findUser(String id) throws ServiceException {
        return repository.findById(id)
                .map(user -> mapper.map(user, UserDTO.class))
                .orElseThrow(() -> new ServiceException("User not found"));
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        setDefaultUser(user);
        User savedUser = repository.save(mapper.map(user, User.class));
        return mapper.map(savedUser,UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String id, UserDTO user) {
        user.setUsername(id);
        return saveUser(user);
    }

    @Override
    public void deleteUser(String username) throws ServiceException {
        if(!repository.existsById(username)) {
            throw new ServiceException();
        }
        repository.deleteById(username);
    }
    private void setDefaultUser(UserDTO user) {
        user.setActive(true);
    }
}
