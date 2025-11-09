package com.jayesh.resourcePrj.repo;

import com.jayesh.resourcePrj.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepo extends JpaRepository<Asset, Long> {


    boolean existsAssetBySerialNumber(String serialNumber);
}
