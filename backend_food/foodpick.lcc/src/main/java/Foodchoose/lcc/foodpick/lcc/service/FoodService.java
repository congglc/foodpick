package Foodchoose.lcc.foodpick.lcc.service;

import Foodchoose.lcc.foodpick.lcc.dto.food.FoodRequest;
import Foodchoose.lcc.foodpick.lcc.dto.food.FoodResponse;
import Foodchoose.lcc.foodpick.lcc.entity.Category;
import Foodchoose.lcc.foodpick.lcc.entity.Food;
import Foodchoose.lcc.foodpick.lcc.entity.Restaurant;
import Foodchoose.lcc.foodpick.lcc.exception.ResourceNotFoundException;
import Foodchoose.lcc.foodpick.lcc.mapper.FoodMapper;
import Foodchoose.lcc.foodpick.lcc.repository.CategoryRepository;
import Foodchoose.lcc.foodpick.lcc.repository.FoodRepository;
import Foodchoose.lcc.foodpick.lcc.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final FoodMapper foodMapper;

    @Transactional(readOnly = true)
    public Page<FoodResponse> getAllFoods(Pageable pageable) {
        return foodRepository.findAll(pageable)
                .map(foodMapper::entityToResponse);
    }

    @Transactional(readOnly = true)
    public FoodResponse getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "id", id));
        return foodMapper.entityToResponse(food);
    }

    @Transactional
    public FoodResponse createFood(FoodRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", request.getRestaurantId()));
        
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Food food = foodMapper.requestToEntity(request);
        food.setRestaurant(restaurant);
        food.setCategory(category);
        food.setIsActive(true);

        Food savedFood = foodRepository.save(food);
        return foodMapper.entityToResponse(savedFood);
    }

    @Transactional
    public FoodResponse updateFood(Long id, FoodRequest request) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "id", id));

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", request.getRestaurantId()));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        foodMapper.updateEntityFromRequest(request, food);
        food.setRestaurant(restaurant);
        food.setCategory(category);

        Food updatedFood = foodRepository.save(food);
        return foodMapper.entityToResponse(updatedFood);
    }

    @Transactional
    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new ResourceNotFoundException("Food", "id", id);
        }
        foodRepository.deleteById(id);
    }
}
