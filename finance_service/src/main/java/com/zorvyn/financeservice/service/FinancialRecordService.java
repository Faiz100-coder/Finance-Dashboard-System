package com.zorvyn.financeservice.service;

import java.time.LocalDate;
import java.util.List;

import com.zorvyn.financeservice.dto.CreateRecordResponse;
import com.zorvyn.financeservice.dto.DashboardSummaryResponse;
import com.zorvyn.financeservice.dto.RecordRequest;

public interface FinancialRecordService {
	// create record
	public CreateRecordResponse  createRecord(RecordRequest request);
	
	// get record
	public List<CreateRecordResponse> getAllRecords();
	
	//get record by id 
	public CreateRecordResponse getRecordById( Long id);
	
	//update record
	public CreateRecordResponse updateRecord(Long id, RecordRequest request);
	
	//Delete (soft) record
	public String deleteRecord(Long id);
	
	// get records by type - filter
	public List<CreateRecordResponse> filterRecordsByType(String type);
	
	// get records by category - filer
	public List<CreateRecordResponse> filterRecordsByCategory(String category);
	
	// filter records by date
	public List<CreateRecordResponse> filterRecordsByDate(LocalDate date);
	
	//Summary Dashboard
	public DashboardSummaryResponse getDashboardSummary();
	
			
}
