package projects.moyethon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.moyethon.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String nickname);
}
