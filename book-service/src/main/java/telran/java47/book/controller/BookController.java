package telran.java47.book.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController{

	final BookService bookService;
	
	@PostMapping("/book")
	public boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

	@GetMapping("/book/{isbn}")
	public BookDto findBookByIsbn(@PathVariable String isbn) {
		
		return bookService.findBookByIsbn(isbn);
	}
	
	@DeleteMapping ("/book/{isbn}")
	public BookDto deleteBookByIsdn(@PathVariable String isbn){
		return	bookService.removeBook(isbn);
	}
	
	@PutMapping ("/book/{isbn}/title/{newTitle}")
	public BookDto updateTitleBook(@PathVariable String isbn, @PathVariable String newTitle){
		return	bookService.updateTitleBook(isbn,  newTitle);
	}
	
	@GetMapping("authors/book/{isbn}")
	public Iterable<AuthorDto> getAuthorsByIsdn(@PathVariable String isbn){
		return	bookService.getAuthorsByIsdn(isbn);
	}
	
	@GetMapping("/books/author/{author}")
	public Iterable<BookDto> getBooksByAuthor(@PathVariable String author){
		return	bookService.getBooksByAuthor(author);
	}
	
	@GetMapping("/books/publisher/{publisher}")
	public Iterable<BookDto> getBooksByPublisher(@PathVariable String publisher){
		return	bookService.getBooksByPublisher(publisher);	
	}
	
	@GetMapping("/publishers/author/{author}")
	public Iterable<String> getPublishersByAuthor(@PathVariable String author){
		return	bookService.getPublishersByAuthor(author);	
	}
	
	@DeleteMapping ("/author/{author}")
	public AuthorDto deleteAuthor(@PathVariable String author){
		return	bookService.removeAuthor(author);
	}
}
