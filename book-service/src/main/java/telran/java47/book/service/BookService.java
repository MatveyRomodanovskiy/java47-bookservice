package telran.java47.book.service;

import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;

public interface BookService {
	boolean addBook (BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);

	BookDto removeBook(String isbn);

	BookDto updateTitleBook(String isbn, String newTitle);

	Iterable<AuthorDto> getAuthorsByIsdn(String isbn);

	Iterable<BookDto> getBooksByAuthor(String author);

	Iterable<BookDto> getBooksByPublisher(String publisher);

	Iterable<String> getPublishersByAuthor(String author);

	AuthorDto removeAuthor(String author);
}
