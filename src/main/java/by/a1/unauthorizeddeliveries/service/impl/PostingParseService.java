package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.CsvPostingModel;
import by.a1.unauthorizeddeliveries.model.DocumentHeaderDTO;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.model.MaterialDTO;
import by.a1.unauthorizeddeliveries.model.PostingRequestDTO;
import by.a1.unauthorizeddeliveries.service.ItemService;
import by.a1.unauthorizeddeliveries.service.ParseService;
import by.a1.unauthorizeddeliveries.service.PostingService;
import by.a1.unauthorizeddeliveries.util.impl.CsvPostingParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PostingParseService implements ParseService {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Value("${csv.path.postings}")
    private String path;
    private final PostingService postingService;
    private final ItemService itemService;
    private final CsvPostingParser parser;
    @Override
    public void parseToDB() throws IOException, ServiceException {
        parser.parse(path)
                .stream()
                .peek(CsvPostingModel::trim)
                .map(model -> PostingRequestDTO.builder()
                        .documentHeader(createHeader(model))
                        .material(createMaterial(model))
                        .build())
                .forEach(postingService::savePosting);
    }
    private DocumentHeaderDTO createHeader(CsvPostingModel model) {

        return DocumentHeaderDTO.builder()
                .username(model.getUsername())
                .postingNumber(Math.abs(Long.parseLong(model.getPostingNumber())))
                .contractDate(LocalDate.parse(model.getContractDate(),DTF))
                .postingDate(LocalDate.parse(model.getPostingDate(),DTF))
                .build();
    }
    private MaterialDTO createMaterial(CsvPostingModel model) {
        return MaterialDTO.builder()
                .item(
                        itemService.findItem(model.getMaterialDescription(),Integer.parseInt(model.getAmount().replace(",","")))
                                .orElse(itemService.saveItem(createItem(model)))
                )
                .itemPosition(Math.abs(Long.parseLong(model.getItem())))
                .quantity(Math.abs(Integer.parseInt(model.getQuantity())))
                .measurementUnit(model.getMeasurementUnit())
                .build();
    }
    private ItemDTO createItem(CsvPostingModel model) {
        return ItemDTO.builder()
                .description(model.getMaterialDescription())
                .amount(Math.abs(Integer.parseInt(model.getAmount().replace(",",""))))
                .currency(model.getCurrency())
                .build();
    }
}
