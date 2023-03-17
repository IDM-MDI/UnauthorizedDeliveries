package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.model.CsvLoginModel;
import by.a1.unauthorizeddeliveries.model.UserDTO;
import by.a1.unauthorizeddeliveries.service.ParseService;
import by.a1.unauthorizeddeliveries.service.UserService;
import by.a1.unauthorizeddeliveries.util.impl.CsvLoginParser;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class LoginParseService implements ParseService {
    @Value("${csv.path.logins}")
    private String path;
    private final CsvLoginParser parser;
    private final UserService service;

    public LoginParseService(CsvLoginParser parser, UserService service) {
        this.parser = parser;
        this.service = service;
    }

    @Override
    public void parseToDB() throws IOException {
        List<CsvLoginModel> parsedModels = parser.parse(path);
        parsedModels.stream()
                .peek(CsvLoginModel::trim)
                .map(csvLoginModel ->
                        UserDTO.builder()
                                .application(csvLoginModel.getApplication())
                                .username(csvLoginModel.getAccountName())
                                .department(csvLoginModel.getDepartment())
                                .job(csvLoginModel.getJobTitle())
                                .active(Boolean.getBoolean(csvLoginModel.getActive()))
                                .build()
                )
                .forEach(service::saveUser);
    }
}
