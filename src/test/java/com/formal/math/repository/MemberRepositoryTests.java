package com.formal.math.repository;

import com.formal.math.entity.Member;
import com.formal.math.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("member"+i+"@aaa.com")
                    .password(passwordEncoder.encode("1111"))
                    .name("member"+i)
                    .del(false)
                    .build();

            member.addMemberRole(MemberRole.USER);
            if(i < 20){
                member.addMemberRole(MemberRole.MANAGER);
            }
            if(i < 10){
                member.addMemberRole(MemberRole.ADMIN);
            }
            repository.save(member);
        });
    }

}
