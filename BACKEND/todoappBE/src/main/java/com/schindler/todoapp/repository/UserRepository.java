package com.schindler.todoapp.repository;

import com.schindler.todoapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    Optional<List<User>> findAllNonDeletedUser();


    @Query("SELECT u FROM User u WHERE u.userId = :userName AND u.isDeleted = false")
    Optional<User> findNonDeletedUserById(@Param("userName") String userName);

}
