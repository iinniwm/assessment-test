package com.test.restful.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "geo")
public class GeoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String lat;
    private String lng;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLat() {
        return lat;
    }
    
    public void setLat(String lat) {
        this.lat = lat;
    }
    
    public String getLng() {
        return lng;
    }
    
    public void setLng(String lng) {
        this.lng = lng;
    }
}