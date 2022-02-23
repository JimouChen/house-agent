package com.houseagent.controller;

import com.houseagent.dao.HouseDao;
import com.houseagent.dao.RentDao;
import com.houseagent.dao.UserDao;
import com.houseagent.entity.Rent;
import com.houseagent.entity.Sale;
import com.houseagent.entity.User;
import com.houseagent.service.RentService;
import com.houseagent.utils.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
@Api(tags = {"租房控制器"})
@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private RentService rentService;
    @ApiOperation("仅方便swagger文档显示sale实体类")
    @PostMapping("/sale1")
    public Sale sale1(){
        return sale1();
    }
    @ApiOperation("仅方便swagger文档显示rent租房实体类")
    @PostMapping("/rent1")
    public Rent rent1(){
        return rent1();
    }
}
