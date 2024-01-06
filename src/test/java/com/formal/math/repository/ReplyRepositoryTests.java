package com.formal.math.repository;

import com.formal.math.entity.Member;
import com.formal.math.entity.Post;
import com.formal.math.entity.Reply;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;
@Log4j2
@SpringBootTest
public class ReplyRepositoryTests {
  @Autowired
  private ReplyRepository replyRepository;
  @Test
  public void insertReply() {
    IntStream.rangeClosed(1, 101).forEach(i -> {
      long j = (long)(Math.random()*100)+1;
      Member member = Member.builder()
        .email("member"+j+"@aaa.com")
        .build();
      long pno = (long)(Math.random()*100)+1;
      Post post = Post.builder().pno(100L).build();
      Reply reply = Reply.builder()
        .replyText("Reply....."+i)
        .post(post)
        .replier(member)
        .build();
      replyRepository.save(reply);
    });
  }
  @Transactional
  @Test
  public void testPostReplies() {
    Long pno = 98L;
    Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());
    Page<Reply> result = replyRepository.listOfPost(pno, pageable);
    result.getContent().forEach(reply -> {
      log.info(reply);
    });
  }
}
