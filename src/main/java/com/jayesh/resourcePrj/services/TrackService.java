package com.jayesh.resourcePrj.services;

import com.jayesh.resourcePrj.dto.request.TrackRequestDto;
import com.jayesh.resourcePrj.dto.request.TrackReturnRequestDto;
import com.jayesh.resourcePrj.dto.response.TrackResponseDto;
import com.jayesh.resourcePrj.entities.Asset;
import com.jayesh.resourcePrj.entities.Employee;
import com.jayesh.resourcePrj.entities.Track;
import com.jayesh.resourcePrj.repo.AssetRepo;
import com.jayesh.resourcePrj.repo.EmployeeRepo;
import com.jayesh.resourcePrj.repo.TrackRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrackService {
    private final AssetRepo assetRepo;
    private final EmployeeRepo employeeRepo;
    private final TrackRepo trackRepo;

    @Transactional
    public TrackResponseDto assignAsset(TrackRequestDto requestDto) {
        Employee employee = employeeRepo
                .findById(requestDto.getEmployeeId())
                .orElseThrow(
                        ()-> new EntityNotFoundException("Employee Not Found with id-> "+requestDto.getEmployeeId())
                );
        Asset asset = assetRepo
                .findById(requestDto.getAssetId())
                .orElseThrow(()-> new EntityNotFoundException("Asset Not Found with id-> "+requestDto.getAssetId())
                );



        Optional<Track> checks = trackRepo.findByAsset_IdAndIsReturnedFalse(requestDto.getAssetId());

        if(checks.isPresent()) {
            log.error("The Asset : {}, Is Already Assigned !!!", asset.getName());
            throw new BadCredentialsException("The Asset : " + asset.getName() + " , Is Already Assigned !!!");
        }
        Track check = trackRepo.findTopByAsset_IdAndIsReturnedTrueOrderByIdDesc(asset.getId());
        System.out.println("Latest Returned Record for asset " + asset.getId() + " : " + check);
            if(check!=null){
                String cond = check.getAssetCondition();
                if(cond!=null && cond.toLowerCase().contains("not good")) {
                    log.error("The Condition of : {}, Is Not Good !!!", asset.getName());
                    throw new BadCredentialsException("The Condition Of : " + asset.getName() + " , Is Not Good !!!");
                }
            }

        Track track = new Track();
        track.setIssueDate(LocalDate.now());
        track.setIssueTime(LocalTime.now());
        track.setIsReturned(false);
        track.setAssetCondition("HAS TO BE UPDATED AFTER RETURN");
        employee.addTrack(track);
        asset.addTrack(track);
        log.info("Asset Assigned Successfully");
        Track savedTrack = trackRepo.save(track);
        return new  TrackResponseDto(savedTrack);
    }

    public TrackResponseDto returnAsset(TrackReturnRequestDto requestDto) {
        Track track = trackRepo.findById(requestDto.getTrackId()).orElseThrow(()-> new EntityNotFoundException("Track Not Found with id-> "+requestDto.getTrackId()));
        track.setReturnDate(LocalDate.now());
        track.setReturnTime(LocalTime.now());
        track.setAssetCondition(requestDto.getAssetCondition());
        track.setIsReturned(true);
        log.info("Track Returned Successfully");
        Track savedTrack = trackRepo.save(track);
        return new  TrackResponseDto(savedTrack);
    }
}
