package lk.ijse.dto;

import lombok.*;

/**
 * @auther sachin
 * @date 2023-11-08
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto {

    private String id;
    private String name;
    private String address;
    private String tel;
}
