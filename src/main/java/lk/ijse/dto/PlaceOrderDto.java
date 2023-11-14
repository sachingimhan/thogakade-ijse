package lk.ijse.dto;

import lk.ijse.dto.tm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther sachin
 * @date 2023-11-14
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDto {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private List<CartTm> cartTmList = new ArrayList<>();
}
