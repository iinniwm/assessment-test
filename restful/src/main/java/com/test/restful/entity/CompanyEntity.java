package com.test.restful.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String catchPhrase;
    private String bs;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCatchPhrase() {
        return catchPhrase;
    }
    
    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }
    
    public String getBs() {
        return bs;
    }
    
    public void setBs(String bs) {
        this.bs = bs;
    }
}