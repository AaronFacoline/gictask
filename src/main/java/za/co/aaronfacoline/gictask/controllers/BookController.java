package za.co.aaronfacoline.gictask.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.aaronfacoline.gictask.dtos.BookDTO;
import za.co.aaronfacoline.gictask.services.BookManagementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookManagementService bookManagementService;

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody BookDTO bookDTO) {
        bookManagementService.addBook(bookDTO);
        return ResponseEntity.ok().build();
    }
}
