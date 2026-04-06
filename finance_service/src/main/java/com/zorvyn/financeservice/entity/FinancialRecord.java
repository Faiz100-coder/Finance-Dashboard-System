package com.zorvyn.financeservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.zorvyn.financeservice.enums.RecordType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "financial_records")
@Data
public class FinancialRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RecordType type;

	@Column(nullable = false)
	private String category;

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Long userId; // reference to user service

	@Column(nullable = false)
	private boolean isDeleted;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	
	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.isDeleted = false;

		if(date == null) {
			this.date = LocalDate.now();
		}
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
