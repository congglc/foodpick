package Foodchoose.lcc.foodpick.lcc.mapper;


import Foodchoose.lcc.foodpick.lcc.dto.category.CategoryRequest;
import Foodchoose.lcc.foodpick.lcc.dto.category.CategoryResponse;
import Foodchoose.lcc.foodpick.lcc.dto.category.CategorySummaryResponse;
import Foodchoose.lcc.foodpick.lcc.entity.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "is_Active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "foods", ignore = true)
    abstract Category requestToEntity(CategoryRequest request);

    @Mapping(target = "foodCount", expression = "java(getFoodCount(category))")
    CategoryResponse entityToResponse(Category category);

    /**
     * Convert List<Category> → List<CategoryResponse>
     */
    List<CategoryResponse> entitiesToResponses(List<Category> categories);

    /**
     * Convert List<Category> → List<CategorySummaryResponse>
     */
    List<CategorySummaryResponse> entitiesToSummaries(List<Category> categories);

    CategorySummaryResponse entityToSummary(Category category);

    /**
     * Update Category entity from CategoryRequest
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "is_Active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "foods", ignore = true)
    void updateEntityFromRequest(CategoryRequest request, @MappingTarget Category category);

    /**
     * Get food count - Helper method
     */
    default Long getFoodCount(Category category) {
        return category.getFoods() != null ? (long) category.getFoods().size() : 0L;
    }
}
