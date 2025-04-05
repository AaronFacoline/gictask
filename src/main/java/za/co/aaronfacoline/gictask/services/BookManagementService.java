package za.co.aaronfacoline.gictask.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.aaronfacoline.gictask.dtos.BookDTO;
import za.co.aaronfacoline.gictask.entities.BookEntity;
import za.co.aaronfacoline.gictask.repositories.BookRepository;
import za.co.aaronfacoline.gictask.utils.ISBNGenerator;

/**
 * Service class for managing books in the system.
 * This class utilizes an instance of {@link BookRepository} for data storage
 * and retrieval and {@link ISBNGenerator} for generating unique ISBN numbers.
 */
@Service
public class BookManagementService {

    private final BookRepository bookRepository;
    private final ISBNGenerator isbnGenerator;

    public BookManagementService(BookRepository bookRepository, ISBNGenerator isbnGenerator) {
        this.bookRepository = bookRepository;
        this.isbnGenerator = isbnGenerator;
    }

    /**
     * Adds a new book to the repository and assigns a generated ISBN to it.
     *
     * @param bookDTO the data transfer object containing the details of the book to be added,
     *                such as the title and author.
     */
    @Transactional
    public void addBook(BookDTO bookDTO) {

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity = bookRepository.save(bookEntity);
        bookEntity.setIsbn(isbnGenerator.generateISBN(bookEntity.getId()));

    }

    private BookDTO bookEntityToBookDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setAuthor(bookEntity.getAuthor());
        bookDTO.setIsbn(bookEntity.getIsbn());
        return bookDTO;
    }

    private BookEntity bookDTOToBookEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookDTO.getId());
        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setIsbn(bookDTO.getIsbn());
        return bookEntity;
    }

}
