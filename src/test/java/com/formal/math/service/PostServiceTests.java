package com.formal.math.service;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.dto.PageResponseDTO;
import com.formal.math.dto.PostDTO;
import com.formal.math.dto.PostWithRCDTO;
import com.formal.math.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTests {
  @Autowired
  private PostService postService;
  @Test
  public void testRegister() {
    PostDTO dto = PostDTO.builder()
      .type("GENERAL")
      .title("Test.")
      .content("Test..")
      .writerName("member55")
      .build();
    Long pno = postService.register(dto);
  }
  @Test
  public void testList() {
    PageRequestDTO pageRequestDTO = new PageRequestDTO();
    PageResponseDTO<PostWithRCDTO> result = postService.listWithRC(pageRequestDTO);
    for(PostWithRCDTO postWithRCDTO : result.getDtoList()) {
      System.out.println(postWithRCDTO);
    }
  }
  @Test
  public void testGet() {
    Long pno = 100L;
    PostDTO postDTO = postService.get(pno);
    System.out.println(postDTO);
  }
  @Test
  public void testModify() {
    PostDTO postDTO = PostDTO.builder()
      .pno(102L)
      .title("제목 변경합니다.2")
      .content("내용 변경합니다.2")
      .build();
    postService.modify(postDTO);
  }
}
