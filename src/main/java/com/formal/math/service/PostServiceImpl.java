package com.formal.math.service;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.dto.PageResponseDTO;
import com.formal.math.dto.PostDTO;
import com.formal.math.dto.PostWithRCDTO;
import com.formal.math.entity.*;
import com.formal.math.repository.MemberRepository;
import com.formal.math.repository.PostRepository;
//import com.formal.math.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository repository;
  private final MemberRepository memberRepository;
//  private final ReplyRepository replyRepository;
  private Post dtoToEntity(PostDTO dto){
    Post post;
    Member member = memberRepository.findByName(dto.getWriterName()).orElseThrow();
    Type type = switch (dto.getType()) {
      case "ADD_THEORY" -> Type.ADD_THEORY;
      case "MODIFY_THEORY" -> Type.MODIFY_THEORY;
      case "DELETE_THEORY" -> Type.DELETE_THEORY;
      case "ADD_CATEGORY" -> Type.ADD_CATEGORY;
      case "MODIFY_CATEGORY" -> Type.MODIFY_CATEGORY;
      case "DELETE_CATEGORY" -> Type.DELETE_CATEGORY;
      default -> Type.GENERAL;
    };
    if(type == Type.ADD_THEORY || type == Type.MODIFY_THEORY || type == Type.DELETE_THEORY) {
      Theory theory = Theory.builder().tno(dto.getTheory()).build();
      post = Post.builder()
        .pno(dto.getPno())
        .type(type)
        .title(dto.getTitle())
        .content(dto.getContent())
        .theory(theory)
        .writer(member)
        .build();
    } else if(type == Type.ADD_CATEGORY || type == Type.MODIFY_CATEGORY || type == Type.DELETE_CATEGORY) {
      Category category = Category.builder().cno(dto.getCategory()).build();
      post = Post.builder()
        .pno(dto.getPno())
        .type(type)
        .title(dto.getTitle())
        .content(dto.getContent())
        .category(category)
        .writer(member)
        .build();
    } else {
      post = Post.builder()
        .pno(dto.getPno())
        .type(type)
        .title(dto.getTitle())
        .content(dto.getContent())
        .writer(member)
        .build();
    }
    return post;
  }
  private PostDTO entityToDTO(Post post, Member member) {
    PostDTO postDTO = PostDTO.builder()
      .pno(post.getPno())
      .type(post.getType().toString())
      .title(post.getTitle())
      .content(post.getContent())
      .writerName(member.getName())
      .regDate(post.getRegDate())
      .modDate(post.getModDate())
      .build();
    return postDTO;
  }
  @Override
  public Long register(PostDTO dto) {
    log.info(dto);
    Post post = dtoToEntity(dto);
    repository.save(post);
    return post.getPno();
  }
  @Override
  public PageResponseDTO<PostWithRCDTO> listWithRC(PageRequestDTO pageRequestDTO) {
    String[] types = pageRequestDTO.getSorts();
    String keyword = pageRequestDTO.getKeyword();
    Pageable pageable = pageRequestDTO.getPageable("pno");
    Page<PostWithRCDTO> result = repository.searchWithRC(types, keyword, pageable);
    return PageResponseDTO.<PostWithRCDTO>withAll()
      .pageRequestDTO(pageRequestDTO)
      .dtoList(result.getContent())
      .total((int)result.getTotalElements())
      .build();
  }
  @Override
  public PostDTO get(Long pno) {
    Object result = repository.getPostByPno(pno);
    Object[] arr = (Object[]) result;
    return entityToDTO((Post) arr[0], (Member) arr[1]);
  }
  @Override
  public void modify(PostDTO postDTO) {
    Post post = repository.findById(postDTO.getPno()).orElseThrow();
    post.changeTitle(postDTO.getTitle());
    post.changeContent(postDTO.getContent());
    repository.save(post);
  }
//  @Override
//  @Transactional
//  public void remove(Long pno) {
//    replyRepository.deleteByPost_Pno(pno);
//    repository.deleteById(pno);
//  }
}
