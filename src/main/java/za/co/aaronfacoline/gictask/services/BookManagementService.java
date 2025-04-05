package za.co.aaronfacoline.gictask.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import za.co.aaronfacoline.gictask.dtos.BookRequestDTO;
import za.co.aaronfacoline.gictask.dtos.BookResponseDTO;
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
     * @param bookRequestDTO the data transfer object containing the details of the book to be added,
     *                such as the title and author.
     */
    @Transactional
    public void addBook(BookRequestDTO bookRequestDTO) {

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookRequestDTO.getTitle());
        bookEntity.setAuthor(bookRequestDTO.getAuthor());
        bookEntity = bookRepository.save(bookEntity);
        bookEntity.setIsbn(isbnGenerator.generateISBN(bookEntity.getId()));

    }

    /**
     * Retrieves a book based on its ID.
     *
     * @param id the ID of the book to retrieve
     * @return a {@code BookResponseDTO} containing the details of the book if found,
     *         or {@code null} if no book with the given ID exists.
     */
    public BookResponseDTO getBook(Long id) {
        BookEntity bookEntity = bookRepository.findById(id).orElse(null);
        if (bookEntity == null) {
            return null;
        }
        return bookEntityToBookDTO(bookEntity);
    }

    /**
     * Updates an existing book in the repository with new details provided in the request DTO.
     *
     * @param bookRequestDTO the data transfer object containing updated details of the book,
     *                       including the title, author, and ISBN.
     * @param id the ID of the book to be updated.
     * @return a {@code BookResponseDTO} containing the updated details of the book if the book exists,
     *         or {@code null} if no book with the given ID is found.
     */
    public BookResponseDTO updateBook(BookRequestDTO bookRequestDTO, Long id) {

        BookEntity bookEntity = bookRepository.findById(id).orElse(null);
        if (bookEntity == null) {
            return null;
        }
        if(!isbnGenerator.validateISBN(bookRequestDTO.getIsbn())){
            BookEntity invalidISBN = new BookEntity();
            invalidISBN.setId(-1L);
            return bookEntityToBookDTO(invalidISBN);
        }

        bookEntity.setTitle(bookRequestDTO.getTitle());
        bookEntity.setAuthor(bookRequestDTO.getAuthor());
        bookEntity.setIsbn(bookRequestDTO.getIsbn());
        bookEntity = bookRepository.save(bookEntity);
        return bookEntityToBookDTO(bookEntity);
    }

    /**
     * Converts a {@code BookEntity} object into a {@code BookResponseDTO} object.
     *
     * @param bookEntity the {@code BookEntity} instance to be converted
     * @return a {@code BookResponseDTO} instance containing the corresponding data of the provided {@code BookEntity}
     */
    private BookResponseDTO bookEntityToBookDTO(BookEntity bookEntity) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(bookEntity.getId());
        bookResponseDTO.setTitle(bookEntity.getTitle());
        bookResponseDTO.setAuthor(bookEntity.getAuthor());
        bookResponseDTO.setIsbn(bookEntity.getIsbn());
        return bookResponseDTO;
    }
}
