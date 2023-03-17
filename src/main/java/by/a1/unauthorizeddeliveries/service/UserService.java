package by.a1.unauthorizeddeliveries.service;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findUsers(int page, int size, String filter, String direction);
    List<UserDTO> findUsers(UserDTO user);
    UserDTO findUser(String username) throws ServiceException;
    UserDTO saveUser(UserDTO user);
    UserDTO updateUser(String username, UserDTO user);
    void deleteUser(String username) throws ServiceException;
}
