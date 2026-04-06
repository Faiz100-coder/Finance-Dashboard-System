package com.zorvyn.financeservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zorvyn.financeservice.entity.FinancialRecord;
import com.zorvyn.financeservice.enums.RecordType;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
	
	List<FinancialRecord> findByIsDeletedFalse();
	
	Optional<FinancialRecord> findByIdAndIsDeletedFalse(Long id);
	
//	List<FinancialRecord> findByIdAndIsDeletedFalse(Long id);

	List<FinancialRecord> findByTypeAndIsDeletedFalse(RecordType type);

	List<FinancialRecord> findByCategoryAndIsDeletedFalse(String category);

	List<FinancialRecord> findByDateAndIsDeletedFalse(LocalDate date);

}
