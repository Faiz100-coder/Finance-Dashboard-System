package com.zorvyn.financeservice.dto;

import java.time.LocalDate;

import com.zorvyn.financeservice.enums.RecordType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecordRequest {

    @NotNull(message= "Amount is Required")
    
    private Double amount;

    @NotNull(message = "Type is required")
    private RecordType type;

    @NotBlank(message = "Category is required")
    private String category;
    
    private LocalDate date;

    @NotBlank(message = "Description is required")
    private String description;
}
