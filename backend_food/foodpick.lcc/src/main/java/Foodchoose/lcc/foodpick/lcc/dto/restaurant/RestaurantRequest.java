package Foodchoose.lcc.foodpick.lcc.dto.restaurant;

import lombok.Data;

@Data
public class RestaurantRequest {
    private String name;
    private String address;
    private String phone;
    private String openTime;
    private String closeTime;
    private String imageUrl;
    private String description;
}
