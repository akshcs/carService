package com.assignment.converzai.carService.dao.user;

import com.assignment.converzai.carService.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CommonUserRepository<T extends User> extends JpaRepository<T, Long> {

    Optional<T> findByUserName(String userName);

    Optional<T> findByEmail(String email);

    Optional<T> findByMobile(String mobile);

    @Query("select t from #{#entityName} t inner join t.roles r where r.name in :roleName")
    List<T> findByRoleName(@Param("roleName") String roleName);
}
