package com.formal.math.service;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.dto.PageResponseDTO;
import com.formal.math.dto.PostDTO;
import com.formal.math.dto.PostWithRCDTO;

public interface PostService {
  Long register(PostDTO dto);
  PageResponseDTO<PostWithRCDTO> listWithRC(PageRequestDTO pageRequestDTO);
  PostDTO get(Long pno);
  void modify(PostDTO postDTO);
  //  void remove(Long bno);
}
