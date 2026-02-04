package Foodchoose.lcc.foodpick.lcc.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class CategorySummaryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Long foodCount;
}
