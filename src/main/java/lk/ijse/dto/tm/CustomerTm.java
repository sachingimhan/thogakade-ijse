package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @auther sachin
 * @date 2023-11-08
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private String tel;
}
