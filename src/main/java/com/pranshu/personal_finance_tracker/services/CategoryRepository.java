package com.pranshu.personal_finance_tracker.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pranshu.personal_finance_tracker.model.Category;

@Repository
public interface CategoryRepository extends  JpaRepository<Category,Long>{

	@Query("SELECT t FROM Category t WHERE "+
			"(:name IS NULL OR t.name LIKE %:name%)")
	Page<Category> findFilterCategory(@Param("name") String name, Pageable pageable);
	
}
