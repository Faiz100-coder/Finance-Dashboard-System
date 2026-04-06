package com.zorvyn.financeservice.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.zorvyn.financeservice.dto.CreateRecordResponse;
import com.zorvyn.financeservice.dto.DashboardSummaryResponse;
import com.zorvyn.financeservice.dto.RecordRequest;
import com.zorvyn.financeservice.entity.FinancialRecord;
import com.zorvyn.financeservice.exceptions.ErrorCodeEnum;
import com.zorvyn.financeservice.exceptions.ProjectException;
import com.zorvyn.financeservice.repository.FinancialRecordRepository;
import com.zorvyn.financeservice.service.FinancialRecordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {

	private final ModelMapper modelMapper;
	private final FinancialRecordRepository financialRecordRepository;

	// create record
	@Override
	public CreateRecordResponse createRecord(RecordRequest request) {

		if (request.getAmount() == null || request.getAmount() <= 0) {
			throw new ProjectException(
					ErrorCodeEnum.AMOUNT_VALIDATION_ERROR.getErrorCode(),
					ErrorCodeEnum.AMOUNT_VALIDATION_ERROR.getErrorMessage(), 
					HttpStatus.BAD_REQUEST);
		}

		FinancialRecord records = modelMapper.map(request, FinancialRecord.class);
		records.setUserId(1L); // TEMP

		FinancialRecord savedRecord = financialRecordRepository.save(records);

		log.info("Record created with id: {}", savedRecord.getId());

		return modelMapper.map(savedRecord, CreateRecordResponse.class);
	}

	// get all records
	@Override
	public List<CreateRecordResponse> getAllRecords() {

		List<FinancialRecord> records = financialRecordRepository.findByIsDeletedFalse();

		log.info("Fetched all records");

		return records.stream()
				.map(rec -> modelMapper
				.map(rec, CreateRecordResponse.class))
				.toList();
	}

	// get record by id
	@Override
	public CreateRecordResponse getRecordById(Long id) {

		FinancialRecord record = financialRecordRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ProjectException(
						ErrorCodeEnum.RECORD_NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.RECORD_NOT_FOUND.getErrorMessage(), 
						HttpStatus.NOT_FOUND));

		log.info("Fetched record id: {}", id);

		return modelMapper.map(record, CreateRecordResponse.class);
	}

	// update record
	@Override
	public CreateRecordResponse updateRecord(Long id, RecordRequest request) {

		FinancialRecord record = financialRecordRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ProjectException(
						ErrorCodeEnum.RECORD_NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.RECORD_NOT_FOUND.getErrorMessage(), 
						HttpStatus.NOT_FOUND));

		// Update record only allowed fields
		record.setAmount(request.getAmount());
		record.setCategory(request.getCategory());
		record.setDescription(request.getDescription());
		record.setType(request.getType());

		financialRecordRepository.save(record);

		log.info("Record updated id: {}", id);

		return modelMapper.map(record, CreateRecordResponse.class);
	}

	// delete record soft delete
	@Override
	public String deleteRecord(Long id) {

		FinancialRecord record = financialRecordRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ProjectException(
						ErrorCodeEnum.RECORD_NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.RECORD_NOT_FOUND.getErrorMessage(),
						HttpStatus.NOT_FOUND));

		record.setDeleted(true);

		// save in  db
		financialRecordRepository.save(record);

		log.info("Record soft deleted id: {}", id);

		return "Record deleted successfully...";
	}

	// filter by type
	@Override
	public List<CreateRecordResponse> filterRecordsByType(String type) {

		List<FinancialRecord> records = financialRecordRepository
				.findByTypeAndIsDeletedFalse(
						Enum.valueOf(com.zorvyn.financeservice.enums.RecordType.class, type));

		return records.stream()
				.map(rec -> modelMapper
						.map(rec, CreateRecordResponse.class))
				.toList();
	}

	// filter by categeroy
	@Override
	public List<CreateRecordResponse> filterRecordsByCategory(String category) {

		List<FinancialRecord> records = financialRecordRepository.findByCategoryAndIsDeletedFalse(category);

		// throw exception if no record found
		if (records.isEmpty()) {
			throw new ProjectException(
					ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorCode(),
					"No records found for category: " + category, 
					HttpStatus.NOT_FOUND);
		}

		return records.stream()
				.map(rec -> modelMapper
						.map(rec, CreateRecordResponse.class))
				.toList();
	}

	// filter by date
	@Override
	public List<CreateRecordResponse> filterRecordsByDate(LocalDate date) {

		List<FinancialRecord> records = financialRecordRepository.findByDateAndIsDeletedFalse(date);

		if (records.isEmpty()) {
			throw new ProjectException(
					ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorCode(),
					"No records found for date: " + date,
					HttpStatus.NOT_FOUND);
		}

		return records.stream().map(rec -> modelMapper.map(rec, CreateRecordResponse.class)).toList();
	}

	// Dashboard Summary Response
	@Override
	public DashboardSummaryResponse getDashboardSummary() {
		List<FinancialRecord> records = financialRecordRepository.findByIsDeletedFalse();

		// Total Income
		double totalIncome = records.stream()
				.filter(r -> r.getType()
				.name().equals("INCOME"))
				.mapToDouble(FinancialRecord::getAmount)
				.sum();

		// Total Expense
		double totalExpense = records.stream()
				.filter(r -> r.getType()
				.name().equals("EXPENSE"))
				.mapToDouble(FinancialRecord::getAmount)
				.sum();

		// Net Balance
		double netBalance = totalIncome - totalExpense;

		// Category wise totals
//		Map<String, Double> categoryTotals = records.stream()
//				.collect(Collectors
//				.groupingBy(FinancialRecord::getCategory, Collectors.summingDouble(FinancialRecord::getAmount)));

		Map<String, Map<String, Double>> categoryWiseTotals = records.stream()
		        .collect(Collectors.groupingBy(
		                r -> r.getType().name(),   // INCOME  / EXPENSE
		                Collectors.groupingBy(
		                        r -> r.getCategory().toLowerCase(), // i normalize this 
		                        Collectors.summingDouble(FinancialRecord::getAmount)
		                )
		        ));
		
		// Recent Transactions (Top 5 Transactions)
		List<CreateRecordResponse> recentTransactions = records.stream()
				.sorted((a, b) -> b.getCreatedAt()
				.compareTo(a.getCreatedAt()))
				.limit(5)
				.map(r -> modelMapper.map(r, CreateRecordResponse.class))
				.toList();

		// Monthly Trends
		Map<String, Double> monthlyTrends = records.stream()
				.collect(Collectors.groupingBy(
				r -> r.getDate().getMonth()
				.toString(), Collectors.summingDouble(FinancialRecord::getAmount)));

		
		return DashboardSummaryResponse.builder()
				.totalIncome(totalIncome)
				.totalExpense(totalExpense)
				.netBalance(netBalance)
				.categoryWiseTotals(categoryWiseTotals)
				.recentTransactions(recentTransactions)
				.monthlyTrends(monthlyTrends)
				.build();

	}

}
