package com.hunger.saviour.portal.apis;

import com.hunger.saviour.common.dto.ApiResponse;
import com.hunger.saviour.common.dto.PageResponse;
import com.hunger.saviour.common.filter.TraceIdFilter;
import com.hunger.saviour.portal.dtos.RestaurantDTO;
import com.hunger.saviour.portal.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantAPI {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<ApiResponse<RestaurantDTO>> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO, HttpServletRequest request) {
        log.info("Received request to create restaurant: {}", restaurantDTO.getRestaurantName());
        RestaurantDTO restaurant = this.restaurantService.createRestaurant(restaurantDTO);
        return new ResponseEntity<>(
                ApiResponse.ok(restaurant, "Restaurant created successfully", request.getRequestURI(), TraceIdFilter.getTraceId()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse<RestaurantDTO>> getRestaurantById(@PathVariable Integer restaurantId, HttpServletRequest request) {
        RestaurantDTO restaurant = this.restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(
                ApiResponse.ok(restaurant, "Restaurant retrieved successfully", request.getRequestURI(), TraceIdFilter.getTraceId())
        );
    }

    @GetMapping("/test/hi")
    public ResponseEntity<ApiResponse<String>> another(HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Hi accessed", "Service is healthy", request.getRequestURI(), TraceIdFilter.getTraceId()));
    }

    @GetMapping("/{offset}/{pagesize}")
    public ResponseEntity<ApiResponse<PageResponse<RestaurantDTO>>> getRestaurants(
            @PathVariable int offset,
            @PathVariable int pagesize,
            HttpServletRequest request) {
        Page<RestaurantDTO> page = restaurantService.getRestaurants(offset, pagesize);
        PageResponse<RestaurantDTO> pageResponse = PageResponse.from(page);
        return ResponseEntity.ok(
                ApiResponse.ok(pageResponse, "Restaurants retrieved successfully", request.getRequestURI(), TraceIdFilter.getTraceId())
        );
    }
}

