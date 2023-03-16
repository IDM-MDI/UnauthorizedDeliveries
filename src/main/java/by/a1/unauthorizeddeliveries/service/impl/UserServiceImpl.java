package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.model.UserDTO;
import by.a1.unauthorizeddeliveries.repository.UserRepository;
import by.a1.unauthorizeddeliveries.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.a1.unauthorizeddeliveries.util.SortDirectionHandler.getDirection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    @Override
    public List<UserDTO> findUsers(int page, int size, String filter, String direction) {
        return repository.findAll(PageRequest.of(page,size, getDirection(Sort.by(filter),direction)));
    }

    @Override
    public List<UserDTO> findUsers(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO findUser(long id) {
        return null;
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO updateUser(long id, UserDTO user) {
        return null;
    }

    @Override
    public void deleteUser(long id) {

    }
}
