package projects.moyethon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.moyethon.dto.DiaryDTO;
import projects.moyethon.service.DiaryService;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/diary")
    @Operation(
            summary = "특정 유저의 일기 전체조회",
            description = "특정 사용자의 일기를 전체조회합니다.",
            tags = {"일기"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "일기 조회 성공",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DiaryDTO.class)),
                                    examples = @ExampleObject(value = """
                                        [
                                          {
                                            "nickname": "user1",
                                            "content": "오늘 하루는 참 기뻤어요",
                                            "createDate": "2024-11-01",
                                            "musicList": [
                                              {
                                                "title": "Happy Song",
                                                "artist": "Singer A",
                                                "previewUrl": "http://example.com/preview1",
                                                "imagePath": "http://example.com/image1"
                                              }
                                            ],
                                            "emotionTypes": [
                                              "기쁨"
                                            ]
                                          },
                                          {
                                            "nickname": "user1",
                                            "content": "오늘은 약간 우울했어요",
                                            "createDate": "2024-11-02",
                                            "musicList": [
                                              {
                                                "title": "Sad Song",
                                                "artist": "Singer B",
                                                "previewUrl": "http://example.com/preview2",
                                                "imagePath": "http://example.com/image2"
                                              }
                                            ],
                                            "emotionTypes": [
                                              "슬픔"
                                            ]
                                          }
                                        ]
                                        """)
                            )
                    ),
            }
    )
    public ResponseEntity<List<DiaryDTO>> getAllDiariesByUser(@RequestParam(name = "userNickname") String userNickname) {
        List<DiaryDTO> diaryDTOList = diaryService.getAllDiariesByUser(userNickname);
        return ResponseEntity.status(200).body(diaryDTOList);
    }
    @GetMapping("/diary/{diaryId}")
    @Operation(
            summary = "일기Id로 일기조회",
            description = "특정 일기의 id로 일기를 조회합니다.",
            tags = {"일기"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "일기 조회 성공")
            }
    )
    public ResponseEntity<DiaryDTO> getDiaryById(@PathVariable Long diaryId) {
        DiaryDTO diaryDTO = diaryService.getDiaryById(diaryId);
        return ResponseEntity.status(200).body(diaryDTO);
    }
    @PostMapping("/api/diary")
    public Map<String, Long> createDiary(@RequestBody DiaryDTO diaryDTO) {
        Long id = diaryService.createDiary(diaryDTO);
        return Map.of("creation successed : ", id);
    }
}
