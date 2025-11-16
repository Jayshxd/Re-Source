package com.jayesh.resourcePrj.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackAnalyticsResponse {
    private long totalTracks;
    private long totalReturns;
    private long totalAssigned;

}
