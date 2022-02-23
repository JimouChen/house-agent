package com.houseagent.dao;

import com.houseagent.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SaleDao extends JpaRepository<Sale, Long> {

    //查看自己买房记录
    @Query(value = "select sale_id, house_name, t_sale_record.buy_time, username as 'owner_name', agent_name, area, unit_price, room,area * t_house.unit_price as 'final_money'from t_sale_record, t_house, t_user, t_agent where valid = '2' and t_sale_record.buyer_id = ? and  t_sale_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t_user.user_id = t_house.owner_id;", nativeQuery = true)
    List<Map<String, String>> buyRecord(Long userId);

    //查看自己的卖房记录
    @Query(value = "select sale_id, house_name, t_sale_record.buy_time, username as 'buyer_name', agent_name, area, unit_price, room,area * t_house.unit_price as 'final_money'from t_sale_record, t_house, t_user, t_agent where valid = '2' and t_house.owner_id = ? and  t_sale_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t_user.user_id = t_sale_record.buyer_id;", nativeQuery = true)
    List<Map<String, String>> saleRecord(Long userId);

    //买房页面的展示
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, username as 'owner_name', t_agent.agent_name, area, unit_price, room, is_finished from t_user, t_house, t_agent where t_house.agent_id = t_agent.agent_id and t_user.user_id = t_house.owner_id and is_sold = '1' and owner_id != ? and is_published = '1';", nativeQuery = true)
    List<Map<String, String>> buyHouse(Long userId);

    //买房页面模糊查询
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, username as 'owner_name', t_agent.agent_name, area, unit_price, room, is_finished from t_user, t_house, t_agent where t_house.agent_id = t_agent.agent_id and t_user.user_id = t_house.owner_id and is_sold = '1' and owner_id != ? and is_published = '1' and house_name like ?;", nativeQuery = true)
    List<Map<String, String>> buySearch(Long userId, String s);

    //点击买，触发操作
    @Modifying
    @Query(value = "update t_house set is_finished = '1' where house_id = ?", nativeQuery = true)
    void buyClick(Long houseId);

    //卖房页面的展示
    @Query(value = "select house_id, house_name, t_house.create_time, t_house.update_time, t_agent.agent_name, area, unit_price, room, is_published from t_user, t_house, t_agent where is_published != '2' and t_house.agent_id = t_agent.agent_id and t_user.user_id = t_house.owner_id and is_sold = '1' and t_house.owner_id = ?;", nativeQuery = true)
    List<Map<String, String>> saleHouses(Long userId);

    //设置买卖房信息表状态位
    @Modifying
    @Query(value = "update t_sale_record set valid = ?2 where sale_id = ?1 ;", nativeQuery = true)
    void setIsValid(Long sale_id, int status);

    //查找售房记录全部人员的信息
    @Query(value = "select sale_id, house_name, t_sale_record.buy_time, t_house.create_time, t1.username as 'ownername', t2.username as 'buyer_name', agent_name, area, unit_price, room,area * t_house.unit_price as 'final_money'from t_sale_record, t_house, t_user t1, t_user t2, t_agent where t_house.owner_id = t1.user_id and t_sale_record.house_id = t_house.house_id and t_house.agent_id = t_agent.agent_id and t2.user_id = t_sale_record.buyer_id", nativeQuery = true)
    List<Map<String, String>> findAllSale();
}
