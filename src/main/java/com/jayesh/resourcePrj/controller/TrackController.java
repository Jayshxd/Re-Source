package com.jayesh.resourcePrj.controller;

import com.jayesh.resourcePrj.dto.request.TrackRequestDto;
import com.jayesh.resourcePrj.dto.request.TrackReturnRequestDto;
import com.jayesh.resourcePrj.dto.response.TrackResponseDto;
import com.jayesh.resourcePrj.services.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tracks")
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
}
