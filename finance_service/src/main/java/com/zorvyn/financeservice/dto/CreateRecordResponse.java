package com.zorvyn.financeservice.dto;

import java.time.LocalDate;

import com.zorvyn.financeservice.enums.RecordType;
import lombok.Data;

@Data

public class CreateRecordResponse {

    private Long id;
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String description;
}