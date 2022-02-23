package com.houseagent.dao;

import com.houseagent.entity.House;
import com.houseagent.entity.Rent;
import com.houseagent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    //自定义查询
//    @Query(value = "select * from t_user where username = ?", nativeQuery = true)
//    List<User> findByName(String username);
//
//
//    @Modifying//测试时加上@Rollback(false)
//    @Query(value = "update t_user set username= ? where user_id = ?", nativeQuery = true)
//    void updateUserNameById(String username, Long userId);


    //查询用户自己已发布成功的房信息
    @Query(value = "select * from t_rent_record where is_published = '1' and user_id = ?;", nativeQuery = true)
    List<Rent> rentPublished(Long id);

    //查询成功交易的记录，is_finished = 1的
    @Query(value = "select * from t_rent_record where is_finished = '1' and user_id = ?;", nativeQuery = true)
    List<Rent> rentFinished(Long id);

    User findByUsernameIs(String username);


}
