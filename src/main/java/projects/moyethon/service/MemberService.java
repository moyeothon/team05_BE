package projects.moyethon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.moyethon.domain.Member;
import projects.moyethon.dto.MemberDTO;
import projects.moyethon.exception.CustomDiaryException;
import projects.moyethon.exception.ErrorCode;
import projects.moyethon.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDTO join(MemberDTO memberDTO) {

        if(!validateMember(memberDTO.nickname())){
            throw new CustomDiaryException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .nickname(memberDTO.nickname())
                .build();

        memberRepository.save(member);
        MemberDTO dto = MemberDTO.from(member);
        return dto;
    }

    private boolean validateMember(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) {
            return false;
        }
        return true;
    }
}
