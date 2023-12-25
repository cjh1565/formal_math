package com.formal.math.service;

import com.formal.math.entity.Member;
import com.formal.math.entity.MemberRole;
import com.formal.math.repository.MemberRepository;

import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;
  @Override
  public String join(String email) throws EmailExistsException {
    Optional<Member> result = memberRepository.findById(email);
    if (result.isPresent() && !result.get().isDel()) {
      throw new EmailExistsException();
    }
    String password = mailService.createPassword();
    mailService.sendPassword(email, password);
    password = passwordEncoder.encode(password);
    return password;
  }
  @Override
  public void checkEmail(String email) throws EmailNotExistException {
    Optional<Member> optionalMember = memberRepository.findById(email);
    if(optionalMember.isEmpty()){
      throw new EmailNotExistException();
    }
    Member member = optionalMember.get();
    String password = mailService.createPassword();
    member.changePassword(passwordEncoder.encode(password));
    memberRepository.save(member);
    mailService.sendPassword(email, password);
  }
  @Override
  public boolean checkPassword(String password, String epw) {
    return passwordEncoder.matches(password, epw);
  }
  @Override
  public void setInfo(String email, String password, String name) throws NameExistsException {
    if(name.matches("user[1-9][0-9]*")) {
      throw new NameExistsException();
    }
    Optional<Member> result = memberRepository.findByName(name);
    if(result.isPresent() && !result.get().getEmail().equals(email)) {
      throw new NameExistsException();
    }
    Member member = Member.builder()
      .email(email)
      .password(passwordEncoder.encode(password))
      .name(name)
      .del(false)
      .build();
    member.addMemberRole(MemberRole.USER);
//    log.info("======================");
//    log.info(member);
//    log.info(member.getRoleSet());
    memberRepository.save(member);
  }
  public String getName(String email) {
    Member member = memberRepository.findById(email).orElse(null);
    return member.getName();
  }
  @Override
  public void modify(String email, String password, String name) throws NameExistsException {
    if(name.matches("user[1-9][0-9]*")) {
      throw new NameExistsException();
    }
    Optional<Member> result = memberRepository.findByName(name);
    if(result.isPresent() && !result.get().getEmail().equals(email)) {
      throw new NameExistsException();
    }
    Member member = memberRepository.findById(email).orElse(null);
    member.changePassword(passwordEncoder.encode(password));
    member.changeName(name);
//    log.info("======================");
//    log.info(member);
//    log.info(member.getRoleSet());
    memberRepository.save(member);
  }
  public void withdraw(String email) {
    Member member = memberRepository.findById(email).orElse(null);
    member.changeDel(true);
    member.clearRoles();
    memberRepository.save(member);
  }
}
