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
    AUTH_USER_NOT("P300", "현재 권한으로 접근 불가능합니다."),
    JWT_DATE_NOT("P301", "JWT토큰이 만료되었습니다."),

    // P4xx: 유저 예외
    USER_NOT_FOUND("P400", "존재하지 않는 유저입니다."),

    // P5xx 주문예외
    ORDER_NOT_FOUND("P500", "존재하지 않은 주문입니다."),

    // P6xx 상품예외
    PRODUCT_NOT_FOUND("P600", "존재하지 않은 상품입니다."),

    // P7xx 리뷰예외
    REVIEW_NOT_FOUND("P700", "존재하지 않은 리뷰입니다."),
    REVIEW_NOT_WRITER("P701", "리뷰 작성자가 아닙니다."),

    // P8xx 장바구니 예외
    CART_PRODUCT_NOT_FOUND("P800", "존재하지 않은 장바구니 상품입니다."),
    QUANTITY_LESS_THAN_ONE("P801", "수량은 1 이상이어야 합니다.");

    private final String code;
    private final String message;


    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
