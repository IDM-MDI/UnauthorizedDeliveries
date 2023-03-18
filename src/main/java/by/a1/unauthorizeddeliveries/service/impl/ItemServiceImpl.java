package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.entity.Item;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.model.StatusModel;
import by.a1.unauthorizeddeliveries.repository.ItemRepository;
import by.a1.unauthorizeddeliveries.service.ItemService;
import by.a1.unauthorizeddeliveries.util.ExampleUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static by.a1.unauthorizeddeliveries.exception.ExceptionStatus.ENTITY_NOT_FOUND;
import static by.a1.unauthorizeddeliveries.util.SortDirectionUtil.getDirection;

@Service
@EnableTransactionManagement(proxyTargetClass = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final ModelMapper mapper;

    public ItemServiceImpl(ItemRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        mapper.createTypeMap(ItemDTO.class, Item.class)
                .addMappings(mapping ->
                        mapping.map(source -> BigDecimal.valueOf(source.getAmount()), Item::setAmount));
        mapper.createTypeMap(Item.class, ItemDTO.class)
                .addMappings(mapping ->
                        mapping.map(Item::getAmount, ItemDTO::setAmount));
    }

    @Override
    public List<ItemDTO> findItems(int page, int size, String filter, String direction) {
        return repository.findByStatus(StatusModel.ACTIVE.name(), PageRequest.of(page,size, getDirection(Sort.by(filter),direction)))
                .stream()
                .map(user -> mapper.map(user, ItemDTO.class))
                .toList();
    }

    @Override
    public ItemDTO findItem(long id) {
        return repository.findByIdAndStatus(id,StatusModel.ACTIVE.name())
                .map(user -> mapper.map(user, ItemDTO.class))
                .orElseThrow(() -> new ServiceException(ENTITY_NOT_FOUND.toString()));
    }

    @Override
    public Optional<ItemDTO> findItem(String description,int amount) {
        return repository.findByDescriptionAndAmount(description,amount)
                .map(user -> mapper.map(user, ItemDTO.class));
    }

    @Override
    public List<ItemDTO> findItems(ItemDTO item) {
        return repository.findAll(Example.of(mapper.map(item, Item.class), ExampleUtil.ENTITY_SEARCH_MATCHER))
                .stream()
                .map(i -> mapper.map(i, ItemDTO.class))
                .toList();
    }

    @Override
    public ItemDTO saveItem(ItemDTO item) {
        return findItem(item.getDescription(), item.getAmount()).orElseGet(() -> saveNewItem(item));
    }

    @Override
    public ItemDTO updateItem(long id, ItemDTO item) {
        item.setId(id);
        return saveItem(item);
    }

    @Override
    @Transactional
    public void deleteItem(long id) {
        if(!repository.existsById(id)) {
            throw new ServiceException(ENTITY_NOT_FOUND.toString());
        }
        repository.setStatus(id, StatusModel.DELETED.name());
    }

    private ItemDTO saveNewItem(ItemDTO item) {
        Item entity = mapper.map(item, Item.class);
        setDefaultItem(entity);
        Item savedItem = repository.save(entity);
        return mapper.map(savedItem, ItemDTO.class);
    }
    private void setDefaultItem(Item item) {
        item.setStatus(StatusModel.ACTIVE.name());
    }
}
