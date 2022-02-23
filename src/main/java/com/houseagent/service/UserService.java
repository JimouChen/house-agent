package com.houseagent.service;

import com.houseagent.entity.Agent;
import com.houseagent.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface UserService {
    void add(User user);

    void update(User user);

    User findById(Long id);

    User findByName(String username);

    List<User> findAllUser();

    Page<User> findByPage(Integer page, Integer size, String sortWay);

    //查询用户自己已发布成功的房信息
//    List<Rent> rentPublished(Long id);


    //查看自己卖出的房源，若需要有序就用TreeMap，但是对前端无影响，Map更快
    List<Map<String, String>> allSale(Long userId);

    //查看自己租出的房源
    List<Map<String, String>> allRent(Long userId);


    //查看买房记录
    List<Map<String, String>> buyRecord(Long userId);

    //查看卖房记录
    List<Map<String, String>> saleRecord(Long userId);

    //查看租入记录
    List<Map<String, String>> rentInRecord(Long userId);

    //查看租出记录
    List<Map<String, String>> rentOutRecord(Long userId);

    //租入房子
    List<Map<String, String>> rentInHouse(Long userId);

    //租入房子页面的查询
    List<Map<String, String>> rentInSearch(Long userId, String s);

    //买房子页面的查询
    List<Map<String, String>> buySearch(Long userId, String s);

    //点击租房按钮，触发该操作
    void rentInClick(Long houseId);

    //租出房子，展示自己类型是租出的房子
    List<Map<String, String>> rentOutHouses(Long userId);

    //买房页面，即查找其他用户已发布is_published = 1，但是is_finished = 0的
    List<Map<String, String>> buyHouses(Long userId);

    //点击买按钮，触发该方法，使得is_finished = 1
    void buyClick(Long houseId);


    //卖房页面，展示自己类型是卖出的房子
    List<Map<String, String>> saleHouses(Long userId);

    //卖房页面点击编辑，返回可选agent
    List<Agent> agentChoose();

    //卖房编辑填完表单后点击确定提交
    void edit(String houseName, Long agentId, double area, double unitPrice, String room, Long houseId);

    //卖房页面删除房子
    void deleteHouse(Long houseId);

    //新增卖房，点击确定新增
    void addSaleHouse(Long ownerId, String houseName, Long agentId, double area, double unitPrice, String room);

    //租房页面，点击编辑，返回可选agent
    void editRent(String houseName, Long agentId, double area, double rentMoney, String room, Long houseId);

    //租房页面删除房子
    void deleteRentHouse(Long houseId);

    //新增卖房，点击确定新增
    void addRentHouse(Long ownerId, String houseName, Long agentId, double area, double rentMoney, String room);
}
