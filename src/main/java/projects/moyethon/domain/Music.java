package projects.moyethon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Music {

    @Id @GeneratedValue
    @Column(name = "music_id")
    private Long id;

    private String title;

    private String artist;

    private String previewUrl;

    private String albumImage;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
}
