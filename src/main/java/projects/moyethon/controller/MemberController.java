package projects.moyethon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.moyethon.dto.MemberDTO;
import projects.moyethon.service.MemberService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "사용자 정보", description = "사용자 정보를 담당하는 api 그룹")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/member")
    @Operation(
            summary = "사용자 등록",
            description = "사용자를 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 등록 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            }
    )

    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberDTO memberDTO) {
        MemberDTO member = memberService.join(memberDTO);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/api/member/check")
    @Operation(
            summary = "사용자 중복체크",
            description = "사용자의 중복을 체크합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "닉네임 사용가능"),
                    @ApiResponse(responseCode = "400", description = "닉네임 중복"),
            }

    )

    public ResponseEntity<?> checkMember(@RequestParam(name = "nickname") String nickname) {

        if(memberService.checkMember(nickname)){
            Map<String, String> map = Map.of("nickname available", nickname);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        Map<String, String> map = Map.of("nickname duplicated", nickname);
        return ResponseEntity.ok(map);
    }

}
