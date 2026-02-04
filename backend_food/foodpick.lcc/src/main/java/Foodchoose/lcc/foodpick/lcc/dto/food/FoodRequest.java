package Foodchoose.lcc.foodpick.lcc.dto.food;

import lombok.Data;

@Data
public class FoodRequest {
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private Long restaurantId;
    private Long categoryId;
}
