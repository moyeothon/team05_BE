package projects.moyethon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import projects.moyethon.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByMemberNickname(String userNickname);
}
