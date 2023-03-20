package by.a1.unauthorizeddeliveries.service.impl;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.model.CsvPostingModel;
import by.a1.unauthorizeddeliveries.model.ItemDTO;
import by.a1.unauthorizeddeliveries.service.ItemService;
import by.a1.unauthorizeddeliveries.service.PostingService;
import by.a1.unauthorizeddeliveries.util.impl.CsvPostingParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostingParseServiceTest {
    @InjectMocks
    private PostingParseService postingParseService;

    @Mock
    private PostingService postingService;

    @Mock
    private ItemService itemService;

    @Mock
    private CsvPostingParser parser;
    private List<CsvPostingModel> modelList;

    @BeforeEach
    void setup() {
        modelList = List.of(
                CsvPostingModel.builder()
                        .postingNumber("6777727662")
                        .materialDescription("\tHeadphones JBL C100SIU BLK black")
                        .item("\t2")
                        .currency("\tBYN")
                        .measurementUnit("\tpc")
                        .quantity("\t3")
                        .amount("\t38,1")
                        .username("\tNLIMONOV")
                        .postingDate("\t25.07.2020")
                        .contractDate("\t25.07.2020")
                        .build(),
                CsvPostingModel.builder()
                        .postingNumber("6777727662")
                        .materialDescription("\tHeadphones JBL C100SIU BLK black")
                        .item("\t2")
                        .currency("\tBYN")
                        .measurementUnit("\tpc")
                        .quantity("\t3")
                        .amount("\t38,1")
                        .username("\tNLIMONOV")
                        .postingDate("\t25.07.2020")
                        .contractDate("\t25.07.2020")
                        .build(),
                CsvPostingModel.builder()
                        .postingNumber("6777727662")
                        .materialDescription("\tHeadphones JBL C100SIU BLK black")
                        .item("\t2")
                        .currency("\tBYN")
                        .measurementUnit("\tpc")
                        .quantity("\t3")
                        .amount("\t38,1")
                        .username("\tNLIMONOV")
                        .postingDate("\t25.07.2020")
                        .contractDate("\t25.07.2020")
                        .build()
        );
    }
    @Test
    void parseToDBShouldCorrectSaveWithCreateItem() throws IOException, ServiceException {
        when(parser.parse(any())).thenReturn(modelList);
        when(itemService.findItem(any(String.class), any(Integer.class))).thenReturn(Optional.empty());

        postingParseService.parseToDB();

        verify(itemService,times(3)).findItem(any(String.class), any(Integer.class));
    }
    @Test
    void parseToDBShouldCorrectSaveWithFindItem() throws IOException, ServiceException {
        when(parser.parse(any())).thenReturn(modelList);
        when(itemService.findItem(any(String.class), any(Integer.class))).thenReturn(Optional.of(new ItemDTO()));

        postingParseService.parseToDB();

        verify(itemService,times(3)).findItem(any(String.class), any(Integer.class));
    }
}