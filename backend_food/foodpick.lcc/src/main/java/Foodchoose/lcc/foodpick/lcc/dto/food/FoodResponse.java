package Foodchoose.lcc.foodpick.lcc.dto.food;

import Foodchoose.lcc.foodpick.lcc.dto.category.CategoryResponse;
import Foodchoose.lcc.foodpick.lcc.dto.restaurant.RestaurantResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodResponse {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private Boolean isActive;
    private RestaurantResponse restaurant;
    private CategoryResponse category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
