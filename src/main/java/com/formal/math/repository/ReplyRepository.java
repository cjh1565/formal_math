package com.formal.math.repository;

import com.formal.math.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
  @Modifying
  @Query("delete from Reply r where r.post.pno = :pno")
  void deleteByPno(Long pno);
}
