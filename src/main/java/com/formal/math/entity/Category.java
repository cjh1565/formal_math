package com.formal.math.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "creator")
@Table(indexes = @Index(name = "idx_name", columnList = "name", unique = true))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    @Column(name = "name", nullable = false)
    private String name;
    private String description;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Long> parents = new HashSet<>();
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Long> children = new HashSet<>();
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Long> theories = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Member creator;
}
