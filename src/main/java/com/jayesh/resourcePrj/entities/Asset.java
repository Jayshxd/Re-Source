package com.jayesh.resourcePrj.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String serialNumber;

    @Column(nullable = false)
    String assetType;


    @OneToMany(
            mappedBy = "asset",
            cascade = CascadeType.ALL
    )
    List<Track> assetTracks = new ArrayList<>();


    public void addTrack(Track track) {
        this.assetTracks.add(track);
        track.setAsset(this);
    }
    public void removeTrack(Track track) {
        this.assetTracks.remove(track);
        track.setAsset(null);
    }

}
