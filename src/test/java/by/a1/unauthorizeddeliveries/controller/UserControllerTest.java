package by.a1.unauthorizeddeliveries.controller;

import by.a1.unauthorizeddeliveries.UnauthorizedDeliveriesApplication;
import by.a1.unauthorizeddeliveries.exception.ExceptionStatus;
import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.UserDTO;
import by.a1.unauthorizeddeliveries.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(classes = UnauthorizedDeliveriesApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {
    private static final int PAGE = 0;
    private static final int SIZE = 10;
    private static final String FILTER = "username";
    private static final String DIRECTION = "asc";
    private static final MediaType HAL_JSON = MediaType.valueOf("application/hal+json");
    private static final MediaType TEXT_PLAIN = MediaType.parseMediaType("text/plain;charset=UTF-8");
    @MockBean
    private UserService userService;
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;

    private List<UserDTO> userDTOS;
    private ObjectMapper mapper;
    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        userDTOS = List.of(
                UserDTO.builder()
                        .username("user1")
                        .job("job")
                        .application("app")
                        .department("dep")
                        .status("ACTIVE")
                        .active(true)
                        .build(),
                UserDTO.builder()
                        .username("user2")
                        .job("job")
                        .application("app")
                        .department("dep")
                        .status("ACTIVE")
                        .active(true)
                        .build(),
                UserDTO.builder()
                        .username("user3")
                        .job("job")
                        .application("app")
                        .department("dep")
                        .status("ACTIVE")
                        .active(true)
                        .build()
        );
    }

    @Test
    void findUsersShouldReturnEntityList() throws Exception {
        UserDTO user = userDTOS.get(0);
        when(userService.findUsers(PAGE,SIZE,FILTER, DIRECTION))
                .thenReturn(userDTOS);
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].username").value(user.getUsername()))
                .andExpect(jsonPath("$.[0].application").value(user.getApplication()))
                .andExpect(jsonPath("$.[0].department").value(user.getDepartment()))
                .andExpect(jsonPath("$.[0].job").value(user.getJob()))
                .andExpect(jsonPath("$.[0].active").value(user.getActive()));
    }
    @Test
    void findUsersShouldThrowServiceException() throws Exception {
        UserDTO user = userDTOS.get(0);
        when(userService.findUser(user.getUsername()))
                .thenThrow(new ServiceException(ExceptionStatus.ENTITY_NOT_FOUND.toString()));
        mockMvc.perform(get("/api/v1/users/" + user.getUsername()))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findUserShouldReturnEntity() throws Exception {
        UserDTO user = userDTOS.get(0);
        when(userService.findUser(user.getUsername()))
                .thenReturn(user);
        mockMvc.perform(get("/api/v1/users/" + user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.application").value(user.getApplication()))
                .andExpect(jsonPath("$.department").value(user.getDepartment()))
                .andExpect(jsonPath("$.job").value(user.getJob()))
                .andExpect(jsonPath("$.active").value(user.getActive()));
    }
    @Test
    void findUserShouldReturnEntityList() throws Exception {
        UserDTO search = UserDTO.builder()
                .job("job")
                .application("app")
                .department("dep")
                .build();
        UserDTO user = userDTOS.get(0);
        when(userService.findUsers(search))
                .thenReturn(userDTOS);
        mockMvc.perform(get("/api/v1/users/search?job=" + search.getJob()
                        + "&application=" + search.getApplication()
                        + "&department=" + search.getDepartment()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].username").value(user.getUsername()))
                .andExpect(jsonPath("$.[0].application").value(user.getApplication()))
                .andExpect(jsonPath("$.[0].department").value(user.getDepartment()))
                .andExpect(jsonPath("$.[0].job").value(user.getJob()))
                .andExpect(jsonPath("$.[0].active").value(user.getActive()));
    }
    @Test
    void saveUserShouldReturnEntity() throws Exception {
        UserDTO user = userDTOS.get(0);
        when(userService.saveUser(any(UserDTO.class)))
                .thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(HAL_JSON)
                        .content(mapper.writeValueAsString(user))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.application").value(user.getApplication()))
                .andExpect(jsonPath("$.department").value(user.getDepartment()))
                .andExpect(jsonPath("$.job").value(user.getJob()))
                .andExpect(jsonPath("$.active").value(user.getActive()));
    }
    @Test
    void saveUserShouldReturnThrowMethodArgumentNotValidException() throws Exception {
        UserDTO user = userDTOS.get(0);
        user.setUsername("");
        when(userService.saveUser(any(UserDTO.class)))
                .thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(HAL_JSON)
                        .content(mapper.writeValueAsString(user))
                )
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateUserShouldReturnEntity() throws Exception {
        UserDTO user = userDTOS.get(0);
        when(userService.updateUser(eq(user.getUsername()),any(UserDTO.class)))
                .thenReturn(user);

        mockMvc.perform(put("/api/v1/users/" + user.getUsername())
                        .contentType(HAL_JSON)
                        .content(mapper.writeValueAsString(user))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.application").value(user.getApplication()))
                .andExpect(jsonPath("$.department").value(user.getDepartment()))
                .andExpect(jsonPath("$.job").value(user.getJob()))
                .andExpect(jsonPath("$.active").value(user.getActive()));
    }

    @Test
    void deleteUsersShouldReturnOK() throws Exception {
        UserDTO user = userDTOS.get(0);
        doNothing()
                .when(userService)
                .deleteUser(user.getUsername());

        mockMvc.perform(delete("/api/v1/users/" + user.getUsername())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("The user successfully was deleted"));
    }
}