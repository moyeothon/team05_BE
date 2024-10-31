package projects.moyethon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.moyethon.domain.Diary;
import projects.moyethon.domain.EmotionType;
import projects.moyethon.domain.Member;
import projects.moyethon.domain.Music;
import projects.moyethon.dto.DiaryDTO;
import projects.moyethon.dto.MusicDTO;
import projects.moyethon.exception.CustomDiaryException;
import projects.moyethon.exception.ErrorCode;
import projects.moyethon.repository.DiaryRepository;
import projects.moyethon.repository.MemberRepository;
import projects.moyethon.repository.MusicRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public Long createDiary(DiaryDTO diaryDTO) {
        Member member = memberRepository.findByNickname(diaryDTO.nickname()).orElseThrow(() ->
                new CustomDiaryException(ErrorCode.MEMBER_ALREADY_EXISTS));

        Diary diary = Diary.builder()
                .member(member)
                .createDate(diaryDTO.createDate())
                .content(diaryDTO.content())
                .build();

        for (EmotionType emotionType : diaryDTO.emotionTypes()) {
            diary.getEmotions().add(emotionType);
        }

        List<MusicDTO> musicDTOList = diaryDTO.musicList();

        List<Music> musicList = musicDTOList.stream().map(music -> music.toMusic()).toList();

        for(Music music : musicList) {
            musicRepository.save(music);
            diary.getMusicList().add(music);
        }

        diaryRepository.save(diary);

        return diary.getId();

    }
}
