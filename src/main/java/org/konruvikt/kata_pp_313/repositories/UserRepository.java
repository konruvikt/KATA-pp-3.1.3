package org.konruvikt.kata_pp_313.repositories;

import org.konruvikt.kata_pp_313.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "user-entity-graph")
    @Query("select u from User u where u.login = :userName")
    User findByUsername(@Param("userName") String userName);
}
