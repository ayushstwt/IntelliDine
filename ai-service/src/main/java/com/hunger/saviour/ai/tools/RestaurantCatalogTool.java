package com.hunger.saviour.ai.tools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RestaurantCatalogTool {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantSearchResult {
        private String name;
        private String cuisine;
        private double rating;
        private List<String> topDishes;
    }

    public List<RestaurantSearchResult> searchRestaurants(String query) {
        log.info("AI Tool executed: searchRestaurants with query: {}", query);
        return List.of(
                RestaurantSearchResult.builder()
                        .name("Spicy Symphony")
                        .cuisine("Indian, Biryani")
                        .rating(4.8)
                        .topDishes(List.of("Hyderabadi Chicken Biryani", "Paneer Butter Masala", "Garlic Naan"))
                        .build(),
                RestaurantSearchResult.builder()
                        .name("Pasta Paradise")
                        .cuisine("Italian")
                        .rating(4.6)
                        .topDishes(List.of("Fettuccine Alfredo", "Truffle Mushroom Risotto", "Tiramisu"))
                        .build()
        );
    }
}
