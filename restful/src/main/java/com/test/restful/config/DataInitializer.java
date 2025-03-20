package com.test.restful.config;

import com.test.restful.entity.AddressEntity;
import com.test.restful.entity.CompanyEntity;
import com.test.restful.entity.GeoEntity;
import com.test.restful.entity.UserEntity;
import com.test.restful.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes sample data when the application starts
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserJpaRepository userRepository;

    @Autowired
    public DataInitializer(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        // Initialize with sample data if repository is empty
        if (userRepository.count() == 0) {
            initializeUsers();
        }
    }

    private void initializeUsers() {
        // User 1
        UserEntity user1 = new UserEntity();
        user1.setName("Leanne Graham");
        user1.setUsername("Bret");
        user1.setEmail("Sincere@april.biz");
        user1.setPhone("1-770-736-8031 x56442");
        user1.setWebsite("hildegard.org");

        AddressEntity address1 = new AddressEntity();
        address1.setStreet("Kulas Light");
        address1.setSuite("Apt. 556");
        address1.setCity("Gwenborough");
        address1.setZipcode("92998-3874");

        GeoEntity geo1 = new GeoEntity();
        geo1.setLat("-37.3159");
        geo1.setLng("81.1496");
        address1.setGeo(geo1);

        CompanyEntity company1 = new CompanyEntity();
        company1.setName("Romaguera-Crona");
        company1.setCatchPhrase("Multi-layered client-server neural-net");
        company1.setBs("harness real-time e-markets");

        user1.setAddress(address1);
        user1.setCompany(company1);
        userRepository.save(user1);

        // User 2
        UserEntity user2 = new UserEntity();
        user2.setName("Ervin Howell");
        user2.setUsername("Antonette");
        user2.setEmail("Shanna@melissa.tv");
        user2.setPhone("010-692-6593 x09125");
        user2.setWebsite("anastasia.net");

        AddressEntity address2 = new AddressEntity();
        address2.setStreet("Victor Plains");
        address2.setSuite("Suite 879");
        address2.setCity("Wisokyburgh");
        address2.setZipcode("90566-7771");

        GeoEntity geo2 = new GeoEntity();
        geo2.setLat("-43.9509");
        geo2.setLng("-34.4618");
        address2.setGeo(geo2);

        CompanyEntity company2 = new CompanyEntity();
        company2.setName("Deckow-Crist");
        company2.setCatchPhrase("Proactive didactic contingency");
        company2.setBs("synergize scalable supply-chains");

        user2.setAddress(address2);
        user2.setCompany(company2);
        userRepository.save(user2);

        // User 3
        UserEntity user3 = new UserEntity();
        user3.setName("Clementine Bauch");
        user3.setUsername("Samantha");
        user3.setEmail("Nathan@yesenia.net");
        user3.setPhone("1-463-123-4447");
        user3.setWebsite("ramiro.info");

        AddressEntity address3 = new AddressEntity();
        address3.setStreet("Douglas Extension");
        address3.setSuite("Suite 847");
        address3.setCity("McKenziehaven");
        address3.setZipcode("59590-4157");

        GeoEntity geo3 = new GeoEntity();
        geo3.setLat("-68.6102");
        geo3.setLng("-47.0653");
        address3.setGeo(geo3);

        CompanyEntity company3 = new CompanyEntity();
        company3.setName("Romaguera-Jacobson");
        company3.setCatchPhrase("Face to face bifurcated interface");
        company3.setBs("e-enable strategic applications");

        user3.setAddress(address3);
        user3.setCompany(company3);
        userRepository.save(user3);
    }
}