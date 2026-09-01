package com.hunger.saviour.portal.services;

import com.hunger.saviour.portal.dtos.RestaurantDTO;
import com.hunger.saviour.portal.entities.RestaurantEntity;
import org.springframework.data.domain.Page;


public interface RestaurantService {
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);
    public Page<RestaurantDTO> getRestaurants(int offset, int pagesize);
    public RestaurantDTO getRestaurantById(Integer restaurantId);

}
