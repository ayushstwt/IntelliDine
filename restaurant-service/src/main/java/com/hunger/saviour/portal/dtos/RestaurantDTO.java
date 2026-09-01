package com.hunger.saviour.portal.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDTO {
    private Integer restaurantId;
    @NotBlank(message = "Restaurant name is mandatory")
    private String restaurantName;
    private String rating;
    @NotBlank(message = "Restaurant image is mandatory")
    private String imageUrl;
    @NotBlank(message = "Restaurant description is mandatory")
    private String description;
    @NotBlank(message = "Restaurant location is mandatory")
    private String location;
    @NotNull(message = "Restaurant menu types are mandatory")
    private List<String> menuTypes;
    @NotNull(message = "There must be at least 1 menu item")
    private List<RestaurantMenuDTO> menuItems;
}
