package com.jayesh.resourcePrj.controller;

import com.jayesh.resourcePrj.dto.request.AssetRequestDto;
import com.jayesh.resourcePrj.dto.response.AssetResponseDto;
import com.jayesh.resourcePrj.services.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetController {
    private final AssetService assetService;


    @PostMapping
    public ResponseEntity<AssetResponseDto> createAsset(@RequestBody AssetRequestDto requestDto) {
        AssetResponseDto assetResponseDto = assetService.createAsset(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assetResponseDto);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<AssetResponseDto>> createAssetsInBulk(@RequestBody List<AssetRequestDto> requestDto) {

        List<AssetResponseDto> assetResponseDtos = assetService.createAssetsInBulk(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assetResponseDtos);
    }


}
