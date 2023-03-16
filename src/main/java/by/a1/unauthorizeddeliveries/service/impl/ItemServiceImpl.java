package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.Item;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.repository.ItemRepository;
import by.a1.unauthorizeddeliveries.service.ItemService;
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
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final ModelMapper mapper;
    @Override
    public List<ItemDTO> findItems(int page, int size, String filter, String direction) {
        return repository.findAll(PageRequest.of(page,size, getDirection(Sort.by(filter),direction)))
                .stream()
                .map(user -> mapper.map(user, ItemDTO.class))
                .toList();
    }

    @Override
    public ItemDTO findItem(long id) throws ServiceException {
        return repository.findById(id)
                .map(user -> mapper.map(user, ItemDTO.class))
                .orElseThrow(() -> new ServiceException("User not found"));
    }

    @Override
    public List<ItemDTO> findItems(ItemDTO item) {
        return repository.findAll(Example.of(mapper.map(item, Item.class)))
                .stream()
                .map(i -> mapper.map(i, ItemDTO.class))
                .toList();
    }

    @Override
    public ItemDTO saveItem(ItemDTO item) {
        Item savedItem = repository.save(mapper.map(item, Item.class));
        return mapper.map(savedItem,ItemDTO.class);
    }

    @Override
    public ItemDTO updateItem(long id, ItemDTO item) {
        item.setId(id);
        return saveItem(item);
    }

    @Override
    public void deleteItem(long id) throws ServiceException {
        if(!repository.existsById(id)) {
            throw new ServiceException();
        }
        repository.deleteById(id);
    }
}
