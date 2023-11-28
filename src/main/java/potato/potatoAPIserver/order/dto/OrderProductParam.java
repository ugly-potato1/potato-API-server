package potato.potatoAPIserver.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.potatoAPIserver.order.domain.OrderProduct;
import potato.potatoAPIserver.product.dto.ProductParam;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderProductParam {

    private Long orderProductId;
    private int count;
    private String name;
    private BigDecimal price;
    private ProductParam productParam;

    public static OrderProductParam from(OrderProduct entity) {
        return new OrderProductParam(entity.getId(), entity.getCount(), entity.getName(), entity.getPrice(), ProductParam.from(entity.getProduct()));
    }
}
