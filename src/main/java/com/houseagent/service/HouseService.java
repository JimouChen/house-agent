package com.houseagent.service;
import com.houseagent.entity.House;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HouseService {
    //表单形式添加house
    void add(House house, Long ownerId, Long agentId);

    List<House> findAll();

    List<House> findByUserId(Long userId);
}
