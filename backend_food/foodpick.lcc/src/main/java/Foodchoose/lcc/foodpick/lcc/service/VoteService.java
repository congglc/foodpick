package Foodchoose.lcc.foodpick.lcc.service;

import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteRequest;
import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteResponse;
import Foodchoose.lcc.foodpick.lcc.dto.vote.VoteSubmitRequest;
import Foodchoose.lcc.foodpick.lcc.entity.*;
import Foodchoose.lcc.foodpick.lcc.exception.ResourceNotFoundException;
import Foodchoose.lcc.foodpick.lcc.mapper.VoteMapper;
import Foodchoose.lcc.foodpick.lcc.repository.*;
import Foodchoose.lcc.foodpick.lcc.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final VoteRecordRepository voteRecordRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final VoteMapper voteMapper;

    @Transactional
    public VoteResponse createVote(VoteRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User creator = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        Vote vote = voteMapper.requestToEntity(request);
        vote.setCreator(creator);
        // Set default expiry if not provided (e.g. 24 hours)
        if (vote.getExpiresAt() == null) {
            vote.setExpiresAt(LocalDateTime.now().plusHours(24));
        }

        List<Food> foods = foodRepository.findAllById(request.getFoodIds());
        
        // Save vote first to get ID
        Vote savedVote = voteRepository.save(vote);

        List<VoteOption> options = foods.stream().map(food -> {
            VoteOption option = new VoteOption();
            option.setVote(savedVote);
            option.setFood(food);
            option.setVoteCount(0L); // Init 0
            return option;
        }).collect(Collectors.toList());

        voteOptionRepository.saveAll(options);
        savedVote.setOptions(options);

        return voteMapper.entityToResponse(savedVote);
    }

    @Transactional(readOnly = true)
    public VoteResponse getVoteById(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote", "id", id));
        
        // Populate vote counts
        vote.getOptions().forEach(option -> {
            Long count = voteRecordRepository.countByVoteOptionId(option.getId());
            option.setVoteCount(count);
        });

        return voteMapper.entityToResponse(vote);
    }

    @Transactional
    public VoteResponse submitVote(Long id, VoteSubmitRequest request) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote", "id", id));

        if (vote.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Vote has expired");
        }

        VoteOption option = voteOptionRepository.findById(request.getVoteOptionId())
                .orElseThrow(() -> new ResourceNotFoundException("VoteOption", "id", request.getVoteOptionId()));

        if (!option.getVote().getId().equals(id)) {
            throw new RuntimeException("Option does not belong to this vote");
        }

        VoteRecord record = new VoteRecord();
        record.setVote(vote);
        record.setVoteOption(option);
        record.setVoterName(request.getVoterName() != null ? request.getVoterName() : "Anonymous");
        
        voteRecordRepository.save(record);

        // Return updated vote info
        return getVoteById(id);
    }
}
