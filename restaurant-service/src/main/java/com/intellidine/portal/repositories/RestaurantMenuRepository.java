package com.intellidine.portal.repositories;

import com.intellidine.portal.entities.RestaurantMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenuEntity,Integer> {
}
