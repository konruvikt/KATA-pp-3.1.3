package org.konruvikt.kata_pp_313.services;

import jakarta.annotation.PostConstruct;
import org.konruvikt.kata_pp_313.models.Role;
import org.konruvikt.kata_pp_313.models.User;
import org.konruvikt.kata_pp_313.repositories.RoleRepository;
import org.konruvikt.kata_pp_313.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public User findById(Long id){
        return userRepository.getReferenceById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void saveUser(User user){
        User editedOrNewUser = userRepository.findByUsername(user.getUsername());
        editedOrNewUser.setRoles(Collections.singletonList(new Role(2L, "ROLE_USER")));
        editedOrNewUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(editedOrNewUser);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return user;
    }

    public User findUserByUserName(String userName) {       // нужен?
       return findAll().stream().filter(user -> user.getUsername().equals(userName)).findAny().orElse(null);
    }

    @PostConstruct
    private void postConstruct() {
        Role admin = new Role(1L, "ROLE_ADMIN");
        Role user = new Role(2L, "ROLE_USER");

        List<Role> rolesOfAdmin = new ArrayList<>();
        List<Role> rolesOfUser = new ArrayList<>();

        Collections.addAll(rolesOfAdmin, admin, user);
        Collections.addAll(rolesOfUser, user);

        User adminUser = new User(1L, "name1", "lastname1",
                (byte) 1, "admin", bCryptPasswordEncoder.encode("admin1"), rolesOfAdmin);
        User normalUser = new User(2L, "name2", "lastname2",
                (byte) 2, "user", bCryptPasswordEncoder.encode("user1"), rolesOfUser);

        userRepository.save(adminUser);
        userRepository.save(normalUser);
    }



}
