package com.houseagent.dao;

import com.houseagent.entity.Agent;
import com.houseagent.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HouseDao extends JpaRepository<House, Long>{

    //查找当前用户的房子
    @Query(value = "select * from t_house where owner_id = ?;", nativeQuery = true)
    List<House> findHouseByUserId(Long userId);

    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, agent_name, area, unit_price, room, is_published, is_finished as 'statue' from t_house, t_agent where is_published!='2' and t_house.agent_id = t_agent.agent_id and is_sold = '1' and owner_id = ?;", nativeQuery = true)
    List<Map<String, String>> allSaleFinished(Long userId);

    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, agent_name, area, rent_money, room, is_published, is_finished from t_house, t_agent where  is_published!='2' and t_house.agent_id = t_agent.agent_id and is_rent = '1' and owner_id = ?;", nativeQuery = true)
    List<Map<String, String>> allRentFinished(Long userId);

    @Modifying
    @Query(value = "update t_house set house_name = ?, agent_id = ?, area = ?, unit_price = ?, room = ? where house_id = ?;", nativeQuery = true)
    void edit(String houseName, Long agentId, double area, double unitPrice, String room, Long houseId);

    @Modifying
    @Query(value = "update t_house set house_name = ?, agent_id = ?, area = ?, rent_money = ?, room = ? where house_id = ?;", nativeQuery = true)
    void editRent(String houseName, Long agentId, double area, double rentMoney, String room, Long houseId);

    @Modifying
    @Query(value = "update t_house set is_published = '2' where house_id = ?;", nativeQuery = true)
    void deleteHouse(Long houseId);

    @Modifying
    @Query(value = "update t_house set is_published = '2' where house_id = ?;", nativeQuery = true)
    void deleteRentHouse(Long houseId);

    @Modifying
    @Query(value = "insert t_house (owner_id, house_name, agent_id, area, unit_price, room, is_published, is_rent, is_sold, is_finished, rent_money) values(?, ?, ?, ?, ?, ?, '0', '0', '1', '0', 0);", nativeQuery = true)
    void addSaleHouse(Long ownerId, String houseName, Long agentId, double area, double unitPrice, String room);

    @Modifying
    @Query(value = "insert t_house (owner_id, house_name, agent_id, area, rent_money, room, is_published, is_rent, is_sold, is_finished, unit_price) values(?, ?, ?, ?, ?, ?, '0', '1', '0', '0', 0);", nativeQuery = true)
    void addRentHouse(Long ownerId, String houseName, Long agentId, double area, double rentMoney, String room);
}
