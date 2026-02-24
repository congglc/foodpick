package Foodchoose.lcc.foodpick.lcc.service;

import Foodchoose.lcc.foodpick.lcc.dto.randomwheel.RandomWheelRequest;
import Foodchoose.lcc.foodpick.lcc.dto.randomwheel.RandomWheelResponse;
import Foodchoose.lcc.foodpick.lcc.entity.Food;
import Foodchoose.lcc.foodpick.lcc.entity.RandomWheel;
import Foodchoose.lcc.foodpick.lcc.entity.User;
import Foodchoose.lcc.foodpick.lcc.exception.ResourceNotFoundException;
import Foodchoose.lcc.foodpick.lcc.mapper.RandomWheelMapper;
import Foodchoose.lcc.foodpick.lcc.repository.FoodRepository;
import Foodchoose.lcc.foodpick.lcc.repository.RandomWheelRepository;
import Foodchoose.lcc.foodpick.lcc.repository.UserRepository;
import Foodchoose.lcc.foodpick.lcc.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RandomWheelService {

    private final RandomWheelRepository randomWheelRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final RandomWheelMapper randomWheelMapper;

    @Transactional
    public RandomWheelResponse createWheel(RandomWheelRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User creator = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        RandomWheel wheel = randomWheelMapper.requestToEntity(request);
        wheel.setCreator(creator);

        List<Food> foods = foodRepository.findAllById(request.getFoodIds());
        wheel.setFoods(foods);

        RandomWheel savedWheel = randomWheelRepository.save(wheel);
        return randomWheelMapper.entityToResponse(savedWheel);
    }

    @Transactional(readOnly = true)
    public RandomWheelResponse getWheelById(Long id) {
        RandomWheel wheel = randomWheelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RandomWheel", "id", id));
        return randomWheelMapper.entityToResponse(wheel);
    }
}
