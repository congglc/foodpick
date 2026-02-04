package Foodchoose.lcc.foodpick.lcc.service;

import Foodchoose.lcc.foodpick.lcc.dto.restaurant.RestaurantRequest;
import Foodchoose.lcc.foodpick.lcc.dto.restaurant.RestaurantResponse;
import Foodchoose.lcc.foodpick.lcc.entity.Restaurant;
import Foodchoose.lcc.foodpick.lcc.entity.User;
import Foodchoose.lcc.foodpick.lcc.exception.ResourceNotFoundException;
import Foodchoose.lcc.foodpick.lcc.mapper.RestaurantMapper;
import Foodchoose.lcc.foodpick.lcc.repository.RestaurantRepository;
import Foodchoose.lcc.foodpick.lcc.repository.UserRepository;
import Foodchoose.lcc.foodpick.lcc.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RestaurantMapper restaurantMapper;

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> getAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .map(restaurantMapper::entityToResponse);
    }

    @Transactional(readOnly = true)
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        return restaurantMapper.entityToResponse(restaurant);
    }

    @Transactional
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User owner = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        Restaurant restaurant = restaurantMapper.requestToEntity(request);
        restaurant.setOwner(owner);
        restaurant.setIsActive(true);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.entityToResponse(savedRestaurant);
    }

    @Transactional
    public RestaurantResponse updateRestaurant(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        
        // Check if current user is owner? For now, assume service layer is secure or handled by controller/security config
        // Or we can add a check here.

        restaurantMapper.updateEntityFromRequest(request, restaurant);
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.entityToResponse(updatedRestaurant);
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant", "id", id);
        }
        restaurantRepository.deleteById(id);
    }
}
