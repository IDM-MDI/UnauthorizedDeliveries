package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.CsvLoginModel;
import by.a1.unauthorizeddeliveries.service.UserService;
import by.a1.unauthorizeddeliveries.util.impl.CsvLoginParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginParseServiceTest {
    @InjectMocks
    private LoginParseService loginParseService;

    @Mock
    private UserService userService;

    @Mock
    private CsvLoginParser parser;

    private List<CsvLoginModel> modelList;

    @BeforeEach
    void setup() {
        modelList = List.of(
                CsvLoginModel.builder()
                        .application("SAP")
                        .accountName("\tBORODZUTPNI")
                        .active("\tTrue")
                        .jobTitle("\tКомплектовщик товаров")
                        .department("\tГруппа складского хозяйства")
                        .build(),
                CsvLoginModel.builder()
                        .application("SAP")
                        .accountName("\tBORODZUTPNI")
                        .active("\tTrue")
                        .jobTitle("\tКомплектовщик товаров")
                        .department("\tГруппа складского хозяйства")
                        .build(),
                CsvLoginModel.builder()
                        .application("SAP")
                        .accountName("\tBORODZUTPNI")
                        .active("\tTrue")
                        .jobTitle("\tКомплектовщик товаров")
                        .department("\tГруппа складского хозяйства")
                        .build()
        );
    }
    @Test
    void parseToDBShouldCorrectSave() throws IOException, ServiceException {
        when(parser.parse(any())).thenReturn(modelList);
        when(userService.saveUser(any())).thenReturn(null);

        loginParseService.parseToDB();

        verify(userService,times(3)).saveUser(any());
    }
}