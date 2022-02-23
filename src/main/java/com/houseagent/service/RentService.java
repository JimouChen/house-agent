package com.houseagent.service;
import com.houseagent.entity.Rent;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface RentService {
    //自定义保存

    void add(Long houseId, Long renterId, Rent rent);
    void delete(Long renterId);
    void setIsValid(Long rentId, int status);
    void editLength(Long rentId, int length);
    List<Map<String, String>> findAllRent();
    Rent findById(Long rentid);
    Page<Rent> findRentByPage(Integer page, Integer size);


    //交易完成自动插入t_rent_record
//    void addRent(Long userId);
}
