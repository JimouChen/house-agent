package com.houseagent.service.impl;

import com.houseagent.dao.AgentDao;
import com.houseagent.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AgentDao agentDao;
}
