package com.houseagent.service.impl;

import com.houseagent.dao.HouseDao;
import com.houseagent.dao.SaleDao;
import com.houseagent.dao.UserDao;
import com.houseagent.entity.House;
import com.houseagent.entity.Sale;
import com.houseagent.entity.User;
import com.houseagent.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleDao saleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HouseDao houseDao;

    @Override
    public void add(Long houseId, Long buyerId, Sale sale) {
        User user = userDao.findById(buyerId).get();
        House house = houseDao.findById(houseId).get();
        sale.setUser(user);
        sale.setHouse(house);
        saleDao.save(sale);
    }

    @Override
    public void setIsValid(Long saleId, int status) {
        saleDao.setIsValid(saleId,status);
    }

    @Override
    public List<Map<String, String>> findAllSale() {
        return saleDao.findAllSale();
    }
}
