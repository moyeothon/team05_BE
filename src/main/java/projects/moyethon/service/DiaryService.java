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

//    public Long createDiary(DiaryDTO diaryDTO) {
//        Member member = memberRepository.findByNickname(diaryDTO.nickname()).orElseThrow(() ->
//                new CustomDiaryException(ErrorCode.MEMBER_ALREADY_EXISTS));
//
//        Diary diary = Diary.builder()
//                .member(member)
//                .createDate(diaryDTO.createDate())
//                .content(diaryDTO.content())
//                .build();
//
//        for (EmotionType emotionType : diaryDTO.emotionTypes()) {
//            diary.getEmotions().add(emotionType);
//        }
//
//        List<MusicDTO> musicDTOList = diaryDTO.musicList();
//
//        List<Music> musicList = musicDTOList.stream().map(music -> music.toMusic()).toList();
//
//        for(Music music : musicList) {
//            musicRepository.save(music);
//            diary.getMusicList().add(music);
//        }
//
//        diaryRepository.save(diary);
//
//        return diary.getId();
//
//    }

    public List<DiaryDTO>  getAllDiariesByUser(String userNickname) {
        Member member = memberRepository.findByNickname(userNickname).orElseThrow(() ->
                new CustomDiaryException(ErrorCode.MEMBER_DOES_NOT_EXISTS));

        Diary diary = diaryRepository.findByMemberNickname(userNickname).orElseThrow(() ->
                new CustomDiaryException(ErrorCode.DIARY_NOT_FOUND));

        List<MusicDTO> musicDTOList = diary.getMusicList().stream()
                .map(diaryMusic -> {
                    Music music = diaryMusic.getMusic();
                    return new MusicDTO(music.getTitle(), music.getArtist(), music.getPreviewUrl(), music.getAlbumImage());
                })
                .toList();

        return diaryRepository.findByMemberNickname(userNickname).stream()
                .map(diary1 -> new DiaryDTO(diary1.getMember().getNickname(), diary1.getContent(), diary1.getCreateDate(), musicDTOList, diary1.getEmotions()))
                .toList();
    }

    public DiaryDTO getDiaryById(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() ->
                new CustomDiaryException(ErrorCode.DIARY_NOT_FOUND));

        List<MusicDTO> musicDTOList = diary.getMusicList().stream()
                .map(diaryMusic -> {
                    Music music = diaryMusic.getMusic();
                    return new MusicDTO(music.getTitle(), music.getArtist(), music.getPreviewUrl(), music.getAlbumImage());
                })
                .toList();

        return new DiaryDTO(diary.getMember().getNickname(), diary.getContent(), diary.getCreateDate(), musicDTOList, diary.getEmotions());
    }

    public void deleteDiaryById(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() ->
                new CustomDiaryException(ErrorCode.DIARY_NOT_FOUND));

        diaryRepository.delete(diary);
    }
}
