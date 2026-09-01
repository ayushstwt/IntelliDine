package com.hunger.saviour.portal.services.impl;

import com.hunger.saviour.common.exception.ResourceNotFoundException;
import com.hunger.saviour.portal.dtos.RestaurantDTO;
import com.hunger.saviour.portal.entities.RestaurantEntity;
import com.hunger.saviour.portal.repositories.RestaurantRepository;
import com.hunger.saviour.portal.services.RestaurantService;
import com.hunger.saviour.portal.utilities.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        log.info("Creating new restaurant: {}", restaurantDTO.getRestaurantName());
        RestaurantEntity restaurantEntity = RestaurantMapper.INSTANCE.DTOtoEntity(restaurantDTO);
        RestaurantEntity savedEntity = this.restaurantRepository.save(restaurantEntity);
        return RestaurantMapper.INSTANCE.entityToDTO(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantDTO> getRestaurants(int offset, int pagesize) {
        log.info("Fetching restaurants page offset={}, pagesize={}", offset, pagesize);
        Page<RestaurantEntity> restaurantEntityPage = this.restaurantRepository.findAll(PageRequest.of(offset, pagesize));
        return restaurantEntityPage.map(RestaurantMapper.INSTANCE::entityToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantDTO getRestaurantById(Integer restaurantId) {
        log.info("Fetching restaurant with id: {}", restaurantId);
        RestaurantEntity entity = this.restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));
        return RestaurantMapper.INSTANCE.entityToDTOWithMenus(entity);
    }
}
