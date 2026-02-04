package Foodchoose.lcc.foodpick.lcc.repository;

import Foodchoose.lcc.foodpick.lcc.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category>findbyName(String name);

    Boolean existsByName(String name);

    List<Category> findAllByIsActiveTrueOrderByDisplayOrderAsc();

    List<Category> findAllByOrderByDisplayOrderAsc();

//    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.foods WHERE c.id = :id")
//    Optional<Category> findByIdWithFoods(Long id);
}
