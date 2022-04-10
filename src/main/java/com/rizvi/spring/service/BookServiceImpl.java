package com.rizvi.spring.service;

import com.rizvi.spring.dto.Mapper;
import com.rizvi.spring.dto.requestDto.BookRequestDto;
import com.rizvi.spring.dto.responseDto.BookResponseDto;
import com.rizvi.spring.model.Author;
import com.rizvi.spring.model.Book;
import com.rizvi.spring.model.Category;
import com.rizvi.spring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorservice;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorservice, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorservice = authorservice;
        this.categoryService = categoryService;
    }

    @Transactional
    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) throws Throwable {

        Book book = new Book();
        book.setName(bookRequestDto.getName());
        if(bookRequestDto.getAuthorsId().isEmpty()){
            throw new IllegalArgumentException("You need atleast on author");
        }else{
            List<Author> authors = new ArrayList();
            for (Long authorId : bookRequestDto.getAuthorsId()) {
                  Author author = authorservice.getAuthor(authorId);
                  authors.add(author);
            }
            book.setAuthors(authors);
        }
        if(bookRequestDto.getCategoryId() == null){
            throw new IllegalArgumentException("book atleast on category");
        }
        Category category = categoryService.getCategory(bookRequestDto.getCategoryId());
        book.setCategories(category);

        Book book1 = bookRepository.save(book);
        return Mapper.bookToBookResponseDto(book1);
    }

    @Override
    public BookResponseDto getBookById(Long bookId) {
           Book book = getBook(bookId);
           return  Mapper.bookToBookResponseDto(book);

    }


    @Override
    public Book getBook(Long bookId) {
          Book book = bookRepository.findById(bookId).orElseThrow(() ->
                  new IllegalArgumentException("can not find book with id  :  "+bookId));
          return book;
    }

    @Override
    public List<BookResponseDto> getBooks() {
          List<Book> books = StreamSupport
                  .stream(bookRepository.findAll().spliterator(), false)
                  .collect(Collectors.toList());
            return Mapper.booksToBookResponseDtos(books);
    }

    @Override
    public BookResponseDto deleteBook(Long bookId) {
           Book book = getBook(bookId);
           bookRepository.delete(book);
           return Mapper.bookToBookResponseDto(book);
    }


    @Transactional
    @Override
    public BookResponseDto editBook(Long bookId, BookRequestDto bookRequestDto) throws Throwable {
            Book bookToEdit = getBook(bookId);
        bookToEdit.setName(bookRequestDto.getName());
        if(!bookRequestDto.getAuthorsId().isEmpty()){
            List<Author> authors = new ArrayList<>();
            for (Long authorId : bookRequestDto.getAuthorsId()) {
                Author author = authorservice.getAuthor(authorId);
                authors.add(author);
            }
            bookToEdit.setAuthors(authors);
        }
        if(bookRequestDto.getCategoryId() != null){
            Category category = categoryService.getCategory(bookRequestDto.getCategoryId());
        }
          return Mapper.bookToBookResponseDto(bookToEdit);
    }

    @Override
    public BookResponseDto addAuthorToBook(Long bookId, Long authorId) {
            Book book = getBook(bookId);
            Author author = authorservice.getAuthor(authorId);
            if(author.getBooks().contains(author)){
                throw new IllegalArgumentException("this author is already assigned to this book");
        }
            book.addAuthor(author);
            author.addBook(book);
            return Mapper.bookToBookResponseDto(book);
    }

    @Override
    public BookResponseDto deleteAuthorFromBook(Long bookId, Long authorId) {
           Book book = getBook(bookId);
           Author author = authorservice.getAuthor(authorId);
           if(!(author.getBooks().contains(book))){
               throw new IllegalArgumentException("book does not have this Author");
           }
           author.removeBook(book);
        book.deleteAuthor(author);
        return Mapper.bookToBookResponseDto(book);
    }

    @Override
    public BookResponseDto addCategoryToBook(Long bookId, Long categoryId) throws Throwable {
        Book book = getBook(bookId);
        Category category = categoryService.getCategory(categoryId);
        if(Objects.nonNull(book.getCategories())){
            throw new IllegalArgumentException("book already has a category");
        }
        book.setCategories(category);
        category.addBook(book);
        return Mapper.bookToBookResponseDto(book);
    }

    @Override
    public BookResponseDto removeCategoryFromBook(Long bookId, Long categoryId) throws Throwable {
        Book book = getBook(bookId);
        Category category = categoryService.getCategory(categoryId);
        if(!(Objects.nonNull(book.getCategories()))){
            throw new IllegalArgumentException("book  does not have a category to delete");
    }
    book.setCategories(null);
        category.removeBook(book);
        return Mapper.bookToBookResponseDto(book);
     }
}