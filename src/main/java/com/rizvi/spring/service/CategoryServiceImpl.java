package com.rizvi.spring.service;

import com.rizvi.spring.dto.Mapper;
import com.rizvi.spring.dto.requestDto.CategoryRequestDto;
import com.rizvi.spring.dto.responseDto.CategoryResponseDto;
import com.rizvi.spring.model.Category;
import com.rizvi.spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category getCategory(Long categoryId) throws Throwable {
         return (Category) categoryRepository.findById(categoryId).orElseThrow(() ->
                 new IllegalArgumentException("could not find category wth id : "+ categoryId));
    }

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
           Category category = new Category();
           category.setName(categoryRequestDto.getName());
           categoryRepository.save(category);
           return Mapper.categoryToCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) throws Throwable {
          Category category = getCategory(categoryId);
          return Mapper.categoryToCategoryResponseDto(category);

    }

    @Override
    public List<CategoryResponseDto> getCategories() {
          List<Category> categories = (List<Category>) StreamSupport
                  .stream(categoryRepository.findAll().spliterator(), false)
                  .collect(Collectors.toList());
          return Mapper.categoriesToCategoryResponseDtos(categories);
    }

    @Override
    public CategoryResponseDto deleteCategory(Long categoryId) throws Throwable {
          Category category = getCategory(categoryId);
          categoryRepository.delete(category);
          return Mapper.categoryToCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto editCategory(Long categoryId, CategoryRequestDto categoryRequestDto) throws Throwable {
            Category categoryToEdit = getCategory(categoryId);
            categoryToEdit.setName(categoryRequestDto.getName());
            return Mapper.categoryToCategoryResponseDto(categoryToEdit);
    }
}
