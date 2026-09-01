package com.hunger.saviour.portal.utilities;

import com.hunger.saviour.portal.dtos.RestaurantDTO;
import com.hunger.saviour.portal.entities.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    RestaurantDTO entityToDTO(RestaurantEntity restaurantEntity);

    @Mapping(source = "menus", target="menuItems")
    RestaurantDTO entityToDTOWithMenus(RestaurantEntity restaurantEntity);

    RestaurantEntity DTOtoEntity(RestaurantDTO restaurantDTO);
}
