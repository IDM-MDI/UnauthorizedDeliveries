package by.a1.unauthorizeddeliveries.controller;

import by.a1.unauthorizeddeliveries.model.ItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @GetMapping
    @Operation(
            summary = "Items",
            description = "API Point made for return Items page"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Items found"
    )
    public List<ItemDTO> getItems(@Parameter(description = "Page number(def: 0,min: 0)")
                                        @RequestParam(defaultValue = "0") @Min(0) int page,
                                        @Parameter(description = "Page size(def: 10, min: 1)")
                                        @RequestParam(defaultValue = "10") @Min(1) int size,
                                        @Parameter(description = "Filter by field(def: id)")
                                        @RequestParam(defaultValue = "id") @NotBlank String filter,
                                        @Parameter(description = "asc or desc(def: asc)")
                                        @RequestParam(defaultValue = "asc") @NotBlank String direction) throws ServiceException {
        return service.findItems(page,size,filter,direction);
    }
    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Item by ID",
            description = "API Point made for return Item by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Item found"
    )
    public ItemDTO getItem(@Parameter(description = "Item ID") @PathVariable @Min(1) long id) throws ServiceException {
        return service.findItem(id);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Item by search",
            description = "API Point made for return Items by search"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Item found"
    )
    public List<ItemDTO> findItem(@Parameter(description = "Items search by") ItemDTO item) throws ServiceException {
        return service.findItems(item);
    }

    @PostMapping
    @Operation(
            summary = "Save Item",
            description = "API Point made for saving Item"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Item created"
    )
    public ItemDTO saveItem(@RequestBody @Valid ItemDTO item) throws ServiceException {
        return service.saveItem(item);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update Item",
            description = "API Point made for updating Item"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Item updated"
    )
    public ItemDTO updateItem(@PathVariable @Min(1) long id,
                                    @RequestBody @Valid ItemDTO item) throws ServiceException {
        return service.updateItem(id,item);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Item",
            description = "API Point made for deleting Item"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Item deleted"
    )

    public ResponseEntity<String> deleteItems(@PathVariable @Min(1) long id) throws ServiceException {
        service.deleteItem(id);
        return ResponseEntity.ok("The Item successfully was deleted");
    }
}
