package com.formal.math.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
  private int page;
  private int size;
  private String sort;
  private String keyword;
  private String link;
  public PageRequestDTO() {
    this.page = 1;
    this.size = 10;
  }
  public String[] getSorts(){
    if(sort == null || sort.isEmpty()){
      return null;
    }
    return sort.split("");
  }
  public Pageable getPageable(String...props) {
    return PageRequest.of(page-1, size, Sort.by(props).descending());
  }
  public String getLink(){
    if(link == null){
      StringBuilder builder = new StringBuilder();
      builder.append("page=" + this.page);
      builder.append("&size=" + this.size);
      if(sort != null && !sort.isEmpty()){
        builder.append("&sort=" + sort);
      }
      if(keyword != null && !keyword.isEmpty()){
        builder.append("&keyword=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8));
      }
      link = builder.toString();
    }
    return link;
  }
}
