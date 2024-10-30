package projects.moyethon.controller;

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
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberDTO memberDTO) {
        MemberDTO member = memberService.join(memberDTO);
        return ResponseEntity.ok(member);
    }


}
