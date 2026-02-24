package Foodchoose.lcc.foodpick.lcc.controller;

import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteRequest;
import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteResponse;
import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteSubmitRequest;
import Foodchoose.lcc.foodpick.lcc.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteResponse> createVote(@Valid @RequestBody VoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.createVote(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteResponse> getVoteById(@PathVariable Long id) {
        return ResponseEntity.ok(voteService.getVoteById(id));
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<VoteResponse> submitVote(@PathVariable Long id, @Valid @RequestBody VoteSubmitRequest request) {
        return ResponseEntity.ok(voteService.submitVote(id, request));
    }
}
