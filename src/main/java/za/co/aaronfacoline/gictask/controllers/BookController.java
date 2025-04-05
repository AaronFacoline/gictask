package za.co.aaronfacoline.gictask.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        BookDTO book = bookManagementService.getBook(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(book);
        }
    }

}
