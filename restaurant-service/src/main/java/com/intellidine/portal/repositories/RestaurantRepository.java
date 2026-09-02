package com.intellidine.portal.repositories;

import com.intellidine.portal.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Integer> {

}
