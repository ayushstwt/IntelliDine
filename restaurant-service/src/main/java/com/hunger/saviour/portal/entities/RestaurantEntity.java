package com.hunger.saviour.portal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Column(name = "rating")
    private String rating;
    @Column(name = "restaurant_image_url")
    private String imageUrl;
    @Column(name = "location")
    private String location;
    @ElementCollection
    @CollectionTable(name = "restaurant_entity_menu_types", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "menu_type")
    private List<String> menuTypes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private List<RestaurantMenuEntity> menus;

}
