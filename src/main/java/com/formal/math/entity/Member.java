package com.formal.math.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "roleSet")
@Table(indexes = @Index(name = "idx_name", columnList = "name", unique = true))
public class Member extends BaseEntity {
    @Id
    private String email;
    private String password;
    private String name;
    private boolean del;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String password){
        this.password = password;
    }
    public void changeName(String name){
        this.name = name;
    }
    public void changeDel(boolean del){
        this.del = del;
    }
    public void addMemberRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);
    }
    public void clearRoles() {
        this.roleSet.clear();
    }
}
