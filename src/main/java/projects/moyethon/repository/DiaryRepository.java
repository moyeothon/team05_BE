package projects.moyethon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.moyethon.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
