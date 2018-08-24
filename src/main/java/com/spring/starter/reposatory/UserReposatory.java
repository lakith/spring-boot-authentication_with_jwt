package com.spring.starter.reposatory;

import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spring.starter.model.User;

public interface UserReposatory extends JpaRepository<User, Long> {
	
    @Modifying
    @Query("update User u set u.refreshToken = :reftoken WHERE u.username= :username")
    @Transactional
    int updateRefreshToken(@Param("username") String username, @Param("reftoken") String refreshToken);

    @Query("SELECT u FROM User u WHERE u.username=?1")
    User getUserByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.refreshToken=?1")
    User findByRefreshToken(String refreshToken);
    
    @Query("SELECT u FROM User u WHERE u.username=?1 AND u.password=?2")
    User validateUser(String username,String password);
}
