package com.pranshu.personal_finance_tracker.services;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pranshu.personal_finance_tracker.model.Transaction;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public Page<Transaction> findFilteredTransactions(String description, BigDecimal amount, String amountFilter,
			LocalDate startDate, LocalDate endDate, Pageable pageable) {
		return transactionRepository.findFilteredTransactions(description, amount, amountFilter, startDate, endDate,
				pageable);
	}
	
	public Transaction getTransactionById(Long id) {
		
		return transactionRepository.findById(id).orElse(null);
	}
	
	public Double getTotalExpanses() {
		
		return transactionRepository.getTotalExpanses();
	}
	
	public Double getTotalIncome() {
		return transactionRepository.getTotalIncome();
	}
	
	public List<Transaction> getRecentTransactions(){
		
		return transactionRepository.findTop10ByOrderByDateDesc();
	}
	
	public Map<String,List<Object>> findMonthlyIncomeAndExpense(){
		int year=LocalDate.now().getYear();
		List<Object[]> result=transactionRepository.findMonthlyIncomeAndExpense(year);
		
		Map<Integer,Object[]> data=new HashMap();
		for(Object[] obj : result) {
			data.put((Integer)obj[0], obj);
			
		}
		
		List<Object> months=new ArrayList<>();
		List<Object> income=new ArrayList<>();
		List<Object> expense=new ArrayList<>();
		int startOfMonth;
		int endOfMonth=12;
		
		for(startOfMonth=1;startOfMonth<=endOfMonth;startOfMonth++) {
			
			months.add(Month.of((Integer)startOfMonth));
			
			if(data.containsKey((Integer)startOfMonth)) {
				Object[] set=data.get(startOfMonth);
				income.add((Double)set[1]);
				expense.add((Double)set[2]);
			}else {
				income.add(0.0);
				expense.add(0.0);
			}
		}
		
		Map<String,List<Object>> response=new HashMap();
		
		response.put("Month", months);
		response.put("Income", income);
		response.put("Expense", expense);
		
		return response;
	}
	
	public Map<String,List<Object>> findWeeklyIncomeAndExpense(){
		LocalDate now=LocalDate.now();
		LocalDate startOfWeek =now.with(DayOfWeek.MONDAY);
		LocalDate endOfWeek = now.with(DayOfWeek.SUNDAY);
		List<Object[]> result=transactionRepository.findWeeklyIncomeAndExpense(startOfWeek,endOfWeek);
		System.out.println("result :"+result.toString());
		Map<String,Object[]> map=new HashMap();
		for(Object[] obj : result) {
			map.put((obj[0]).toString(), obj);
		}
		
		List<Object> days=new ArrayList<>();
		List<Object> income=new ArrayList<>();
		List<Object> expense=new ArrayList<>();
		
		LocalDate day=startOfWeek;
		while(!day.isAfter(endOfWeek)) {
			String dayName=day.getDayOfWeek().toString();
			days.add(dayName);
			
			if(map.containsKey(day.toString())) {
				Object [] row=map.get(day.toString());
				income.add((Double)row[1]);
				expense.add((Double)row[2]);
			}else {
				income.add(0.0);
				expense.add(0.0);
			}
			day=day.plusDays(1);
		}
		
		Map<String,List<Object>> response=new HashMap();
		response.put("days", days);
		response.put("Income", income);
		response.put("Expense", expense);
		
		return response;
	}
}
