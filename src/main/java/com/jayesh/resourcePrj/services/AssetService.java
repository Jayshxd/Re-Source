package com.jayesh.resourcePrj.services;

import com.jayesh.resourcePrj.dto.request.AssetRequestDto;
import com.jayesh.resourcePrj.dto.response.AssetResponseDto;
import com.jayesh.resourcePrj.entities.Asset;
import com.jayesh.resourcePrj.repo.AssetRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class AssetService {

    private final AssetRepo assetRepo;

    public AssetResponseDto createAsset(AssetRequestDto requestDto) {
        log.info("Received request to Create Asset");
        Asset asset = mapDtoToAsset(requestDto);
        Asset savedAsset = assetRepo.save(asset);
        log.info("Asset Created Successfully");
        return new AssetResponseDto(savedAsset);
    }

    private Asset mapDtoToAsset(AssetRequestDto requestDto) {
        if(assetRepo.existsAssetBySerialNumber(requestDto.getSerialNumber())) {
            throw new BadCredentialsException("Asset already exists");
        }
        Asset asset = new Asset();
        asset.setSerialNumber(requestDto.getSerialNumber());
        asset.setName(requestDto.getName());
        asset.setAssetType(requestDto.getAssetType());
        log.info("Asset Mapped Successfully");
        return asset;
    }


    public List<AssetResponseDto> createAssetsInBulk(List<AssetRequestDto> requestDto) {
        log.info("Received request to Create Assets In Bulk");
        List<Asset> resp = requestDto.stream().map(
                this::mapDtoToAsset
        ).toList();
        List<Asset> savedAssets = assetRepo.saveAll(resp);
        log.info("Assets Created In Bulk Successfully");
        return savedAssets.stream().map(AssetResponseDto::new).toList();
    }


    //find all assets or by criteria
    public List<AssetResponseDto> findAssets(String name, String serialNumber, String assetType) {
        return assetRepo.findByCriteria(name, serialNumber, assetType)
                .stream()
                .map(AssetResponseDto::new)
                .toList();
    }

    public AssetResponseDto updateAsset(Long id, AssetRequestDto requestDto) {
        log.info("Received request to Update Asset");
        Asset asset = assetRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Asset Not Found with id : "+id));
        asset.setName(requestDto.getName());
        asset.setSerialNumber(requestDto.getSerialNumber());
        asset.setAssetType(requestDto.getAssetType());
        log.info("Asset Updated Successfully");
        return new AssetResponseDto(asset);
    }

    public AssetResponseDto updateAssetDetails(Long id, AssetRequestDto requestDto) {
        log.info("Received request to Update Asset Details");
        Asset asset = assetRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Asset Not Found with id : "+id));
        if(requestDto.getName() != null && !requestDto.getName().isEmpty()) {
            asset.setName(requestDto.getName());
        }
        if(requestDto.getSerialNumber() != null && !requestDto.getSerialNumber().isEmpty()) {
            asset.setSerialNumber(requestDto.getSerialNumber());
        }
        if(requestDto.getAssetType() != null && !requestDto.getAssetType().isEmpty()) {
            asset.setAssetType(requestDto.getAssetType());
        }
        log.info("Asset Details Updated Successfully");
        return new AssetResponseDto(asset);

    }

    public void deleteAsset(Long id) {
        log.info("Received request to Delete Asset");
        assetRepo.deleteById(id);
        log.info("Asset Deleted Successfully");
    }
}
