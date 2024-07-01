package com.example.BookCrud.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookCrud.Entity.Book;
import com.example.BookCrud.Repository.BookRepository;


@RestController
@RequestMapping("/api/books")
public class BookController {
	 @Autowired
	    private BookRepository bookRepository;

	    @GetMapping("/getAll")
	    public List<Book> getAllBooks() {
	        return bookRepository.findAll();
	    }

	    @PostMapping("/add")
	    public Book createBook(@RequestBody Book book) {
	        return bookRepository.save(book);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
	        Optional<Book> book = bookRepository.findById(id);
	        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
	        Optional<Book> book = bookRepository.findById(id);
	        if (book.isPresent()) {
	            Book existingBook = book.get();
	            existingBook.setTitle(bookDetails.getTitle());
	            existingBook.setAuthor(bookDetails.getAuthor());
	            existingBook.setIsbn(bookDetails.getIsbn());
	            existingBook.setYearOfPublication(bookDetails.getYearOfPublication());
	            return ResponseEntity.ok(bookRepository.save(existingBook));
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
	        if (bookRepository.existsById(id)) {
	            bookRepository.deleteById(id);
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @GetMapping("/year/{yearOfPublication}")
	    public List<Book> getBooksByYearOfPublication(@PathVariable int yearOfPublication) {
	        return bookRepository.findByYearOfPublication(yearOfPublication);
	    }
	}

