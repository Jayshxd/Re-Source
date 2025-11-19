package com.jayesh.resourcePrj.controller;

import com.jayesh.resourcePrj.dto.request.AssetRequestDto;
import com.jayesh.resourcePrj.dto.response.AssetResponseDto;
import com.jayesh.resourcePrj.dto.response.TrackResponseDto;
import com.jayesh.resourcePrj.services.AssetService;
import com.jayesh.resourcePrj.services.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
@CrossOrigin(origins = "*")
public class AssetController {
    private final AssetService assetService;
    private final TrackService trackService;


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


    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<AssetResponseDto> findAssets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) String assetType){
        return assetService.findAssets(name,serialNumber,assetType);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssetResponseDto updateAsset(@PathVariable Long id, @RequestBody AssetRequestDto requestDto) {
        return assetService.updateAsset(id,requestDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AssetResponseDto updateAssetDetails(@PathVariable Long id, @RequestBody AssetRequestDto requestDto) {
        return assetService.updateAssetDetails(id,requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAsset(@PathVariable Long id){
        assetService.deleteAsset(id);
    }


    @GetMapping("/{assetId}/tracks")
    @ResponseStatus(HttpStatus.OK)
    public List<TrackResponseDto> findTracksByAssetId(@PathVariable Long assetId){
        return trackService.findTracksByAssetId(assetId);
    }

}
