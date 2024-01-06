package com.formal.math.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"post", "replier"})
@Table(indexes = @Index(name = "idx_reply_post_pno", columnList = "post_pno"))
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String replyText;
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member replier;
    public void changeReplyText(String replyText) {
        this.replyText = replyText;
    }
}
