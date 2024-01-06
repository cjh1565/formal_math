package com.formal.math.service;

import com.formal.math.dto.ReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    @Autowired
    private ReplyService replyService;
    @Test
    public void testRegister() {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("ReplyDTO Text")
                .replier("member99")
                .pno(99L)
                .build();
        log.info(replyService.register(replyDTO));
    }
}