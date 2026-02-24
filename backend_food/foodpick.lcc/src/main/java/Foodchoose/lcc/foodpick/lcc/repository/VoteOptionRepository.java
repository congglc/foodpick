package Foodchoose.lcc.foodpick.lcc.repository;

import Foodchoose.lcc.foodpick.lcc.entity.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteOptionRepository extends JpaRepository<VoteOption, Long> {
}
