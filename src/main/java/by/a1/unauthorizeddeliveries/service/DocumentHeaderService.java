package by.a1.unauthorizeddeliveries.service;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.DocumentHeaderDTO;

public interface DocumentHeaderService {
    DocumentHeaderDTO save(DocumentHeaderDTO header) throws ServiceException;
}
