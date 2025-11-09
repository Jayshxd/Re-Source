package com.jayesh.resourcePrj.dto.response;

import com.jayesh.resourcePrj.entities.Asset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetResponseDto {
    private Long id;
    private String name;
    private String assetType;

    public AssetResponseDto(Asset asset) {
        this.id = asset.getId();
        this.name = asset.getName();
        this.assetType = asset.getAssetType();
    }
}
