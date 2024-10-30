package projects.moyethon.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 닉네임 입니다.");

    private final HttpStatus httpStatus;

    private final String message;

}
