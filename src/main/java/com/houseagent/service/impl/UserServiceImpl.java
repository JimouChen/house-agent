package com.houseagent.service.impl;

import com.houseagent.dao.*;
import com.houseagent.entity.*;
import com.houseagent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private HouseDao houseDao;
    @Autowired
    private SaleDao saleDao;
    @Autowired
    private RentDao rentDao;
    @Autowired
    private AgentDao agentDao;

    @Override
    public void add(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public User findByName(String name) {
        return userDao.findByUsernameIs(name);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAll();
    }

    @Override
    public Page<User> findByPage(Integer page, Integer size, String sortWay) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "userId"));
        if (sortWay.contains("-"))
            pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "userId"));
        return userDao.findAll(pageable);
    }

    @Override
    public List<Map<String, String>> rentInSearch(Long userId, String s) {
        return rentDao.rentInSearch(userId, s);
    }

    @Override
    public List<Map<String, String>> buySearch(Long userId, String s) {
        return saleDao.buySearch(userId, s);
    }

    //    @Override
//    public List<Rent> rentPublished(Long id) {
//        return userDao.rentPublished(id);
//    }

    @Override
    public List<Map<String, String>> allSale(Long userId) {
        return houseDao.allSaleFinished(userId);
    }

    @Override
    public List<Map<String, String>> allRent(Long userId) {
        return houseDao.allRentFinished(userId);
    }

    @Override
    public List<Map<String, String>> buyRecord(Long userId) {
        return saleDao.buyRecord(userId);
    }

    @Override
    public List<Map<String, String>> rentInRecord(Long userId) {
        return rentDao.rentInRecord(userId);
    }

    @Override
    public List<Map<String, String>> saleRecord(Long userId) {
        return saleDao.saleRecord(userId);
    }

    @Override
    public List<Map<String, String>> rentOutRecord(Long userId) {
        return rentDao.rentOutRecord(userId);
    }

    @Override
    public List<Map<String, String>> buyHouses(Long userId) {
        return saleDao.buyHouse(userId);
    }

    @Override
    public void buyClick(Long houseId) {
        saleDao.buyClick(houseId);
    }

    @Override
    public List<Map<String, String>> saleHouses(Long userId) {
        return saleDao.saleHouses(userId);
    }

    @Override
    public List<Map<String, String>> rentInHouse(Long userId) {
        return rentDao.rentInHouse(userId);
    }

    @Override
    public void rentInClick(Long houseId) {
        rentDao.rentInClick(houseId);
    }

    @Override
    public List<Map<String, String>> rentOutHouses(Long userId) {
        return rentDao.rentOutHouses(userId);
    }

    @Override
    public void edit(String houseName, Long agentId, double area, double unitPrice, String room, Long houseId) {
        houseDao.edit(houseName, agentId, area, unitPrice, room, houseId);
    }

    @Override
    public void deleteHouse(Long houseId) {
        houseDao.deleteHouse(houseId);
    }

    @Override
    public List<Agent> agentChoose() {
        return agentDao.findAll();
    }

    @Override
    public void editRent(String houseName, Long agentId, double area, double rentMoney, String room, Long houseId) {
        houseDao.editRent(houseName, agentId, area, rentMoney, room, houseId);
    }

    @Override
    public void deleteRentHouse(Long houseId) {
        houseDao.deleteRentHouse(houseId);
    }

    @Override
    public void addSaleHouse(Long ownerId, String houseName, Long agentId, double area, double unitPrice, String room) {
        houseDao.addSaleHouse(ownerId, houseName, agentId, area, unitPrice, room);
    }

    @Override
    public void addRentHouse(Long ownerId, String houseName, Long agentId, double area, double rentMoney, String room) {
        houseDao.addRentHouse(ownerId, houseName, agentId, area, rentMoney, room);
    }
}
