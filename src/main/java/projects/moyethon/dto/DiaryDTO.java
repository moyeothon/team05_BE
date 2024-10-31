package projects.moyethon.dto;

import projects.moyethon.domain.Diary;
import projects.moyethon.domain.EmotionType;
import projects.moyethon.domain.Music;

import java.time.LocalDate;
import java.util.List;

public record DiaryDTO(String nickname, String content, LocalDate createDate, List<MusicDTO> musicList,
                       List<EmotionType> emotionTypes)  {

    public Diary toDiary() {
        return Diary.builder()
                .content(content)
                .createDate(createDate)
                .build();
    }
}
