package com.formal.math.service;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.dto.PageResultDTO;
import com.formal.math.dto.PostDTO;
import com.formal.math.entity.Member;
import com.formal.math.entity.Post;
import com.formal.math.repository.PostRepository;

import com.formal.math.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository repository;
  private final ReplyRepository replyRepository;
  @Override
  public Long register(PostDTO dto) {
    log.info(dto);
    Post post = dtoToEntity(dto);
    repository.save(post);
    return post.getPno();
  }
  @Override
  public PageResultDTO<PostDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    log.info(pageRequestDTO);
    Function<Object[], PostDTO> fn = (en -> entityToDTO((Post) en[0], (Member) en[1], (Long) en[2]));
    Page<Object[]> result = repository.getPostWithReplyCount(pageRequestDTO.getPageable(Sort.by("pno").descending()));
    return new PageResultDTO<>(result, fn);
  }
  @Override
  public PostDTO get(Long pno) {
    Object result = repository.getPostByPno(pno);
    Object[] arr = (Object[]) result;
    return entityToDTO((Post) arr[0], (Member) arr[1], (Long) arr[2]);
  }
  @Override
  @Transactional
  public void removeWithReplies(Long pno) {
    replyRepository.deleteByPno(pno);
    repository.deleteById(pno);
  }
  @Override
  public void modify(PostDTO postDTO) {
    Post post = repository.findById(postDTO.getPno()).orElse(null);
    if(post != null) {
      post.changeTitle(postDTO.getTitle());
      post.changeContent(postDTO.getContent());
      repository.save(post);
    }
  }
}
