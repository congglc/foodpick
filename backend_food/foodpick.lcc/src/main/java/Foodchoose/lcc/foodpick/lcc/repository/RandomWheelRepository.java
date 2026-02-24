package Foodchoose.lcc.foodpick.lcc.repository;

import Foodchoose.lcc.foodpick.lcc.entity.RandomWheel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RandomWheelRepository extends JpaRepository<RandomWheel, Long> {
    List<RandomWheel> findByCreatorId(String creatorId);
}
