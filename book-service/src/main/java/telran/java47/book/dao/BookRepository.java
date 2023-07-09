package telran.java47.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;


public interface BookRepository extends PagingAndSortingRepository<Book, String> {

	@Query("select b from Book b, Author a where a.name =:author")
	Stream<Book> findBooksByAuthor(String author);
	

	@Query("select b from Book b where publisher =:publisher")
	Stream<Book> findBooksByPublisher(Publisher publisher);
	

}
