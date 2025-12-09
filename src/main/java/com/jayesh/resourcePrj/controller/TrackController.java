package com.jayesh.resourcePrj.controller;

import com.jayesh.resourcePrj.dto.request.TrackRequestDto;
import com.jayesh.resourcePrj.dto.request.TrackReturnRequestDto;
import com.jayesh.resourcePrj.dto.request.TrackUpdateRequestDto;
import com.jayesh.resourcePrj.dto.response.TrackAnalyticsResponse;
import com.jayesh.resourcePrj.dto.response.TrackResponseDto;
import com.jayesh.resourcePrj.services.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tracks")
@CrossOrigin(origins = "https://re-source-xi.vercel.app/")
public class TrackController {

    private final TrackService trackService;

    @PostMapping("/assignAsset")
    public ResponseEntity<TrackResponseDto> assignAsset(@RequestBody TrackRequestDto requestDto){
        TrackResponseDto responseDto = trackService.assignAsset(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @PostMapping("/returnAsset")
    public ResponseEntity<TrackResponseDto> returnAsset(@RequestBody TrackReturnRequestDto requestDto){
        TrackResponseDto responseDto = trackService.returnAsset(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<TrackResponseDto> findTracks(
            @RequestParam(required = false) LocalDate issueDate,
            @RequestParam(required = false) LocalTime issueTime,
            @RequestParam(required = false) LocalDate returnDate,
            @RequestParam(required = false) LocalTime returnTime,
            @RequestParam(required = false) String assetCondition,
            @RequestParam(required = false) Boolean isReturned,
            @RequestParam(required = false) LocalDate expectedReturnDate
    ){
        return trackService.findTracks(issueDate,issueTime,returnDate,returnTime,assetCondition,isReturned, expectedReturnDate);
    }


    @PatchMapping("/{trackId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TrackResponseDto updateTrackDetails(@PathVariable Long trackId,@RequestBody TrackUpdateRequestDto requestDto){
        return trackService.updateTrackDetails(trackId,requestDto);
    }

    @PutMapping("/{trackId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TrackResponseDto updateTrack(@PathVariable Long trackId,@RequestBody TrackUpdateRequestDto requestDto){
        return trackService.updateTrack(trackId,requestDto);
    }

    @DeleteMapping("/{trackId}")
    public String deleteTrack(@PathVariable Long trackId){
        trackService.deleteTrack(trackId);
        return "Deleted Successfully";
    }


    //analytics
    @GetMapping("/analytics")
    @ResponseStatus(HttpStatus.OK)
    public TrackAnalyticsResponse analytics(){
        return trackService.showAnalytics();
    }
}
