package com.test.restful.mapper;

import com.test.restful.entity.AddressEntity;
import com.test.restful.entity.CompanyEntity;
import com.test.restful.entity.GeoEntity;
import com.test.restful.entity.UserEntity;
import com.test.restful.model.Address;
import com.test.restful.model.Company;
import com.test.restful.model.Geo;
import com.test.restful.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPhone(entity.getPhone());
        user.setWebsite(entity.getWebsite());
        
        if (entity.getAddress() != null) {
            Address address = new Address();
            address.setStreet(entity.getAddress().getStreet());
            address.setSuite(entity.getAddress().getSuite());
            address.setCity(entity.getAddress().getCity());
            address.setZipcode(entity.getAddress().getZipcode());
            
            if (entity.getAddress().getGeo() != null) {
                Geo geo = new Geo();
                geo.setLat(entity.getAddress().getGeo().getLat());
                geo.setLng(entity.getAddress().getGeo().getLng());
                address.setGeo(geo);
            }
            
            user.setAddress(address);
        }
        
        if (entity.getCompany() != null) {
            Company company = new Company();
            company.setName(entity.getCompany().getName());
            company.setCatchPhrase(entity.getCompany().getCatchPhrase());
            company.setBs(entity.getCompany().getBs());
            user.setCompany(company);
        }
        
        return user;
    }
    
    public UserEntity toEntity(User dto) {
        if (dto == null) {
            return null;
        }
        
        UserEntity userEntity = new UserEntity();
        userEntity.setId(dto.getId());
        userEntity.setName(dto.getName());
        userEntity.setUsername(dto.getUsername());
        userEntity.setEmail(dto.getEmail());
        userEntity.setPhone(dto.getPhone());
        userEntity.setWebsite(dto.getWebsite());
        
        if (dto.getAddress() != null) {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(dto.getAddress().getStreet());
            addressEntity.setSuite(dto.getAddress().getSuite());
            addressEntity.setCity(dto.getAddress().getCity());
            addressEntity.setZipcode(dto.getAddress().getZipcode());
            
            if (dto.getAddress().getGeo() != null) {
                GeoEntity geoEntity = new GeoEntity();
                geoEntity.setLat(dto.getAddress().getGeo().getLat());
                geoEntity.setLng(dto.getAddress().getGeo().getLng());
                addressEntity.setGeo(geoEntity);
            }
            
            userEntity.setAddress(addressEntity);
        }
        
        if (dto.getCompany() != null) {
            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setName(dto.getCompany().getName());
            companyEntity.setCatchPhrase(dto.getCompany().getCatchPhrase());
            companyEntity.setBs(dto.getCompany().getBs());
            userEntity.setCompany(companyEntity);
        }
        
        return userEntity;
    }
    
    public void updateEntityFromDto(User dto, UserEntity entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setWebsite(dto.getWebsite());
        
        // Handle address update
        if (dto.getAddress() != null) {
            if (entity.getAddress() == null) {
                entity.setAddress(new AddressEntity());
            }
            
            entity.getAddress().setStreet(dto.getAddress().getStreet());
            entity.getAddress().setSuite(dto.getAddress().getSuite());
            entity.getAddress().setCity(dto.getAddress().getCity());
            entity.getAddress().setZipcode(dto.getAddress().getZipcode());
            
            // Handle geo update
            if (dto.getAddress().getGeo() != null) {
                if (entity.getAddress().getGeo() == null) {
                    entity.getAddress().setGeo(new GeoEntity());
                }
                
                entity.getAddress().getGeo().setLat(dto.getAddress().getGeo().getLat());
                entity.getAddress().getGeo().setLng(dto.getAddress().getGeo().getLng());
            }
        }
        
        // Handle company update
        if (dto.getCompany() != null) {
            if (entity.getCompany() == null) {
                entity.setCompany(new CompanyEntity());
            }
            
            entity.getCompany().setName(dto.getCompany().getName());
            entity.getCompany().setCatchPhrase(dto.getCompany().getCatchPhrase());
            entity.getCompany().setBs(dto.getCompany().getBs());
        }
    }
}