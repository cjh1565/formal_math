package com.formal.math.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "creator")
@Table(indexes = @Index(name = "idx_name", columnList = "name", unique = true))
public class Theory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;
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
    private List<Long> decls = new ArrayList<>();
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Long> category = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Member creator;
}
