package Foodchoose.lcc.foodpick.lcc.repository;

import Foodchoose.lcc.foodpick.lcc.entity.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {
    Long countByVoteOptionId(Long voteOptionId);
    List<VoteRecord> findByVoteId(Long voteId);
}
