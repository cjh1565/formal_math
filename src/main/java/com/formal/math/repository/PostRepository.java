package com.formal.math.repository;

import com.formal.math.entity.Post;
import com.formal.math.repository.search.PostSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostSearch {
  @Query("select p, w from Post p left join p.writer w where p.pno = :pno")
  Object getPostByPno(@Param("pno") Long pno);
}