package com.formal.math.repository;

import com.formal.math.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  @Query("select p, w from Post p left join p.writer w where p.pno = :pno")
  Object getPostWithWriter(@Param("pno") Long pno);
  @Query("select p, r from Post p left join Reply r on r.post = p where p.pno = :pno")
  List<Object[]> getPostWithReply(@Param("pno") Long pno);
  @Query(value = "select p, w, count(r) from Post p left join p.writer w left join Reply r on r.post = p group by p",
    countQuery = "select count(p) from Post p")
  Page<Object[]> getPostWithReplyCount(Pageable pageable);
  @Query("select p, w, count(r) from Post p left join p.writer w left join Reply r on r.post = p where p.pno = :pno")
  Object getPostByPno(@Param("pno") Long pno);
}