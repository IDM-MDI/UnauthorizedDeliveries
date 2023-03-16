package by.a1.unauthorizeddeliveries.controller;

import by.a1.unauthorizeddeliveries.model.UserDTO;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    @Operation(
            summary = "Users",
            description = "API Point made for return users page"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Users found"
    )
    public List<UserDTO> getUsers(@Parameter(description = "Page number(def: 0,min: 0)")
                                 @RequestParam(defaultValue = "0") @Min(0) int page,
                                  @Parameter(description = "Page size(def: 10, min: 1)")
                                 @RequestParam(defaultValue = "10") @Min(1) int size,
                                  @Parameter(description = "Filter by field(def: id)")
                                 @RequestParam(defaultValue = "id") @NotBlank String filter,
                                  @Parameter(description = "asc or desc(def: asc)")
                                 @RequestParam(defaultValue = "asc") @NotBlank String direction) throws ServiceException {
        return service.findUsers(page,size,filter,direction);
    }
    @GetMapping(value = "/{id}")
    @Operation(
            summary = "User by ID",
            description = "API Point made for return user by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User found"
    )
    public UserDTO getUser(@Parameter(description = "User ID") @PathVariable @Min(1) long id) throws ServiceException {
        return service.findUser(id);
    }

    @GetMapping("/search")
    @Operation(
            summary = "User by search",
            description = "API Point made for return users by search"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User found"
    )
    public List<UserDTO> findUser(@Parameter(description = "Users search by") UserDTO user) throws ServiceException {
        return service.findUsers(user);
    }

    @PostMapping
    @Operation(
            summary = "Save User",
            description = "API Point made for saving User"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User created"
    )
    public UserDTO saveUser(@RequestBody @Valid UserDTO user) throws ServiceException {
        return service.saveUser(user);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update User",
            description = "API Point made for updating User"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User updated"
    )
    public UserDTO updateUser(@PathVariable @Min(1) long id,
                              @RequestBody @Valid UserDTO user) throws ServiceException {
        return service.updateUser(id,user);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete User",
            description = "API Point made for deleting user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User deleted"
    )
    
    public ResponseEntity<String> deleteUsers(@PathVariable @Min(1) long id) throws ServiceException {
        service.deleteUser(id);
        return ResponseEntity.ok("The user successfully was deleted");
    }
}
