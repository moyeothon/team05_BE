package projects.moyethon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.moyethon.domain.Music;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Optional<Music> findByTitleAndArtist(String title, String artist);
}
