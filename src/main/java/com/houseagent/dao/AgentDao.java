package com.houseagent.dao;

import com.houseagent.entity.Agent;
import com.houseagent.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AgentDao extends JpaRepository<Agent, Long> {

    //自定义添加
//    @Modifying //测试时用Rollback为false
//    @Query(value = "insert into t_agent(agent_name, password, sex, phone_number) value(?,?,?,?);", nativeQuery = true)
//    void saveOne(String name, String psw, String sex, String pn);

    Agent findByAgentId(Long id);

    List<Agent> findAll();

    //中介查看自己经手的房屋
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, username, area, unit_price, room, is_rent,is_sold,is_finished as 'statue' from t_house, t_user where t_house.owner_id = t_user.user_id and is_published = '1' and agent_id = ?;", nativeQuery = true)
    List<Map<String, String>> findHouseByAgentId(Long id);

    //中介查看自己已经完成的售房订单
    @Query(value = "select sale_id, house_name, t_sale_record.buy_time, t_house.create_time, t1.username as 'ownername', t2.username as 'buyer_name', area, unit_price, room,area * t_house.unit_price as 'final_money'from t_sale_record, t_house, t_user t1, t_user t2, t_agent where t_house.owner_id = t1.user_id and  t_sale_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t2.user_id = t_sale_record.buyer_id and t_house.agent_id = ?;", nativeQuery = true)
    List<Map<String, String>> allSaleFinished(Long agentId);

    //中介查看自己已经完成的租房订单
    @Query(value = "select rent_id, house_name, t_rent_record.begin_time, rent_length, t2.username as 'renter_name', t1.username as 'ownername', area, rent_money, room , t_house.create_time from t_rent_record, t_house, t_user t1, t_user t2, t_agent where t_house.owner_id = t1.user_id and  t_rent_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t2.user_id = t_rent_record.renter_id and t_house.agent_id = ?;", nativeQuery = true)
    List<Map<String, String>> allRentFinished(Long agentId);

    //中介查看用户待发布新房源
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, username, area, unit_price, room, is_rent,is_sold,is_finished as 'statue' from t_house, t_user where t_house.owner_id = t_user.user_id and is_published = '0' and agent_id = ?;", nativeQuery = true)
    List<Map<String, String>> waitPubish(Long agentId);

    //中介查看用户待发布的出租房源
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, username, area, rent_money, room from t_house, t_user where t_house.owner_id = t_user.user_id and is_published = '0' and is_rent = '1' and agent_id = ?;", nativeQuery = true)
    List<Map<String, String>> rentWaitPubish(Long agentId);

    //中介查看用户待发布的买卖房源
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, username, area, unit_price, room from t_house, t_user where t_house.owner_id = t_user.user_id and is_published = '0' and is_sold = '1' and agent_id = ?;", nativeQuery = true)
    List<Map<String, String>> saleWaitPubish(Long agentId);

    //中介确认用户待发布新房源
    @Modifying
    @Query(value = "UPDATE t_house SET is_published = '1' where house_id = ?;", nativeQuery = true)
    void setPubish(Long houseId);

    //中介查看用户待确认的租赁合同
    @Query(value = "select rent_id, house_name, t_house.house_id, t_rent_record.begin_time, rent_length, t2.username as 'renter_name', t1.username as 'ownername', area, rent_money, room , t_house.create_time from t_rent_record, t_house, t_user t1, t_user t2, t_agent where t_house.owner_id = t1.user_id and  t_rent_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t2.user_id = t_rent_record.renter_id and t_house.agent_id = ? and valid = '1' and t_house.is_finished = '1';", nativeQuery = true)
    List<Map<String, String>> rentWaitFinish(Long agentId);

    //租赁合同的模糊搜索
    @Query(value = "select rent_id, house_name, t_house.house_id, t_rent_record.begin_time, rent_length, t2.username as 'renter_name', t1.username as 'ownername', area, rent_money, room , t_house.create_time from t_rent_record, t_house, t_user t1, t_user t2, t_agent where t_house.owner_id = t1.user_id and  t_rent_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t2.user_id = t_rent_record.renter_id and t_house.agent_id = ? and valid = '1' and t_house.is_finished = '1' and house_name like ?;", nativeQuery = true)
    List<Map<String, String>> rentSearch(Long agentId, String s);

    //买卖合同的模糊搜索
    @Query(value = "select sale_id, house_name, t_house.house_id, t_sale_record.buy_time, t_house.create_time, t1.username as 'ownername', t2.username as 'buyer_name', area, unit_price, room,area * t_house.unit_price as 'final_money'from t_sale_record, t_house, t_user t1, t_user t2, t_agent where t_house.owner_id = t1.user_id and  t_sale_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t2.user_id = t_sale_record.buyer_id and t_house.agent_id = ? and valid = '1' and t_house.is_finished = '1' and house_name like ?;", nativeQuery = true)
    List<Map<String, String>> buySearch(Long agentId, String s);

    //中介查看用户待确认的买卖合同
    @Query(value = "select sale_id, house_name, t_house.house_id, t_sale_record.buy_time, t_house.create_time, t1.username as 'ownername', t2.username as 'buyer_name', area, unit_price, room,area * t_house.unit_price as 'final_money'from t_sale_record, t_house, t_user t1, t_user t2, t_agent where t_house.owner_id = t1.user_id and  t_sale_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t2.user_id = t_sale_record.buyer_id and t_house.agent_id = ? and valid = '1' and t_house.is_finished = '1';", nativeQuery = true)
    List<Map<String, String>> saleWaitFinish(Long agentId);

    //中介确认用户交易的房源
    @Modifying
    @Query(value = "UPDATE t_house SET is_finished = :status where house_id = :houseId ;", nativeQuery = true)
    void setFinish(@Param("houseId") Long houseId, @Param("status") int status);

    Agent findByAgentNameIs(String agentname);

}
