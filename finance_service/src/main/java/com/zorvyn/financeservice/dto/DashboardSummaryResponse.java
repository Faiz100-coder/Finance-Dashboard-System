package com.zorvyn.financeservice.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummaryResponse {

    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;

//    private Map<String, Double> categoryWiseTotals;
    private Map<String, Map<String, Double>> categoryWiseTotals;
    private List<CreateRecordResponse> recentTransactions;

    private Map<String, Double> monthlyTrends;
}
