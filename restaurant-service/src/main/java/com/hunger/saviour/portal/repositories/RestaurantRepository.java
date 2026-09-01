package com.hunger.saviour.portal.repositories;

import com.hunger.saviour.portal.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Integer> {

}
