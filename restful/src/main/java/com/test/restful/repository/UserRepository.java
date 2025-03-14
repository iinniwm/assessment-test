package com.test.restful.repository;


import com.test.restful.model.Address;
import com.test.restful.model.Company;
import com.test.restful.model.Geo;
import com.test.restful.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(3); // Start from 4 as we have 3 initial users

    public UserRepository() {
        // Initialize with sample data
        initializeUsers();
    }

    private void initializeUsers() {
        // User 1
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Leanne Graham");
        user1.setUsername("Bret");
        user1.setEmail("Sincere@april.biz");
        user1.setPhone("1-770-736-8031 x56442");
        user1.setWebsite("hildegard.org");

        Address address1 = new Address();
        address1.setStreet("Kulas Light");
        address1.setSuite("Apt. 556");
        address1.setCity("Gwenborough");
        address1.setZipcode("92998-3874");

        Geo geo1 = new Geo();
        geo1.setLat("-37.3159");
        geo1.setLng("81.1496");
        address1.setGeo(geo1);

        Company company1 = new Company();
        company1.setName("Romaguera-Crona");
        company1.setCatchPhrase("Multi-layered client-server neural-net");
        company1.setBs("harness real-time e-markets");

        user1.setAddress(address1);
        user1.setCompany(company1);
        users.add(user1);

        // User 2
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Ervin Howell");
        user2.setUsername("Antonette");
        user2.setEmail("Shanna@melissa.tv");
        user2.setPhone("010-692-6593 x09125");
        user2.setWebsite("anastasia.net");

        Address address2 = new Address();
        address2.setStreet("Victor Plains");
        address2.setSuite("Suite 879");
        address2.setCity("Wisokyburgh");
        address2.setZipcode("90566-7771");

        Geo geo2 = new Geo();
        geo2.setLat("-43.9509");
        geo2.setLng("-34.4618");
        address2.setGeo(geo2);

        Company company2 = new Company();
        company2.setName("Deckow-Crist");
        company2.setCatchPhrase("Proactive didactic contingency");
        company2.setBs("synergize scalable supply-chains");

        user2.setAddress(address2);
        user2.setCompany(company2);
        users.add(user2);

        // User 3
        User user3 = new User();
        user3.setId(3L);
        user3.setName("Clementine Bauch");
        user3.setUsername("Samantha");
        user3.setEmail("Nathan@yesenia.net");
        user3.setPhone("1-463-123-4447");
        user3.setWebsite("ramiro.info");

        Address address3 = new Address();
        address3.setStreet("Douglas Extension");
        address3.setSuite("Suite 847");
        address3.setCity("McKenziehaven");
        address3.setZipcode("59590-4157");

        Geo geo3 = new Geo();
        geo3.setLat("-68.6102");
        geo3.setLng("-47.0653");
        address3.setGeo(geo3);

        Company company3 = new Company();
        company3.setName("Romaguera-Jacobson");
        company3.setCatchPhrase("Face to face bifurcated interface");
        company3.setBs("e-enable strategic applications");

        user3.setAddress(address3);
        user3.setCompany(company3);
        users.add(user3);
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter.incrementAndGet());
            users.add(user);
        } else {
            users.removeIf(u -> u.getId().equals(user.getId()));
            users.add(user);
        }
        return user;
    }

    public boolean deleteById(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
}

