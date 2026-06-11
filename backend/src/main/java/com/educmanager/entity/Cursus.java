package com.educmanager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cursus {

    @Id
    // id auto
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere;
}
