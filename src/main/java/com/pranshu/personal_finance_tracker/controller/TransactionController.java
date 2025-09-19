package com.pranshu.personal_finance_tracker.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pranshu.personal_finance_tracker.model.Transaction;
import com.pranshu.personal_finance_tracker.services.TransactionRepository;
import com.pranshu.personal_finance_tracker.services.TransactionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("Transaction")
public class TransactionController {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	
	
	@GetMapping
	public String listTransactions(@RequestParam(defaultValue="0") int page,
								   @RequestParam(defaultValue="10") int size,
								   @RequestParam(required=false) String description,
								   @RequestParam(required=false) BigDecimal amount,
								   @RequestParam(required=false) String amountFilter,
								   @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-mm-dd") LocalDate startDate,
								   @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-mm-dd") LocalDate endDate,								   
									Model model) {
		Pageable pageable=PageRequest.of(page, size);
		Page<Transaction> transactions=transactionService.findFilteredTransactions(description, amount, description, startDate, endDate, pageable);
		model.addAttribute("transactions", transactions);
		model.addAttribute("activePage","Transaction");
		model.addAttribute("description",description);
		model.addAttribute("amount",amount);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("amountFilter", amountFilter);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages",transactions.getTotalPages());
		return "transaction";
	}
	
	@GetMapping("/add")
	public String transactionForm(Model model){
		
		model.addAttribute("transaction", new Transaction());
		model.addAttribute("pageTitle", "Transction- form");
		model.addAttribute("activePage", "Transaction");
		model.addAttribute("subPage","Add New Transaction");
		return "add-transaction";
	}
	@PostMapping("/add")
	public String saveTransaction(@Valid @ModelAttribute("transaction") Transaction transaction, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			System.out.println("validate Error"+result.getAllErrors());
		}
		transactionRepository.save(transaction);
		redirectAttributes.addFlashAttribute("successMassage","Transaction added successfully");
		
		return "redirect:/Transaction";
	}
	@GetMapping("/View/{id}")
	@ResponseBody
	public ResponseEntity <Transaction> viewTransaction(@PathVariable Long id) {
		
		Transaction transaction= transactionService.getTransactionById(id);
		
		if(transaction !=null) {
			return ResponseEntity.ok(transaction);
		}
		else {
			return ResponseEntity.notFound().build();
		}
			
	}
	
	@GetMapping("/edit/{id}")
	public String editTransaction(@PathVariable Long id, Model model) {
		
		Transaction transaction= transactionService.getTransactionById(id);
		model.addAttribute("transaction", transaction);
		model.addAttribute("pageTitle", "Transction- form");
		model.addAttribute("activePage", "Transaction");
		model.addAttribute("subPage","Edit Transaction");
		if(transaction!=null) {
			return "edit-transaction";
		}else return "transaction";
	}
	
	@PostMapping("/edit")
	public String editTransactionCompleate(@Valid @ModelAttribute("transaction") Transaction transaction, BindingResult result, RedirectAttributes redirectAttributes) {
		
		
		if(result.hasErrors()) {
			System.out.println("validate Error"+result.getAllErrors());
		}
		transactionRepository.save(transaction);
		redirectAttributes.addFlashAttribute("successMassage", "Transction Updated Successfully");
		return "transaction";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTransaction(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		
		transactionRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMassage", " Transaction Has been deleted");
		return "redirect:/Transaction";
	}
}
	
