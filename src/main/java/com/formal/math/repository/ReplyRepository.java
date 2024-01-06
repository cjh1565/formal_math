package com.formal.math.repository;

import com.formal.math.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
  @Query("select r from Reply r where r.post.pno = :pno")
  Page<Reply> listOfPost(Long pno, Pageable pageable);
  //void deleteByPost_Pno(Long pno);
}
