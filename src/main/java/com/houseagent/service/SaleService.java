package com.houseagent.service;
import com.houseagent.entity.Sale;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface SaleService {
    void add(Long houseId, Long buyerId, Sale sale);
    void setIsValid(Long saleId, int status);

    List<Map<String, String>> findAllSale();
}
