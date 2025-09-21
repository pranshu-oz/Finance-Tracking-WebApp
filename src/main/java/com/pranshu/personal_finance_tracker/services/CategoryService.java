package com.pranshu.personal_finance_tracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pranshu.personal_finance_tracker.model.Category;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public Page<Category> findFilterCategory(String name, Pageable pageable){
		
		return categoryRepository.findFilterCategory(name, pageable);
	}
	
	public List<Category> findAllCategory() {
		
		return categoryRepository.findAll();
	}
	
	public Category findCategoryById(Long i) {
		
		return categoryRepository.findById(i).orElse(null);
	}
}
