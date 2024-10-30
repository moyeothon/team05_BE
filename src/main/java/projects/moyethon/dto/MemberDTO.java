package projects.moyethon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projects.moyethon.domain.Member;


public record MemberDTO(String nickname) {

    public static MemberDTO of(String nickname) {
        return new MemberDTO(nickname);
    }

    public static MemberDTO from(Member member) {
        return new MemberDTO(member.getNickname());
    }

    public Member toMember() {
        return Member.builder().nickname(nickname()).build();
    }
}
