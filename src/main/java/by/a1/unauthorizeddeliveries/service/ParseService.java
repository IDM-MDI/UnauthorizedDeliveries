package by.a1.unauthorizeddeliveries.service;

import by.a1.unauthorizeddeliveries.exception.ServiceException;

import java.io.IOException;

public interface ParseService {
    void parseToDB() throws IOException, ServiceException;
}
