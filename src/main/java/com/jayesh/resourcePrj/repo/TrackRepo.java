package com.jayesh.resourcePrj.repo;

import com.jayesh.resourcePrj.dto.response.TrackResponseDto;
import com.jayesh.resourcePrj.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepo extends JpaRepository<Track,Long> {
    Track findTopByAsset_IdAndIsReturnedTrueOrderByIdDesc(Long assetId);

    Optional<Track> findByAsset_IdAndIsReturnedFalse(Long assetId);
    @Query("SELECT t FROM Track t WHERE " +
            "(:issueDate IS NULL OR t.issueDate = :issueDate) AND " +
            "(:issueTime IS NULL OR t.issueTime = :issueTime) AND " +
            "(:returnDate IS NULL OR t.returnDate = :returnDate) AND " +
            "(:returnTime IS NULL OR t.returnTime = :returnTime) AND " +
            "(:assetCondition IS NULL OR LOWER(t.assetCondition) LIKE LOWER(CONCAT('%', :assetCondition, '%'))) AND " +
            "(:isReturned IS NULL OR t.isReturned = :isReturned)")

    List<Track> findByCriteria(
            @Param("issueDate") LocalDate issueDate,
            @Param("issueTime") LocalTime issueTime,
            @Param("returnDate") LocalDate returnDate,
            @Param("returnTime") LocalTime returnTime,
            @Param("assetCondition") String assetCondition,
            @Param("isReturned") Boolean isReturned
    );



    List<Track> findTracksByEmployeeId(Long employeeId);

    List<Track> findTracksByAssetId(Long assetId);
}
