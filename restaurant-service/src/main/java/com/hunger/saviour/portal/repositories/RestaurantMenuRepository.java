package com.hunger.saviour.portal.repositories;

import com.hunger.saviour.portal.entities.RestaurantMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenuEntity,Integer> {
}
