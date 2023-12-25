package com.formal.math.security.service;

import com.formal.math.entity.Member;
import com.formal.math.repository.MemberRepository;
import com.formal.math.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: " + username);

        Optional<Member> result = memberRepository.findById(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("Account not found....");
        }
        Member member = result.get();
        if(member.isDel()){
            throw new UsernameNotFoundException("Account not found....");
        }
        log.info("-----------------");
        log.info(member);
        AuthMemberDTO authMember = new AuthMemberDTO(
          member.getEmail(),
          member.getPassword(),
          member.getName(),
          member.isDel(),
          member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
            .collect(Collectors.toSet())
        );
        return authMember;
    }
}
