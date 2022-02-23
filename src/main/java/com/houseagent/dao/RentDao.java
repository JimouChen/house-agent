package com.houseagent.dao;

import com.houseagent.entity.House;
import com.houseagent.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentDao extends JpaRepository<Rent, Long> {

    //查看自己租入房子记录
    @Query(value = "select rent_id, house_name, t_rent_record.begin_time, rent_length, username as 'owner_name', agent_name, area, rent_money, room from t_rent_record, t_house, t_user, t_agent where valid = '2' and t_rent_record.renter_id = ? and t_rent_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t_user.user_id = t_house.owner_id;", nativeQuery = true)
    List<Map<String, String>> rentInRecord(Long userId);

    //查看自己租出房子记录
    @Query(value = "select rent_id, house_name, t_rent_record.begin_time, rent_length, username as 'renter_name', agent_name, area, rent_money, room from t_rent_record, t_house, t_user, t_agent where valid = '2' and t_house.owner_id = ? and  t_rent_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t_user.user_id = t_rent_record.renter_id;", nativeQuery = true)
    List<Map<String, String>> rentOutRecord(Long userId);

    //查看可租入房子的展示页面
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, username as 'owner_name', t_agent.agent_name, area, rent_money, room, is_finished from t_user, t_house, t_agent where t_house.agent_id = t_agent.agent_id and t_user.user_id = t_house.owner_id and is_rent = '1' and owner_id != ? and is_published = '1';", nativeQuery = true)
    List<Map<String, String>> rentInHouse(Long userId);

    //可租入房子页面的点击查询
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, username as 'owner_name', t_agent.agent_name, area, rent_money, room, is_finished from t_user, t_house, t_agent where t_house.agent_id = t_agent.agent_id and t_user.user_id = t_house.owner_id and is_rent = '1' and owner_id != ? and is_published = '1' and house_name like ?;", nativeQuery = true)
    List<Map<String, String>> rentInSearch(Long userId, String s);

    //点击租入房子页面的租房按钮,
    @Modifying
    @Query(value = "update t_house set is_finished = '1' where house_id = ?", nativeQuery = true)
    void rentInClick(Long houseId);


    //查看自己租出房页面的展示
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, t_agent.agent_name, area, rent_money, room, is_published from t_user, t_house, t_agent where is_published != '2' and t_house.agent_id = t_agent.agent_id and t_user.user_id = t_house.owner_id and is_rent = '1' and t_house.owner_id = ?;", nativeQuery = true)
    List<Map<String, String>> rentOutHouses(Long userId);


    //设置租房信息表状态位
    @Modifying
    @Query(value = "update t_rent_record set valid = ?2 where rent_id = ?1 ", nativeQuery = true)
    void setIsValid(Long rent_id, int status);

    //设置租房信息表状态位
    @Modifying
    @Query(value = "update t_rent_record set rent_length = ?2 where rent_id = ?1 ", nativeQuery = true)
    void editLength(Long rent_id, int rent_length);

    //查找全部租房记录的人员信息
    @Query(value = "select rent_id, house_name, t_house.house_id, t_rent_record.begin_time, rent_length, t1.username as 'ownername', t2.username as 'renter_name', agent_name, area, rent_money, room , t_house.create_time from t_rent_record, t_house, t_user t1, t_user t2, t_agent where t_house.owner_id = t1.user_id and  t_rent_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t2.user_id = t_rent_record.renter_id ;", nativeQuery = true)
    List<Map<String, String>> findAllRent();
}
