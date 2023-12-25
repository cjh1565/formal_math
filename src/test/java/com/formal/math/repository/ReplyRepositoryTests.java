package com.formal.math.repository;

import com.formal.math.entity.Member;
import com.formal.math.entity.Post;
import com.formal.math.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
  @Autowired
  private ReplyRepository replyRepository;
  @Test
  public void insertReply() {
    IntStream.rangeClosed(1, 300).forEach(i -> {
      long j = (long)(Math.random()*100)+1;
      Member member = Member.builder()
        .email("member"+j+"@aaa.com")
        .build();
      long pno = (long)(Math.random()*100)+1;
      Post post = Post.builder().pno(pno).build();
      Reply reply = Reply.builder()
        .comment("Reply....."+i)
        .post(post)
        .replier(member)
        .build();
      replyRepository.save(reply);
    });
  }
}
