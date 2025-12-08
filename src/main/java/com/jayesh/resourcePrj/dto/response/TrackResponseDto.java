package com.jayesh.resourcePrj.dto.response;

import com.jayesh.resourcePrj.entities.Track;
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
public class TrackResponseDto {

    private Long id;
    private String assetName;
    private String employeeName;
    private LocalDate issueDate;
    private LocalTime issueTime;
    private LocalDate expectedReturnDate;
    private Boolean isReturned;
    private Long employeeId;
    private Long assetId;
    private String assetCondition;

    public TrackResponseDto(Track track) {
        this.id = track.getId();
        this.assetName = track.getAsset().getName();
        this.employeeName = track.getEmployee().getName();
        this.issueDate = track.getIssueDate();
        this.issueTime = track.getIssueTime();
        this.expectedReturnDate = track.getExpectedReturnDate();
        this.isReturned = track.getIsReturned();
        this.employeeId = track.getEmployee().getId();
        this.assetId = track.getAsset().getId();
        this.assetCondition = track.getAssetCondition();
    }
}
