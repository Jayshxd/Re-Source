package com.jayesh.resourcePrj.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackUpdateRequestDto {
    private LocalDate issueDate;
    private LocalTime issueTime;
    private LocalDate returnDate;
    private LocalTime returnTime;
    private String assetCondition;
    private Boolean isReturned;
    private Long employeeId;
    private Long assetId;
    private LocalDate expectedReturnDate;
}
