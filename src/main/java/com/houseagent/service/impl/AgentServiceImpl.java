package com.houseagent.service.impl;

import com.houseagent.dao.AgentDao;
import com.houseagent.entity.Agent;
import com.houseagent.entity.User;
import com.houseagent.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentDao agentDao;

    @Override
    public List<Agent> findall() {
        return agentDao.findAll();
    }

    @Override
    public List<Map<String, String>> findAllByAgentId(Long id) {
        return agentDao.findHouseByAgentId(id);
    }

    @Override
    public List<Map<String, String>> allSaleFinished(Long agentId) {
        return agentDao.allSaleFinished(agentId);
    }

    @Override
    public List<Map<String, String>> allRentFinished(Long agentId) {
        return agentDao.allRentFinished(agentId);
    }

    @Override
    public List<Map<String, String>> waitPubish(Long agentId) {
        return agentDao.waitPubish(agentId);
    }

    @Override
    public List<Map<String, String>> rentWaitPubish(Long agentId) {
        return agentDao.rentWaitPubish(agentId);
    }

    @Override
    public List<Map<String, String>> saleWaitPubish(Long agentId) {
        return agentDao.saleWaitPubish(agentId);
    }

    @Override
    public List<Map<String, String>> rentWaitFinish(Long agentId) {
        return agentDao.rentWaitFinish(agentId);
    }

    @Override
    public List<Map<String, String>> saleWaitFinish(Long agentId) {
        return agentDao.saleWaitFinish(agentId);
    }

    @Override
    public void setPubish(Long houseId) {
        agentDao.setPubish(houseId);
    }

    @Override
    public void setFinish(Long houseId, int status) {
        agentDao.setFinish(houseId,status);
    }

    @Override
    public Agent findByName(String name) {
        return agentDao.findByAgentNameIs(name);
    }

    @Override
    public void update(Agent agent) {
        agentDao.save(agent);
    }

    @Override
    public Agent findByAgentId(Long id) {
        return agentDao.findByAgentId(id);
    }

    @Override
    public List<Map<String, String>> rentSearch(Long agentId, String s) {
        return agentDao.rentSearch(agentId, s);
    }

    @Override
    public List<Map<String, String>> buySearch(Long agentId, String s) {
        return agentDao.buySearch(agentId, s);
    }

    @Override
    public Page<Agent> findByPage(Integer page, Integer size, String sortWay) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "agentId"));
        if (sortWay.contains("-"))
            pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "agentId"));
        return agentDao.findAll(pageable);
    }
}
