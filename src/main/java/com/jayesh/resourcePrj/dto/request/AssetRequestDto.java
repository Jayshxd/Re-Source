package com.jayesh.resourcePrj.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetRequestDto {

    private String name;
    private String serialNumber;
    private String assetType;
}
