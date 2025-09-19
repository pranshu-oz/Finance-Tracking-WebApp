package com.pranshu.personal_finance_tracker.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pranshu.personal_finance_tracker.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE " +
	           "(:description IS NULL OR t.description LIKE %:description%) AND " +
	           "(:amount IS NULL OR " +
	           "(:amountFilter = '=' AND t.amount = :amount) OR " +
	           "(:amountFilter = '<=' AND t.amount <= :amount) OR " +
	           "(:amountFilter = '>=' AND t.amount >= :amount)) AND " +
	           "(:startDate IS NULL OR t.date >= :startDate) AND " +
	           "(:endDate IS NULL OR t.date <= :endDate)")
	
	Page<Transaction> findFilteredTransactions(
			@Param("description") String description,
			@Param("amount") BigDecimal amount,
			@Param("amountFilter") String amountFilter,
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate,
			Pageable pageable);
	
	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE"+
			" t.transactionType='EXPENSE'")
	Double getTotalExpanses();
	
	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE"+
			" t.transactionType='INCOME'")
	Double getTotalIncome();
	List<Transaction> findTop10ByOrderByDateDesc();
	
	@Query("SELECT MONTH(t.date), " +
	           "SUM(CASE WHEN t.transactionType = 'INCOME' THEN t.amount ELSE 0 END), " +
	           "SUM(CASE WHEN t.transactionType = 'EXPENSE' THEN t.amount ELSE 0 END) " +
	           "FROM Transaction t " +
	           "WHERE YEAR(t.date) = :year " +
	           "GROUP BY MONTH(t.date) " +
	           "ORDER BY MONTH(t.date)")
	List<Object[]> findMonthlyIncomeAndExpense(@Param("year") int year);
	
	@Query("SELECT t.date, " +
	           "SUM(CASE WHEN t.transactionType = 'INCOME' THEN t.amount ELSE 0 END), " +
	           "SUM(CASE WHEN t.transactionType = 'EXPENSE' THEN t.amount ELSE 0 END) " +
	           "FROM Transaction t " +
	           "WHERE t.date BETWEEN :startOfWeek AND :endOfWeek "+
	           "GROUP BY t.date " +
	           "ORDER BY t.date")
	List<Object[]> findWeeklyIncomeAndExpense(@Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);
	
}