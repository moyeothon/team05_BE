package projects.moyethon.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomDiaryException extends RuntimeException {

  private ErrorCode errorCode;

}
