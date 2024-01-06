package com.formal.math.dto;

import com.formal.math.entity.Type;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PostWithRCDTO {
  private Long pno;
  private Type type;
  private String title;
  private String name;
  private LocalDateTime regDate;
  private Long replyCount;
}
