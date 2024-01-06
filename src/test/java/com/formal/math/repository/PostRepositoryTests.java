package com.formal.math.repository;

import com.formal.math.dto.PostWithRCDTO;
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

import java.util.Arrays;
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
    public void testRead3() {
        Object result = postRepository.getPostByPno(98L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void testSearchReplyCount() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0,10, Sort.by("pno").descending());
        Page<PostWithRCDTO> result = postRepository.searchWithRC(types, keyword, pageable);
        //total pages
        log.info(result.getTotalPages());
        //pag size
        log.info(result.getSize());
        //pageNumber
        log.info(result.getNumber());
        //prev next
        log.info(result.hasPrevious() +": " + result.hasNext());
        result.getContent().forEach(post -> log.info(post));
    }
}
