package za.co.aaronfacoline.gictask.services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
     *                       such as the title and author.
     */
    @Transactional
    public boolean addBook(BookRequestDTO bookRequestDTO) {

        if (bookRequestDTO.getTitle() == null || bookRequestDTO.getAuthor() == null ||
                bookRequestDTO.getTitle().isBlank() || bookRequestDTO.getAuthor().isBlank() ||
                bookRequestDTO.getTitle().length() > 100 || bookRequestDTO.getAuthor().length() > 50) {
            return false;
        }

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookRequestDTO.getTitle());
        bookEntity.setAuthor(bookRequestDTO.getAuthor());
        bookEntity = bookRepository.save(bookEntity);
        bookEntity.setIsbn(isbnGenerator.generateISBN(bookEntity.getId()));
        return true;

    }

    /**
     * Retrieves a book based on its ID.
     *
     * @param id the ID of the book to retrieve
     * @return a {@code BookResponseDTO} containing the details of the book if found,
     * or {@code null} if no book with the given ID exists.
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
     * @param id             the ID of the book to be updated.
     * @return a {@code BookResponseDTO} containing the updated details of the book if the book exists,
     * or {@code null} if no book with the given ID is found.
     */
    public BookResponseDTO updateBook(BookRequestDTO bookRequestDTO, Long id) {

        BookEntity bookEntity = bookRepository.findById(id).orElse(null);
        if (bookEntity == null) {
            return null;
        }
        if (!isbnGenerator.validateISBN(bookRequestDTO.getIsbn()) ||
                bookRequestDTO.getTitle() == null || bookRequestDTO.getAuthor() == null ||
                bookRequestDTO.getTitle().isBlank() || bookRequestDTO.getAuthor().isBlank() ||
                bookRequestDTO.getTitle().length() > 100 || bookRequestDTO.getAuthor().length() > 50) {
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
     * Deletes a book by its identifier.
     *
     * @param id the unique identifier of the book to be deleted
     * @return a BookResponseDTO object representing the deleted book's information,
     *         or null if the book is not found
     */
    public BookResponseDTO deleteBook(Long id) {
        BookEntity bookEntity = bookRepository.findById(id).orElse(null);
        if (bookEntity == null) {
            return null;
        }
        bookRepository.deleteById(id);
        return bookEntityToBookDTO(bookEntity);
    }

    public Page<BookResponseDTO> getBooks(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BookEntity> bookEntities = bookRepository.findAll(pageRequest);
        if (bookEntities.getSize() == 0) {
            return null;
        }
        return bookEntities.map(this::bookEntityToBookDTO);
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
