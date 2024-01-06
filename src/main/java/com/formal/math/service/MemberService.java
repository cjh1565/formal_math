package com.formal.math.service;

public interface MemberService {
  static class EmailExistsException extends Exception {}
  static class NameExistsException extends Exception {}
  static class EmailNotExistException extends Exception {}
  String join(String email) throws EmailExistsException;
  void checkEmail(String email) throws EmailNotExistException;
  boolean checkPassword(String password, String epw);
  void setInfo(String email, String password, String name) throws NameExistsException;
  String getName(String email);
  void modify(String email, String password, String name) throws NameExistsException;
  void withdraw(String name);
//  default Member dtoToEntity(MemberDTO dto) {
//    return Member.builder()
//      .email(dto.getEmail())
//      .password(dto.getPassword())
//      .name(dto.getName())
//      .del(dto.isDel())
//      .build();
//  }
//  default MemberDTO entityToDto(Member member) {
//    return MemberDTO.builder()
//      .email(member.getEmail())
//      .password(member.getPassword())
//      .name(member.getName())
//      .del(member.isDel())
//      .build();
//  }
}
