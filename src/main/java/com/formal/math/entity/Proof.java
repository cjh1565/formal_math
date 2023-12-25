package com.formal.math.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
@IdClass(ProofPK.class)
public class Proof {
    @Id
    private Long decl;
    @Id
    private Long theory;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> actionList = new ArrayList<>();
    //private List<Action> actionList;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
}
