package com.formal.math.security.service;

import com.formal.math.entity.Member;
import com.formal.math.entity.MemberRole;
import com.formal.math.repository.MemberRepository;
import com.formal.math.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("-----------------------------");
        log.info("userRequest: " + userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: " + clientName);
        log.info(userRequest.getAdditionalParameters());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("=============================");
        oAuth2User.getAttributes().forEach((k,v) -> { log.info(k + ":" + v); });
        String email = null;
        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }
        log.info("EMAIL: " + email);
        Member member = saveSocialMember(email);
        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.isDel(),
                member
                    .getRoleSet()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        return authMember;
    }
    //@Transactional
    private Member saveSocialMember(String email) {
        Optional<Member> result = repository.findById(email);
//        Optional<Member> result = repository.findByEmail(email, false);
        if(result.isPresent() && !result.get().isDel()) {
            return result.get();
        }
        Long cnt = repository.count()+1;
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode("1111"))
                .name("user"+cnt)
                .del(false)
                .build();
        member.addMemberRole(MemberRole.USER);
        repository.save(member);
        return member;
    }
}
