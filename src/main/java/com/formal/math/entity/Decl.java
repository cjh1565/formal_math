package com.formal.math.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
@Table(indexes = @Index(name = "idx_name", columnList = "name", unique = true))
public class Decl extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dno;
    @Enumerated(EnumType.ORDINAL)
    private Kind kind;
    @Column(name = "name", nullable = false)
    private String term;
    @Column(nullable = false)
    private String type;
    private String description;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Long> theories = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
}
