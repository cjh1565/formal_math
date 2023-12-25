package com.formal.math.repository;

import com.formal.math.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
//  @EntityGraph(attributePaths =  {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
//  @Query("select m from Member m where m.social = :social and m.email = :email")
//  Optional<Member> findByEmail(@Param("email") String email, @Param("social") boolean social);
  @Query("select m from Member m where m.name = :name")
  Optional<Member> findByName(@Param("name") String name);
}
