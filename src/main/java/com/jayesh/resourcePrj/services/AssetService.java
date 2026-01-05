package com.jayesh.resourcePrj.services;

import com.jayesh.resourcePrj.dto.request.AssetRequestDto;
import com.jayesh.resourcePrj.dto.response.AssetResponseDto;
import com.jayesh.resourcePrj.entities.Asset;
import com.jayesh.resourcePrj.repo.AssetRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Asset mapDtoToAsset(AssetRequestDto dto) {
        Asset asset = new Asset();
        asset.setSerialNumber(dto.getSerialNumber());
        asset.setName(dto.getName());
        asset.setAssetType(dto.getAssetType());
        return asset;
    }



    //Normal Bulk Insertion
    @Transactional
    public List<AssetResponseDto> createAssetsInBulk(List<AssetRequestDto> requestDto) {
        long startTime = System.currentTimeMillis();
        log.info("normal bulk insertion task started for {} records...", requestDto.size());

        List<Asset> resp = requestDto.stream().map(
                this::mapDtoToAsset
        ).toList();
        List<Asset> savedAssets = assetRepo.saveAll(resp);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("Bulk Insert Finished!");
        log.info("Total time taken in background : {} ms",duration);
        log.info("Speed: {}  records/sec",(requestDto.size() / (duration / 1000.0)));

        return savedAssets.stream().map(AssetResponseDto::new).toList();
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createAssetsInBulkOptimised(List<AssetRequestDto> requestDtos) {
        long startTime = System.currentTimeMillis();
        log.info("Optimised bulk insertion task started for {} records...", requestDtos.size());

        try {
            int batchSize = 500;

            for (int i = 0; i < requestDtos.size(); i++) {
                AssetRequestDto dto = requestDtos.get(i);

                Asset asset = mapDtoToAsset(dto);

                entityManager.persist(asset);

                if (i > 0 && i % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }


            entityManager.flush();
            entityManager.clear();

            long duration = System.currentTimeMillis() - startTime;
            log.info("Bulk Asset Insert Finished!");
            log.info("Total time taken in background : {} ms",duration);
            log.info("Speed: {}  records/sec",(requestDtos.size() / (duration / 1000.0)));

        } catch (Exception e) {
            log.error("Bulk Insert Failed: {}", e.getMessage());

        }
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

    public Page<Asset> getAllAssetsByPages(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return assetRepo.findAll(pageable);
    }
}
