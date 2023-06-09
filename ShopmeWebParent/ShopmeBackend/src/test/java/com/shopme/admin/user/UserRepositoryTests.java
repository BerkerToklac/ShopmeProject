package com.shopme.admin.user;

import com.shopme.admin.user.Repository.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateUserWithOneRole(){
        Role roleAdmin = testEntityManager.find(Role.class,1);
        User user = new User("berkertk@codejava.net","berker2023","Berker","Toklac");
        user.addRole(roleAdmin);

        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles(){
        User userRavi = new User("ravi@gmail.com","ravi2023","Ravi","Kumar");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssistant);

        User savedUser = userRepository.save(userRavi);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User userNam = userRepository.findById(1).get();
        System.out.println(userNam);
        assertThat(userNam).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userNam = userRepository.findById(1).get();
        userNam.setEnabled(false);
        userNam.setEmail("berkerjavaprogrammer@gmail.com");

        userRepository.save(userNam);
    }

    @Test
    public void testUpdateUserRoles(){
        User userBerk = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesPerson = new Role(2);
        userBerk.getRoles().remove(roleEditor);
        userBerk.addRole(roleSalesPerson);

        userRepository.save(userBerk);
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        userRepository.deleteById(userId);

    }

    @Test
    public void testGetUserByEmail(){
        String email = "berker@gmail.com";
        User user = userRepository.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id = 1;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableUser(){
        Integer id = 1;
        userRepository.updateEnabledStatus(id, false);
    }

    @Test
    public void testEnableUser(){
        Integer id = 1;
        userRepository.updateEnabledStatus(id, true);

    }

    @Test
    public void testListFirstPage(){
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = (Pageable) PageRequest.of(pageNumber,pageSize);
        Page<User> page = userRepository.findAll((org.springframework.data.domain.Pageable) pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));
        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers(){
        String keyword = "bruce";
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = (Pageable) PageRequest.of(pageNumber,pageSize);
        Page<User> page = userRepository.findAll((org.springframework.data.domain.Pageable) pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(user -> System.out.println(user));
        assertThat(listUsers.size()).isGreaterThan(0);
    }

}
