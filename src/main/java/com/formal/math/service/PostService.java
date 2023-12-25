package com.formal.math.service;

import com.formal.math.dto.PageRequestDTO;
import com.formal.math.dto.PageResultDTO;
import com.formal.math.dto.PostDTO;
import com.formal.math.entity.*;

public interface PostService {
  Long register(PostDTO dto);
  PageResultDTO<PostDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
  PostDTO get(Long pno);
  void removeWithReplies(Long bno);
  void modify(PostDTO postDTO);
  default Post dtoToEntity(PostDTO dto){
    Post post;
    Member member = Member.builder().email(dto.getWriterEmail()).build();
    if(dto.getType() == Type.ADD_THEORY || dto.getType() == Type.MODIFY_THEORY || dto.getType() == Type.DELETE_THEORY) {
      Theory theory = Theory.builder().tno(dto.getTheory()).build();
      post = Post.builder()
        .pno(dto.getPno())
        .type(dto.getType())
        .title(dto.getTitle())
        .content(dto.getContent())
        .theory(theory)
        .writer(member)
        .build();
    } else if(dto.getType() == Type.ADD_CATEGORY || dto.getType() == Type.MODIFY_CATEGORY || dto.getType() == Type.DELETE_CATEGORY) {
      Category category = Category.builder().cno(dto.getCategory()).build();
      post = Post.builder()
        .pno(dto.getPno())
        .type(dto.getType())
        .title(dto.getTitle())
        .content(dto.getContent())
        .category(category)
        .writer(member)
        .build();
    } else {
      post = Post.builder()
        .pno(dto.getPno())
        .type(dto.getType())
        .title(dto.getTitle())
        .content(dto.getContent())
        .writer(member)
        .build();
    }
    return post;
  }
  default PostDTO entityToDTO(Post post, Member member, Long replyCount) {
    PostDTO postDTO = PostDTO.builder()
      .pno(post.getPno())
      .type(post.getType())
      .title(post.getTitle())
      .content(post.getContent())
      .writerEmail(member.getEmail())
      .writerName(member.getName())
      .regDate(post.getRegDate())
      .modDate(post.getModDate())
      .replyCount(replyCount.intValue())
      .build();
    return postDTO;
  }
}
