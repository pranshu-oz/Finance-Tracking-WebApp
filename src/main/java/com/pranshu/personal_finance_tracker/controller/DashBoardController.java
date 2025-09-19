package com.pranshu.personal_finance_tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pranshu.personal_finance_tracker.model.Transaction;
import com.pranshu.personal_finance_tracker.services.TransactionService;

@Controller
public class DashBoardController {
	
	@Autowired()
	TransactionService transactionService;
	
	@GetMapping("/")
	public String showDashBoard(Model model) {
		
		model.addAttribute("activePage", "Dash Board");
		return"index";
	}
	
	@GetMapping("/total-expanse")
	@ResponseBody
	public ResponseEntity<Double> getTotalTransactionExapanses() {
		
		return ResponseEntity.ok(transactionService.getTotalExpanses());
	}
	
	@GetMapping("/total-income")
	@ResponseBody
	public ResponseEntity<Double> getTotalTransactionIncome() {
		
		return ResponseEntity.ok(transactionService.getTotalIncome());
	}
	
	@GetMapping("/recent-transaction")
	@ResponseBody
	public ResponseEntity<List<Transaction>> getRecentTransactions(){
		return ResponseEntity.ok(transactionService.getRecentTransactions());
	}
	
	@GetMapping("/transaction-by-month")
	@ResponseBody
	public ResponseEntity<Map<String,List<Object>>> getIncomeAndExpenseOfCurrentYear(){
		return ResponseEntity.ok(transactionService.findMonthlyIncomeAndExpense());
	}
	
	@GetMapping("/transaction-by-week")
	@ResponseBody
	public ResponseEntity<Map<String,List<Object>>> getIncomeAndExpenseOfCurrentMonth(){
		return ResponseEntity.ok(transactionService.findWeeklyIncomeAndExpense());
	}
}
