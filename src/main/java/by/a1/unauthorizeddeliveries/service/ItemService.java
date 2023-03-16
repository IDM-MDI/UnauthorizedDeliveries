package by.a1.unauthorizeddeliveries.service;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> findItems(int page, int size, String filter, String direction);

    ItemDTO findItem(long id) throws ServiceException;

    List<ItemDTO> findItems(ItemDTO item);

    ItemDTO saveItem(ItemDTO item);

    ItemDTO updateItem(long id, ItemDTO item);

    void deleteItem(long id) throws ServiceException;
}
