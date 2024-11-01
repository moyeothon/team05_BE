package projects.moyethon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projects.moyethon.dto.MemberDTO;
import projects.moyethon.service.MemberService;

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


}
