package za.co.aaronfacoline.gictask.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.aaronfacoline.gictask.dtos.BookRequestDTO;
import za.co.aaronfacoline.gictask.dtos.BookResponseDTO;
import za.co.aaronfacoline.gictask.services.BookManagementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookManagementService bookManagementService;

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody BookRequestDTO bookRequestDTO) {
        boolean added = bookManagementService.addBook(bookRequestDTO);
        if (!added) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        BookResponseDTO book = bookManagementService.getBook(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(book);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO bookRequestDTO){

        BookResponseDTO bookResponseDTO = bookManagementService.updateBook(bookRequestDTO, id);

        if(bookResponseDTO == null){
            return ResponseEntity.notFound().build();
        }
        else if(bookResponseDTO.getId() == -1L){
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(bookResponseDTO);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id){
        BookResponseDTO book = bookManagementService.deleteBook(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(book);
        }
    }

}
