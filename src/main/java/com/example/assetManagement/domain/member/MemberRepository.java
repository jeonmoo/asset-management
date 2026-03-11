package com.example.assetManagement.domain.member;

import com.example.assetManagement.domain.member.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {

    Optional<Members> findByLoginId(String loginId);
}
