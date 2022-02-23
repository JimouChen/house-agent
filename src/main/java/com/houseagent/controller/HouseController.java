package com.houseagent.controller;

import com.houseagent.entity.House;
import com.houseagent.service.HouseService;
import com.houseagent.utils.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/house")
@Api(tags = {"房源控制器"})
public class HouseController {
    @Autowired
    private HouseService houseService;
    @ApiOperation(value = "新增房源并保存")
    @PostMapping("/addForm")
//    @Transactional
    public AjaxResult saveHouse(House house, @RequestParam("ownerId")@ApiParam(value = "房主ID",required = true) Long ownerId,@ApiParam(value = "中介ID",required = true) @RequestParam("agentId") Long agentId) {
        try {
            houseService.add(house, ownerId, agentId);
            return new AjaxResult(200, house);
        } catch (Exception e) {
            return new AjaxResult(400 , e);
        }
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查找所有的房源")
    public List<House> findAll(){
        return houseService.findAll();
    }

    @GetMapping("/findAll/{userId}")
    @ApiOperation(value = "查找当前用户的房子")

    public List<House> findHousesByUserId(@PathVariable("userId") @ApiParam(value = "用户ID",required = true) Long userId){
        return houseService.findByUserId(userId);
    }

    @GetMapping("/manage")
    @ApiOperation(value = "返回house manage的信息")

    public String HouseManage(){
        return "house manage";
    }
}
