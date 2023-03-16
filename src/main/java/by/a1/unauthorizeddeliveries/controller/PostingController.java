package by.a1.unauthorizeddeliveries.controller;

import by.a1.unauthorizeddeliveries.model.PostingDTO;
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
@RequestMapping("/api/v1/posting")
@RequiredArgsConstructor
public class PostingController {
    private final PostingService service;

    @GetMapping
    @Operation(
            summary = "Postings",
            description = "API Point made for return Postings page"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Postings found"
    )
    public List<PostingDTO> getPostings(@Parameter(description = "Page number(def: 0,min: 0)")
                                  @RequestParam(defaultValue = "0") @Min(0) int page,
                                        @Parameter(description = "Page size(def: 10, min: 1)")
                                  @RequestParam(defaultValue = "10") @Min(1) int size,
                                        @Parameter(description = "Filter by field(def: id)")
                                  @RequestParam(defaultValue = "id") @NotBlank String filter,
                                        @Parameter(description = "asc or desc(def: asc)")
                                  @RequestParam(defaultValue = "asc") @NotBlank String direction) throws ServiceException {
        return service.findPostings(page,size,filter,direction);
    }
    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Posting by ID",
            description = "API Point made for return Posting by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posting found"
    )
    public PostingDTO getPosting(@Parameter(description = "Posting ID") @PathVariable @Min(1) long id) throws ServiceException {
        return service.findPosting(id);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Posting by search",
            description = "API Point made for return Postings by search"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posting found"
    )
    public List<PostingDTO> findPosting(@Parameter(description = "Postings search by") PostingDTO posting) throws ServiceException {
        return service.findPostings(posting);
    }

    @PostMapping
    @Operation(
            summary = "Save Posting",
            description = "API Point made for saving Posting"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Posting created"
    )
    public PostingDTO savePosting(@RequestBody @Valid PostingDTO posting) throws ServiceException {
        return service.savePosting(posting);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update Posting",
            description = "API Point made for updating Posting"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posting updated"
    )
    public PostingDTO updatePosting(@PathVariable @Min(1) long id,
                              @RequestBody @Valid PostingDTO posting) throws ServiceException {
        return service.updatePosting(id,posting);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Posting",
            description = "API Point made for deleting Posting"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posting deleted"
    )

    public ResponseEntity<String> deletePostings(@PathVariable @Min(1) long id) throws ServiceException {
        service.deletePosting(id);
        return ResponseEntity.ok("The Posting successfully was deleted");
    }
}
