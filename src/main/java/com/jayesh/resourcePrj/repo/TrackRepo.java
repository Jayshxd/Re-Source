package com.jayesh.resourcePrj.repo;

import com.jayesh.resourcePrj.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepo extends JpaRepository<Track,Long> {
    Track findTopByAsset_IdAndIsReturnedTrueOrderByIdDesc(Long assetId);

    Optional<Track> findByAsset_IdAndIsReturnedFalse(Long assetId);
}
