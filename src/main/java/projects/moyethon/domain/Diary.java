package projects.moyethon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    private String content;

    private LocalDate createDate;

    @ElementCollection
    @CollectionTable(name = "diary_emotions", joinColumns = @JoinColumn(name = "diary_id"))
    @Enumerated(EnumType.STRING)
    private List<EmotionType> emotions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryMusic> musicList = new ArrayList<>();

    public Diary withUpdatedContent(String content, LocalDate createDate, List<EmotionType> emotions, List<DiaryMusic> musicList) {
        return Diary.builder()
                .id(this.id)
                .member(this.member)
                .content(content)
                .createDate(createDate)
                .emotions(emotions)
                .musicList(musicList)
                .build();
    }

}
