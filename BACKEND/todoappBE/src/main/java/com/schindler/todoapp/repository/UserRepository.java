package com.schindler.todoapp.repository;

import com.schindler.todoapp.domain.MyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<MyAppUser, Long> {

    @Query("SELECT u FROM MyAppUser u WHERE u.isDeleted = false")
    Optional<List<MyAppUser>> findAllNonDeletedUser();


    @Query("SELECT u FROM MyAppUser u WHERE u.userName = :userName AND u.isDeleted = false")
    Optional<MyAppUser> findNonDeletedUserByUserName(@Param("userName") String userName);

    @Query("SELECT u FROM MyAppUser u WHERE u.userEmail = :userEmail AND u.isDeleted = false")
    Optional<MyAppUser> findNonDeletedUserByEmail(@Param("userEmail") String userEmail);

}
