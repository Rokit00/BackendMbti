package com.project.weing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.weing.Entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
