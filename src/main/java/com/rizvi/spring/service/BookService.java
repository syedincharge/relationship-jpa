package com.rizvi.spring.service;


import com.rizvi.spring.dto.requestDto.BookRequestDto;
import com.rizvi.spring.dto.responseDto.BookResponseDto;
import com.rizvi.spring.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface BookService {

    public BookResponseDto addBook(BookRequestDto bookRequestDto) throws Throwable;

    public BookResponseDto getBookById(Long bookId);

    public Book getBook(Long bookId);

    public List<BookResponseDto> getBooks();

    public BookResponseDto deleteBook(Long bookId);

    BookResponseDto editBook(Long bookId, BookRequestDto bookRequestDto) throws Throwable;

    public BookResponseDto addAuthorToBook(Long bookId, Long authorId);

    public BookResponseDto deleteAuthorFromBook(Long bookId, Long authorId);

    public BookResponseDto addCategoryToBook(Long bookId, Long categoryId) throws Throwable;

    public BookResponseDto removeCategoryFromBook(Long bookId, Long categoryId) throws Throwable;
}
