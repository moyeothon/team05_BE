package projects.moyethon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.moyethon.domain.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
