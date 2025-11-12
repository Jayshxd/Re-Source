package com.jayesh.resourcePrj.repo;

import com.jayesh.resourcePrj.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepo extends JpaRepository<Asset, Long> {


    boolean existsAssetBySerialNumber(String serialNumber);


    @Query(
            "select a from Asset a where"+
                    "(:name is null or lower(a.name) like lower(concat('%' , :name,'%') ) ) and"+
                    "(:serialNumber is null or lower(a.serialNumber) like lower(concat('%' , :serialNumber,'%') ) ) and"+
                    "(:assetType is null or lower(a.assetType) like lower(concat('%' , :assetType,'%') ) )"
    )
    List<Asset> findByCriteria(
            @Param("name") String name,
            @Param("serialNumber") String serialNumber,
            @Param("assetType") String assetType
    );
}
