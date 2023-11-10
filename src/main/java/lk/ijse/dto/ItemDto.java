package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @auther sachin
 * @date 2023-11-10
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private String code;
    private String description;
    private double unit_price;
    private int qty_on_hand;
}
