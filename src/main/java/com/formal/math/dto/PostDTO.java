package com.formal.math.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
  private String type;
  @NotEmpty
  @Size(min=3, max=100)
  private String title;
  @NotEmpty
  private String content;
  private Long theory;
  private Long category;
  private String writerName;
  private LocalDateTime regDate;
  private LocalDateTime modDate;
}
