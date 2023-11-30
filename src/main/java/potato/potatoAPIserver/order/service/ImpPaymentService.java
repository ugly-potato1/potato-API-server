package potato.potatoAPIserver.order.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import potato.potatoAPIserver.common.CustomException;
import potato.potatoAPIserver.common.ResultCode;
import potato.potatoAPIserver.order.dto.ImpRefundDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 정순원
 * @since 23-11-30
 */
@Service
public class ImpPaymentService {

    @Value("${iamport.api-key}")
    private String impKey;
    @Value("${iamport.api-secret-key}")
    private String impSecretKey;
    private IamportClient iamportClient;


    public ImpPaymentService() {
        this.iamportClient = new IamportClient(impKey, impSecretKey);
    }


    public IamportResponse<Payment> validatePayment(String impUid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(impUid);
    }

    //환불
    public void refund(Long orderId, BigDecimal amount, String reason) {

        String impAccessToken = getImpToken();
        ImpRefundDTO impRefundDTO = new ImpRefundDTO(String.valueOf(orderId), amount, reason);

        JSONObject response = WebClient.create()
                .post()
                .uri("https://api.iamport.kr/payments/cancel")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(impAccessToken))
                .bodyValue(impRefundDTO)
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();

        //TODO 가맹점코드(프론트)로 API 테스트해보고 오류 재정의
        if (response.get("code").toString().equals("-1"))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.IMP_NOT_ACCESSTOKEN);
    }


    //사후 검증
    public BigDecimal paymentInfo(String impUid)  {

        String impAccessToken = getImpToken();

        JSONObject response = WebClient.create()
                .get()
                .uri("https://api.iamport.kr/payments/" + impUid)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(impAccessToken))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        //TODO 가맹점코드(프론트)로 API 테스트해보고 오류 재정의
        if (response.get("code").toString().equals("-1"))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.IMP_NOT_ACCESSTOKEN);

        return new BigDecimal(response.get("amount").toString()); //결제금액

    }

    //사전 검증
    public void preValidate(Long orderId) {

        String impAccessToken = getImpToken();

        JSONObject response = WebClient.create()
                .get()
                .uri("https://api.iamport.kr/payments/prepare/" + String.valueOf(orderId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(impAccessToken))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();

        if (response.get("code").toString().equals("-1"))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.IMP_NOT_ACCESSTOKEN);
        if (response.get("code").toString().equals("1"))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.PAYMENT_PREVALIDATION_FAIL);
    }

    //아임포트 토큰 가져오기
    private String getImpToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("imp_key", impKey);
        body.add("imp_secret", impSecretKey);

        JSONObject response = WebClient.create()
                .post()
                .uri("https://api.iamport.kr/users/getToken")
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        if (!response.get("code").toString().equals("0"))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.IMP_NOT_VALIDAION);
        Map<String, Object> impResponse = (Map<String, Object>) response.get("response");
        return impResponse.get("access_token").toString();
    }
}