package projects.moyethon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    private String title;

    private String artist;

    private String previewUrl;

    private String albumImage;

    @Enumerated(EnumType.STRING)
    private EmotionType emotion;

}
