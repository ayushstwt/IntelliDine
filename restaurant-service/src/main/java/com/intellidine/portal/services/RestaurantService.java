package com.intellidine.portal.services;

import com.intellidine.portal.dtos.RestaurantDTO;
import com.intellidine.portal.entities.RestaurantEntity;
import org.springframework.data.domain.Page;


public interface RestaurantService {
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);
    public Page<RestaurantDTO> getRestaurants(int offset, int pagesize);
    public RestaurantDTO getRestaurantById(Integer restaurantId);

}
