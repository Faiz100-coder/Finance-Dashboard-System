package com.zorvyn.financeservice.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zorvyn.financeservice.dto.CreateRecordResponse;
import com.zorvyn.financeservice.dto.DashboardSummaryResponse;
import com.zorvyn.financeservice.dto.RecordRequest;
import com.zorvyn.financeservice.service.FinancialRecordService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
@Validated  // this enables query parameter validation
public class FinancialController {

	private final FinancialRecordService recordService;

	// create record
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/record")
	public CreateRecordResponse createRecord(@Valid @RequestBody RecordRequest request) {

		return recordService.createRecord(request);
	}

	// get records
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/records")
	public List<CreateRecordResponse> getAllRecords() {
		return recordService.getAllRecords();
	}

   //get record by id;
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
	@GetMapping("/records/{id}")
	public CreateRecordResponse getRecordById(@PathVariable Long id) {
		return recordService.getRecordById(id);
	}
	
	//update record
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/records/{id}")
	public CreateRecordResponse updateRecord(@PathVariable Long id, @Valid @RequestBody RecordRequest request) {
		
		
		return recordService.updateRecord(id, request);
	}
	
	// soft Delete by id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/records/{id}")
	public String deleteRecord(@PathVariable Long id) {
		return recordService.deleteRecord(id);
	}
	
	//Filter record by type
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
	@GetMapping("/records/filter/type")
	public List<CreateRecordResponse> filterByType(
			@RequestParam 
			@NotNull(message = "Type is required")
			@Pattern(regexp = "EXPENSE|INCOME", message = "Type must be EXPENSE or INCOME")
			String type) {
	    return recordService.filterRecordsByType(type);
	}
	
	// Filter record by category
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
	@GetMapping("/records/filter/category")
	public List<CreateRecordResponse> filterByCategory(
			@RequestParam
			@NotBlank(message = "Category is required")
			String category) {
	    return recordService.filterRecordsByCategory(category);
	}
	
	// Filter by date
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
	@GetMapping("/records/filter/date")
	public List<CreateRecordResponse> filterByDate(
			@RequestParam 
			@NotNull(message = "Date is required")
			@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format YYYY-MM-DD")
			String date) {
	    return recordService.filterRecordsByDate(LocalDate.parse(date));
	}
		
	// summary Dashboard Summary 
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/dashboard/summary")
	public DashboardSummaryResponse getDashboardSummary() {
	    return recordService.getDashboardSummary();
	}
	

}
