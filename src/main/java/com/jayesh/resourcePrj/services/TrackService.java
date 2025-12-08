package com.jayesh.resourcePrj.services;
import com.jayesh.resourcePrj.dto.request.TrackRequestDto;
import com.jayesh.resourcePrj.dto.request.TrackReturnRequestDto;
import com.jayesh.resourcePrj.dto.request.TrackUpdateRequestDto;
import com.jayesh.resourcePrj.dto.response.EmployeeResponseDto;
import com.jayesh.resourcePrj.dto.response.TrackAnalyticsResponse;
import com.jayesh.resourcePrj.dto.response.TrackResponseDto;
import com.jayesh.resourcePrj.entities.Asset;
import com.jayesh.resourcePrj.entities.Employee;
import com.jayesh.resourcePrj.entities.Track;
import com.jayesh.resourcePrj.notification.EmailService;
import com.jayesh.resourcePrj.repo.AssetRepo;
import com.jayesh.resourcePrj.repo.EmployeeRepo;
import com.jayesh.resourcePrj.repo.TrackRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class TrackService {
    private final AssetRepo assetRepo;
    private final EmployeeRepo employeeRepo;
    private final TrackRepo trackRepo;
    private final EmailService emailService;

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
        track.setExpectedReturnDate(requestDto.getExpectedReturnDate());
        employee.addTrack(track);
        asset.addTrack(track);
        log.info("Asset Assigned Successfully");
        emailService.sendEmailWhenAssigned(employee.getEmail(),track);
        log.info("Assigned Email Sent to : {}", track.getEmployee().getEmail());
        Track savedTrack = trackRepo.save(track);
        return new  TrackResponseDto(savedTrack);
    }

    public TrackResponseDto returnAsset(TrackReturnRequestDto requestDto) {
        Track track = trackRepo.findById(requestDto.getTrackId()).orElseThrow(()-> new EntityNotFoundException("Track Not Found with id-> "+requestDto.getTrackId()));
        track.setReturnDate(LocalDate.now());
        track.setReturnTime(LocalTime.now());
        track.setAssetCondition(requestDto.getAssetCondition());
        track.setIsReturned(true);
        track.setExpectedReturnDate(null);
        log.info("Track Returned Successfully");
        emailService.sendEmailWhenReturned(track.getEmployee().getEmail(),track);
        log.info("Returned Email Sent to : {}", track.getEmployee().getEmail());
        Track savedTrack = trackRepo.save(track);
        return new TrackResponseDto(savedTrack);
    }

    public List<TrackResponseDto> findTracks(LocalDate issueDate,LocalTime issueTime,LocalDate returnDate,LocalTime returnTime, String assetCondition, Boolean isReturned,LocalDate expectedReturnDate) {
        List<Track> tracks = trackRepo.findByCriteria(issueDate,issueTime,returnDate,returnTime,assetCondition,isReturned,expectedReturnDate);
        return tracks.stream().map(TrackResponseDto::new).toList();
    }


    //patch
    public TrackResponseDto updateTrackDetails(Long trackId,TrackUpdateRequestDto requestDto) {
        Track track = trackRepo.findById(trackId).orElseThrow(()-> new EntityNotFoundException("Track Not Found with id-> "+trackId));
        if (requestDto.getIssueDate() != null)
            track.setIssueDate(requestDto.getIssueDate());
        if (requestDto.getIssueTime() != null)
            track.setIssueTime(requestDto.getIssueTime());
        if (requestDto.getReturnDate() != null)
            track.setReturnDate(requestDto.getReturnDate());
        if (requestDto.getReturnTime() != null)
            track.setReturnTime(requestDto.getReturnTime());
        if (requestDto.getAssetCondition() != null)
            track.setAssetCondition(requestDto.getAssetCondition());
        if (requestDto.getIsReturned() != null)
            track.setIsReturned(requestDto.getIsReturned());
        if(requestDto.getExpectedReturnDate() != null){
            track.setExpectedReturnDate(requestDto.getExpectedReturnDate());
        }

        Track saved = trackRepo.save(track);
        return new  TrackResponseDto(saved);
    }

    //put
    public TrackResponseDto updateTrack(Long trackId, TrackUpdateRequestDto requestDto) {
        Track track = trackRepo.findById(trackId)
                .orElseThrow(() -> new EntityNotFoundException("Track Not Found with id -> " + trackId));

        track.setIssueDate(requestDto.getIssueDate());
        track.setIssueTime(requestDto.getIssueTime());
        track.setReturnDate(requestDto.getReturnDate());
        track.setReturnTime(requestDto.getReturnTime());
        track.setAssetCondition(requestDto.getAssetCondition());
        track.setIsReturned(requestDto.getIsReturned());
        track.setExpectedReturnDate(requestDto.getExpectedReturnDate());
        trackRepo.save(track);
        return new TrackResponseDto(track);
    }

    public void deleteTrack(Long trackId) {
        trackRepo.deleteById(trackId);
    }

    public List<TrackResponseDto> findTracksByEmpId(Long empId) {
        return trackRepo.findTracksByEmployeeId(empId).stream().map(TrackResponseDto::new).toList();
    }

    public List<TrackResponseDto> findTracksByAssetId(Long assetId) {
        return trackRepo.findTracksByAssetId(assetId).stream().map(TrackResponseDto::new).toList();
    }

    @Transactional
    @Scheduled(cron = "0 0 9 * * *")
    public void overDues(){
        List<Track> list = trackRepo.findTracksByExpectedReturnDateBeforeAndIsReturnedFalse(LocalDate.now());
        for(Track track : list){
            emailService.sendEmail(track.getEmployee().getEmail(),track.getExpectedReturnDate(),track.getAsset().getName());
            log.info("Email Sent to : {}", track.getEmployee().getEmail());
        }
    }

    public TrackAnalyticsResponse showAnalytics() {
        long totalTracks = trackRepo.count();
        long totalReturns = trackRepo.countTrackByIsReturned(true);
        long totalAssigned = trackRepo.countTrackByIsReturned(false);
        long overdueAssets = trackRepo.countTrackByIsReturned(false);
        return TrackAnalyticsResponse.builder()
                .totalAssigned(totalAssigned)
                .totalReturns(totalReturns)
                .totalTracks(totalTracks)
                .overdueAssets(overdueAssets)
                .build();
    }

    public Integer countEmps(Long empId) {
        return trackRepo.countTracksByEmployeeId(empId);
    }
}
