package Foodchoose.lcc.foodpick.lcc.dto.restaurant;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String openTime;
    private String closeTime;
    private String imageUrl;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
