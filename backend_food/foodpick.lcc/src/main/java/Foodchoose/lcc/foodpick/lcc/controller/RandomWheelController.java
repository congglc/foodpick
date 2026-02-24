package Foodchoose.lcc.foodpick.lcc.controller;

import Foodchoose.lcc.foodpick.lcc.dto.randomwheel.RandomWheelRequest;
import Foodchoose.lcc.foodpick.lcc.dto.randomwheel.RandomWheelResponse;
import Foodchoose.lcc.foodpick.lcc.service.RandomWheelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wheels")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RandomWheelController {

    private final RandomWheelService randomWheelService;

    @PostMapping
    public ResponseEntity<RandomWheelResponse> createWheel(@Valid @RequestBody RandomWheelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(randomWheelService.createWheel(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RandomWheelResponse> getWheelById(@PathVariable Long id) {
        return ResponseEntity.ok(randomWheelService.getWheelById(id));
    }
}
