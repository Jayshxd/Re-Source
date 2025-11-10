package com.jayesh.resourcePrj.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;


    private String password;

    @Column(unique = true)
    private String email;


    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL
    )
    List<Track> employeeTracks = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name ="role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public void addTrack(Track track) {
        this.employeeTracks.add(track);
        track.setEmployee(this);
    }
    public void removeTrack(Track track) {
        this.employeeTracks.remove(track);
        track.setEmployee(null);
    }



}
