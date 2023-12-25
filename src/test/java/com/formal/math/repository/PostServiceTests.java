package com.formal.math.repository;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.dto.PageResultDTO;
import com.formal.math.dto.PostDTO;
import com.formal.math.entity.Type;
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
      .type(Type.GENERAL)
      .title("Test.")
      .content("Test..")
      .writerEmail("member55@aaa.com")
      .build();
    Long pno = postService.register(dto);
  }
  @Test
  public void testList() {
    PageRequestDTO pageRequestDTO = new PageRequestDTO();
    PageResultDTO<PostDTO, Object[]> result = postService.getList(pageRequestDTO);
    for(PostDTO postDTO : result.getDtoList()) {
      System.out.println(postDTO);
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
