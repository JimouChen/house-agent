package com.houseagent.service.impl;

import com.houseagent.dao.AgentDao;
import com.houseagent.dao.HouseDao;
import com.houseagent.dao.UserDao;
import com.houseagent.entity.Agent;
import com.houseagent.entity.House;
import com.houseagent.entity.User;
import com.houseagent.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseDao houseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AgentDao agentDao;

    @Override
    public void add(House house, Long ownerId, Long agentId) {
        //需要先拿到前端的ownerId 和 agentId，确保级联保存成功
        //通过id找到user和agent
        User user = userDao.findById(ownerId).get();
        Agent agent = agentDao.findById(agentId).get();
        house.setUser(user);
        house.setAgent(agent);
        System.out.println(house);
        houseDao.save(house);
    }

    @Override
    public List<House> findAll(){
        return houseDao.findAll();
    }

    @Override
    public List<House> findByUserId(Long userId){
        return houseDao.findHouseByUserId(userId);
    }
}
