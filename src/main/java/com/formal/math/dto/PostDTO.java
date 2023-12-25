package com.formal.math.dto;

import com.formal.math.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
  private Long pno;
  private Type type;
  private String title;
  private String content;
  private Long theory;
  private Long category;
  private String writerEmail;
  private String writerName;
  private LocalDateTime regDate;
  private LocalDateTime modDate;
  private int replyCount;
}