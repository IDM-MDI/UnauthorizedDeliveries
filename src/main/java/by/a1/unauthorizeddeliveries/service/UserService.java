package by.a1.unauthorizeddeliveries.service;

import by.a1.unauthorizeddeliveries.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findUsers(int page, int size, String filter, String direction);

    List<UserDTO> findUsers(UserDTO user);

    UserDTO findUser(long id);

    UserDTO saveUser(UserDTO user);

    UserDTO updateUser(long id, UserDTO user);

    void deleteUser(long id);
}
