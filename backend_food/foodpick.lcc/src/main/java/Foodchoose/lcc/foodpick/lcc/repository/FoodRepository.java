package Foodchoose.lcc.foodpick.lcc.repository;

import Foodchoose.lcc.foodpick.lcc.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByIsActiveTrue();
    List<Food> findByRestaurantId(Long restaurantId);
    List<Food> findByCategoryId(Long categoryId);
}
