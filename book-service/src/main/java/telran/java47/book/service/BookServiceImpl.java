package telran.java47.book.service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java47.book.dao.AuthorRepository;
import telran.java47.book.dao.BookRepository;
import telran.java47.book.dao.PublisherRepository;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.dto.exceptions.EntityNotFoundException;
import telran.java47.book.model.Author;
import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;
	
	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if(bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		//Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		//Authors
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
						.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book,  BookDto.class);
	}

	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return modelMapper.map(book,  BookDto.class);
	}

	@Override
	@Transactional
	public BookDto updateTitleBook(String isbn, String newTitle) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(newTitle);
		return modelMapper.map(book,  BookDto.class);
	}

	@Override
	public Iterable<AuthorDto> getAuthorsByIsdn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors()
				.stream()
				.map(a -> modelMapper.map(a,  AuthorDto.class))
				.collect(Collectors.toList());	
	}

	@Override
	@Transactional
	public Iterable<BookDto> getBooksByAuthor(String author) {
		
		return  bookRepository.findBooksByAuthor(author)
				.map(book -> modelMapper.map(book,  BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Iterable<BookDto> getBooksByPublisher(String publisher) {
		Publisher publish = publisherRepository.findById(publisher).orElseThrow(EntityNotFoundException::new);
		return bookRepository.findBooksByPublisher(publish)
		.map(book -> modelMapper.map(book,  BookDto.class))
		.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Iterable<String> getPublishersByAuthor(String author) {
		ArrayList<BookDto> books = (ArrayList<BookDto>) getBooksByAuthor(author);
		return books
			.stream()
			.map(book -> book.getPublisher())
			.collect(Collectors.toSet());		 
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String author) {
		Author deletedAuthor = authorRepository.findById(author).orElseThrow(EntityNotFoundException::new);
		Iterable<Book> books = bookRepository.findBooksByAuthor(author)
				.collect(Collectors.toList());
		bookRepository.deleteAll(books);
		authorRepository.deleteById(author);
		return modelMapper.map(deletedAuthor,  AuthorDto.class);
	}
	
	

}
