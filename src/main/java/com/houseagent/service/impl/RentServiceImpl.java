package com.houseagent.service.impl;

import com.houseagent.dao.HouseDao;
import com.houseagent.dao.RentDao;
import com.houseagent.dao.UserDao;
import com.houseagent.entity.House;
import com.houseagent.entity.Rent;
import com.houseagent.entity.User;
import com.houseagent.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RentServiceImpl implements RentService {
    @Autowired
    private RentDao rentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HouseDao houseDao;

    @Override
    public void add(Long houseId, Long renterId, Rent rent) {
        User user = userDao.findById(renterId).get();
        House house = houseDao.findById(houseId).get();
        rent.setUser(user);
        rent.setHouse(house);
        rentDao.save(rent);
    }

    @Override
    public void delete(Long renterId) {
        rentDao.deleteById(renterId);
    }

    @Override
    public void setIsValid(Long rentId, int status) {
        rentDao.setIsValid(rentId,status);
    }

    @Override
    public void editLength(Long rentId, int length) {
        rentDao.editLength(rentId,length);
    }

    @Override
    public List<Map<String, String>> findAllRent() {
        return rentDao.findAllRent();
    }

    @Override
    public Rent findById(Long id) {
        return rentDao.findById(id).get();
    }

    @Override
    public Page<Rent> findRentByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return rentDao.findAll(pageable);
    }
}
