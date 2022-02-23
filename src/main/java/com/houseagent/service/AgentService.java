package com.houseagent.service;
import com.houseagent.entity.Agent;
import com.houseagent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface AgentService {

    List<Agent> findall();
    List<Map<String,String>> findAllByAgentId(Long id);
    List<Map<String, String>> allSaleFinished(Long agentId);
    List<Map<String, String>> allRentFinished(Long agentId);
    List<Map<String,String>> waitPubish(Long agentId);
    List<Map<String,String>> rentWaitPubish(Long agentId);
    List<Map<String,String>> saleWaitPubish(Long agentId);
    List<Map<String,String>> rentWaitFinish(Long agentId);
    List<Map<String,String>> rentSearch(Long agentId, String s);
    List<Map<String,String>> buySearch(Long agentId, String s);
    List<Map<String,String>> saleWaitFinish(Long agentId);
    void setPubish(Long houseId);
    void setFinish(Long houseId, int ststus);
    Agent findByName(String agentname);

    void update(Agent agent);

    Agent findByAgentId(Long id);

    Page<Agent> findByPage(Integer page, Integer size, String sortWay);

}
