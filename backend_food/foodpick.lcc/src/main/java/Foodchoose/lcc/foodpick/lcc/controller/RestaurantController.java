package Foodchoose.lcc.foodpick.lcc.controller;

import Foodchoose.lcc.foodpick.lcc.dto.restaurant.RestaurantRequest;
import Foodchoose.lcc.foodpick.lcc.dto.restaurant.RestaurantResponse;
import Foodchoose.lcc.foodpick.lcc.dto.response.MessageResponse;
import Foodchoose.lcc.foodpick.lcc.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> getAllRestaurants(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(restaurantService.getAllRestaurants(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok(new MessageResponse("Xóa nhà hàng thành công!"));
    }
}
