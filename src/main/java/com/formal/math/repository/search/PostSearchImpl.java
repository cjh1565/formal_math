package com.formal.math.repository.search;

import com.formal.math.dto.PostWithRCDTO;
import com.formal.math.entity.Post;
import com.formal.math.entity.QMember;
import com.formal.math.entity.QPost;
import com.formal.math.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class PostSearchImpl extends QuerydslRepositorySupport implements PostSearch {
  public PostSearchImpl(){
    super(Post.class);
  }
  @Override
  public Page<PostWithRCDTO> searchWithRC(String[] types, String keyword, Pageable pageable) {
    QPost post = QPost.post;
    QReply reply = QReply.reply;
    QMember member = QMember.member;
    JPQLQuery<Post> query = from(post);
    query.leftJoin(member).on(post.writer.eq(member));
    query.leftJoin(reply).on(reply.post.eq(post));
    query.groupBy(post);

    if(types != null && types.length > 0 && keyword != null){
      //검색 조건을 작성하기
      BooleanBuilder booleanBuilder = new BooleanBuilder();
      for (String t: types) {
        switch(t){
          case "t":
            booleanBuilder.or(post.title.contains(keyword));
            break;
          case "w":
            booleanBuilder.or(member.name.contains(keyword));
            break;
          case "c":
            booleanBuilder.or(post.content.contains(keyword));
            break;
        }
      }
      query.where(booleanBuilder);
    }
//    query.where(post.pno.gt(0L));
    JPQLQuery<PostWithRCDTO> dtoQuery = query.select(Projections.bean(
      PostWithRCDTO.class,
      post.pno,
      post.type,
      post.title,
      member.name,
      post.regDate,
      reply.count().as("replyCount")
      ));
    this.getQuerydsl().applyPagination(pageable, dtoQuery);
    List<PostWithRCDTO> dtoList = dtoQuery.fetch();
    long count = dtoQuery.fetchCount();
    return new PageImpl<>(dtoList, pageable, count);
  }
}
