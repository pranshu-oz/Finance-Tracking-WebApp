package com.pranshu.personal_finance_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pranshu.personal_finance_tracker.model.Category;
import com.pranshu.personal_finance_tracker.services.CategoryRepository;
import com.pranshu.personal_finance_tracker.services.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping
	public String getAllCategories(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String name,
			Model model) {

		Pageable pageble = PageRequest.of(page, size);

		Page<Category> categories = categoryService.findFilterCategory(name, pageble);
		model.addAttribute("categories", categories);
		model.addAttribute("activePage", "Category");
		model.addAttribute("name", name);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", categories.getTotalPages());
		return "category";
	}

	@PostMapping
	public String saveAllCategories() {

		return "category";
	}

	@GetMapping("/add")
	public String addNewCategory(Model model) {

		Category categories = new Category();
		model.addAttribute("activePage", "Category");
		model.addAttribute("subPage", "Add New Category");
		model.addAttribute("category", categories);
		return "add-category";
	}

	@PostMapping("/add")
	public String addNewCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
			RedirectAttributes redirect) {

		if (result.hasErrors()) {
			System.out.println("Error : " + result.getAllErrors());
		}

		categoryRepository.save(category);
		redirect.addFlashAttribute("successMessage", "New Category has been Registered");

		return "redirect:/category";
	}
}
