package com.rizvi.spring.service;

import com.rizvi.spring.dto.requestDto.CategoryRequestDto;
import com.rizvi.spring.dto.responseDto.CategoryResponseDto;
import com.rizvi.spring.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public Category getCategory(Long categoryId) throws Throwable;

    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);

    public CategoryResponseDto getCategoryById(Long categoryId) throws Throwable;

    public List<CategoryResponseDto> getCategories();

    public CategoryResponseDto deleteCategory(Long categoryId) throws Throwable;

    public CategoryResponseDto editCategory(Long categoryId, CategoryRequestDto categoryRequestDto) throws Throwable;
}
