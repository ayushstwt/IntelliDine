package com.hunger.saviour.portal.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class RestaurantMenuDTO {
    private Integer restaurantMenuId;
    @NotBlank(message = "Restaurant menu item is mandatory")
    private String itemName;
    @NotBlank(message = "Restaurant menu item description is mandatory")
    private String description;
    @NotBlank(message = "Restaurant menu item image url is mandatory")
    private String menuImageUrl;
    @NotBlank(message = "Restaurant menu item menu-type is mandatory")
    private String menuType;
    @NotBlank(message = "Restaurant menu item price is mandatory")
    private Double price;
}
