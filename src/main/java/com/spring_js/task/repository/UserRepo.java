package com.spring_js.task.repository;

import com.spring_js.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roleSet r WHERE  u.name = (:username)")
    User findByName(@Param("username") String name);
}
