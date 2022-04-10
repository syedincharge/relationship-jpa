package com.rizvi.spring.service;

import com.rizvi.spring.dto.Mapper;
import com.rizvi.spring.dto.requestDto.AuthorRequestDto;
import com.rizvi.spring.dto.responseDto.AuthorResponseDto;
import com.rizvi.spring.model.Author;
import com.rizvi.spring.model.Zipcode;
import com.rizvi.spring.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class AuthorServiceImpl implements AuthorService {

        private final AuthorRepository authorRepository;
        private final ZipcodeService zipcodeService;

        @Autowired
        public AuthorServiceImpl(AuthorRepository authorRepository, ZipcodeService zipcodeService) {
           this.authorRepository = authorRepository;
           this.zipcodeService = zipcodeService;
    }

    @Transactional
    @Override
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestdto) {
        Author author = new Author();
        author.setName(authorRequestdto.getName());
        if(authorRequestdto.getZipcodeId() == null){
            throw new IllegalArgumentException("author need a  zipcode");
        }
        Zipcode zipcode = zipcodeService.getZipcode(authorRequestdto.getZipcodeId());
        author.setZipcode(zipcode);
        authorRepository.save(author);
        return Mapper.authorToAuthorResponseDto(author);
    }

    @Override
    public List<AuthorResponseDto> getAuthors() {
        List<Author> authors = StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
           return Mapper.authorsToAuthorResponseDtos(authors);
    }

    @Override
    public AuthorResponseDto getAuthorById(Long authorId) {
            return Mapper.authorToAuthorResponseDto(getAuthor(authorId));
    }

    @Override
    public Author getAuthor(Long authorId) {
          Author author = authorRepository.findById(authorId).orElseThrow(() ->
                  new IllegalArgumentException("Author wth Id :  "+authorId+ "   could not be found"));
          return author;
    }

    @Override
    public AuthorResponseDto deleteAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        authorRepository.delete(author);
        return Mapper.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
          Author authorToEdit = getAuthor(authorId);
          authorToEdit.setName(authorRequestDto.getName());
          if(authorRequestDto.getZipcodeId() != null){
              Zipcode zipcode = zipcodeService.getZipcode(authorRequestDto.getZipcodeId());
              authorToEdit.setZipcode(zipcode);
          }
          return Mapper.authorToAuthorResponseDto(authorToEdit);
    }

    @Override
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId) {
          Author author = getAuthor(authorId);
          Zipcode zipcode = zipcodeService.getZipcode(zipcodeId);
          if(Objects.nonNull(author.getZipcode())){
              throw new RuntimeException("author already has a zipcode");
          }
          author.setZipcode(zipcode);
          return Mapper.authorToAuthorResponseDto(author);
    }

    @Override
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        author.setZipcode(null);
        return Mapper.authorToAuthorResponseDto(author);

    }
}
