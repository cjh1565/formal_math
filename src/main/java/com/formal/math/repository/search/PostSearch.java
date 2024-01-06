package com.formal.math.repository.search;

import com.formal.math.dto.PostWithRCDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearch {
  Page<PostWithRCDTO> searchWithRC(String[] types, String keyword, Pageable pageable);
}
