package potato.potatoAPIserver.common;

import lombok.Getter;

/**
 * @author 정순원
 * @since 23-10-13
 */
@Getter
public enum ResultCode {

    // 정상 처리
    OK("P000", "요청 정상 처리"),

    // 서버 내부 에러 (5xx 에러)
    INTERNAL_SERVER_ERROR("P100", "서버 에러 발생"),

    // F2xx: JSon 값 예외
    NOT_VALIDATION("P200", "json 값이 올바르지 않습니다."),

    // P3xx: 인증, 권한에 대한 예외
    MAIL_AUTHNUMBER_NOT("P300", "인증번호가 틀립니다."),
    AUTH_USER_NOT("F301", "현재 권한으로 접근 불가능합니다."),
    JWT_DATE_NOT("F302", "JWT토큰이 만료되었습니다."),

    // P4xx: 유저 예외
    USER_NOT_FOUND("F400", "존재하지 않는 유저입니다."),
    USER_MANY_REQUEST("F401", "사용자의 API요청이 제한됩니다."),
    USER_UNIVERSITY_CERTIFICATION_NOT_FOUND("F402", "유저의 대학 정보가 없습니다");

    // P5xx 주문예외

    // P6xx 상품예외

    private final String code;
    private final String message;


    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
