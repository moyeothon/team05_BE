package projects.moyethon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.moyethon.domain.*;
import projects.moyethon.dto.DiaryDTO;
import projects.moyethon.dto.MusicDTO;
import projects.moyethon.exception.CustomDiaryException;
import projects.moyethon.exception.ErrorCode;
import projects.moyethon.repository.DiaryRepository;
import projects.moyethon.repository.MemberRepository;
import projects.moyethon.repository.MusicRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public Long createDiary(DiaryDTO diaryDTO) {
        Member member = findMemberByNickname(diaryDTO.nickname());

        Diary diary = Diary.builder()
                .member(member)
                .createDate(diaryDTO.createDate())
                .content(diaryDTO.content())
                .build();

        addEmotionsToDiary(diary, diaryDTO.emotionTypes());
        addMusicToDiary(diary, diaryDTO.musicList());

        diaryRepository.save(diary);
        return diary.getId();
    }

    public List<DiaryDTO> getAllDiariesByUser(String userNickname) {
        findMemberByNickname(userNickname);
        List<Diary> diaries = diaryRepository.findByMemberNickname(userNickname);

        if (diaries.isEmpty()) {
            throw new CustomDiaryException(ErrorCode.DIARY_NOT_FOUND);
        }

        return diaries.stream()
                .map(this::convertDiaryToDTO)
                .toList();
    }

    public DiaryDTO getDiaryById(Long diaryId) {
        Diary diary = findDiaryById(diaryId);
        return convertDiaryToDTO(diary);
    }

    public DiaryDTO updateDiaryById(Long diaryId, DiaryDTO diaryDTO) {
        Diary diary = findDiaryById(diaryId);

        List<EmotionType> updatedEmotions = new ArrayList<>(diaryDTO.emotionTypes());
        List<DiaryMusic> updatedMusicList = convertToDiaryMusicList(diary, diaryDTO.musicList());

        Diary updatedDiary = diary.withUpdatedContent(diaryDTO.content(), diaryDTO.createDate(), updatedEmotions, updatedMusicList);
        diaryRepository.save(updatedDiary);

        return convertDiaryToDTO(updatedDiary);
    }

    public void deleteDiaryById(Long diaryId) {
        Diary diary = findDiaryById(diaryId);
        diaryRepository.delete(diary);
    }

    private Member findMemberByNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomDiaryException(ErrorCode.MEMBER_DOES_NOT_EXISTS));
    }

    private Diary findDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomDiaryException(ErrorCode.DIARY_NOT_FOUND));
    }

    private void addEmotionsToDiary(Diary diary, List<EmotionType> emotions) {
        emotions.forEach(diary.getEmotions()::add);
    }

    private void addMusicToDiary(Diary diary, List<MusicDTO> musicDTOList) {
        List<Music> musicList = musicDTOList.stream()
                .map(music -> musicRepository.findByTitleAndArtist(music.title(), music.artist())
                        .orElseGet(() -> musicRepository.save(music.toMusic())))
                .toList();

        musicList.forEach(music -> {
            DiaryMusic diaryMusic = DiaryMusic.builder()
                    .diary(diary)
                    .music(music)
                    .build();
            diary.getMusicList().add(diaryMusic);
        });
    }

    private List<DiaryMusic> convertToDiaryMusicList(Diary diary, List<MusicDTO> musicDTOList) {
        return musicDTOList.stream()
                .map(musicDTO -> new DiaryMusic(null, diary, musicDTO.toMusic()))
                .toList();
    }

    private DiaryDTO convertDiaryToDTO(Diary diary) {
        List<MusicDTO> musicDTOList = diary.getMusicList().stream()
                .map(diaryMusic -> {
                    Music music = diaryMusic.getMusic();
                    return new MusicDTO(music.getTitle(), music.getArtist(), music.getPreviewUrl(), music.getAlbumImage(), music.getEmotion());
                })
                .toList();

        return new DiaryDTO(diary.getMember().getNickname(), diary.getContent(), diary.getCreateDate(), musicDTOList, diary.getEmotions());
    }
}
