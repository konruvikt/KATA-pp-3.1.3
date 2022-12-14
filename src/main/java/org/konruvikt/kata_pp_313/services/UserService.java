package org.konruvikt.kata_pp_313.services;

import jakarta.annotation.PostConstruct;
import org.konruvikt.kata_pp_313.models.Role;
import org.konruvikt.kata_pp_313.models.User;
import org.konruvikt.kata_pp_313.repositories.RoleRepository;
import org.konruvikt.kata_pp_313.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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

    public User findUserByUserName(String userName) {
       return findAll().stream().filter(user -> user.getUsername().equals(userName)).findAny().orElse(null);
    }

    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @PostConstruct
    private void postConstruct() {
        Role admin = new Role(1L, "ROLE_ADMIN");
        Role user = new Role(2L, "ROLE_USER");
        roleRepository.saveAll(List.of(admin, user));

        User adminUser = new User();
        adminUser.setName("name1");
        adminUser.setLastName("lastname1");
        adminUser.setAge((byte) 33);
        adminUser.setLogin("admin");
        adminUser.setPassword(bCryptPasswordEncoder.encode("admin1"));
        adminUser.addRole(admin);
        adminUser.addRole(user);

        User normalUser = new User();
        normalUser.setName("name2");
        normalUser.setLastName("lastname2");
        normalUser.setAge((byte) 44);
        normalUser.setLogin("user");
        normalUser.setPassword(bCryptPasswordEncoder.encode("user1"));
        normalUser.addRole(user);

        userRepository.save(adminUser);
        userRepository.save(normalUser);
    }
}