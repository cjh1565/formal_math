package com.formal.math.repository;

import com.formal.math.entity.Member;
import com.formal.math.entity.Post;
import com.formal.math.entity.Type;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class PostRepositoryTests {
    @Autowired
    private PostRepository postRepository;
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
              .email("member"+i+"@aaa.com")
              .build();

            Post post = Post.builder()
              .type(Type.GENERAL)
              .title("title..."+i)
              .content("content..."+i)
              .writer(member)
              .build();
            postRepository.save(post);
        });
    }
    @Test
    public void testGePostWithReply(){
        List<Object[]> result = postRepository.getPostWithReply(100L);
        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }
    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("pno").descending());
        Page<Object[]> result = postRepository.getPostWithReplyCount(pageable);
        result.get().forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }
    @Test
    public void testRead3() {
        Object result = postRepository.getPostByPno(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
}
