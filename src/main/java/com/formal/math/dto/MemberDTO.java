package com.formal.math.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class MemberDTO {
  private String email;
  private String password;
  private String name;
  private boolean del;
  private boolean social;
}
