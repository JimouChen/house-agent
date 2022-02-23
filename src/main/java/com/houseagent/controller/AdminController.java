package com.houseagent.controller;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.houseagent.entity.*;
import com.houseagent.service.*;
import com.houseagent.utils.AjaxResult;
import io.swagger.annotations.*;
import lombok.Getter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
@Api(tags = {"管理员控制器"})
public class AdminController {
    @Autowired
    private AgentService agentService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RentService rentService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private UserService userService;
    @ApiOperation("仅方便swagger文档admin实体类")
    @PostMapping("/admin1")
     public Admin admin11(){
         return admin11();
     }


    @PostMapping("/login")
    @ApiOperation(
            value = "登录"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",required = true
            ),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",required = true
            )
    })
    public AjaxResult login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (username.equals("admin")){
            Map<String, Object> data = new HashMap<>();
            data.put("token", "admin-token");
            return new AjaxResult(200, data);
        }
        else
            return new AjaxResult(500, "用户名不正确");
    }
    @ApiOperation(
            value = "登出"
    )
    @PostMapping("/logout")//登出
    public AjaxResult logout() {
        return new AjaxResult(200, "登出成功");
    }


    //1 客户管理
    @ApiOperation(value = "进行客户管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",dataType = "Integer",required = true
            )
    })
    @GetMapping("/findAllUser/{page}")
    public AjaxResult findAllUser(@PathVariable("page") Integer page) {
        //从第page-1页开始，每页查size条
        Page<User> p = userService.findByPage(page, 10, "sort");
        Map<String, Object> data = new HashMap<>();
        data.put("total", p.getTotalElements());
        data.put("items", p.getContent());
        return new AjaxResult(200, data);
    }

    @GetMapping("/findTheUser")
    public AjaxResult findTheUser(@RequestParam("username")String username) {
        Page<User> p = userService.findByPage(1, 10, "sort");
        Map<String, Object> data = new HashMap<>();
        List<User>item = new ArrayList<>();
        for (User u: p){
            if (u.getUsername().contains(username)){
                item.add(u);
            }
        }
        data.put("total", item.size());
        data.put("items", item);

        return new AjaxResult(200, data);
    }

    //1.1 客户更新
    @PutMapping("/updateUser")
    @ApiOperation(value = "进行客户更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",dataType = "Long",required = true),
            @ApiImplicitParam(name = "username",value = "用户名字",dataType = "String",required = true),
            @ApiImplicitParam(name = "sex",value = "性别",dataType = "String",required = true),
            @ApiImplicitParam(name = "createTime",value = "更新时间",dataType = "String",required = true),
            @ApiImplicitParam(name = "phoneNumber",value = "手机号码",dataType = "String",required = true),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",required = true)
    })
    public AjaxResult updateUser(@RequestParam("userId") Long userid, @RequestParam("username") String username, @RequestParam("sex") String sex, @RequestParam("createTime") String date, @RequestParam("phoneNumber") String phone, @RequestParam("password") String password) {
        try {
            User user = userService.findById(userid);
            user.setUsername(username);
            user.setSex(sex);
            user.setPhoneNumber(phone);
            user.setPassword(password);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime parsedDate = LocalDateTime.parse(date, formatter);
            user.setCreateTime(parsedDate);
            userService.update(user);
            return new AjaxResult(200, user);

        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }

    //2 中介管理
    @ApiOperation(value = "进行中介管理")
    @ApiImplicitParam(name = "page",value = "页",dataType = "Integer",required = true)

    @GetMapping("/findAllAgent/{page}")
    public AjaxResult findAllAgent(@PathVariable("page") Integer page) {
//        List<Agent> data = agentService.findall();
        Page<Agent> p = agentService.findByPage(page, 10, "sort");
        Map<String, Object> data = new HashMap<>();
        data.put("total", p.getTotalElements());
        data.put("items", p.getContent());
        return new AjaxResult(200, data);
    }

    @GetMapping("/findTheAgent")
    public AjaxResult findTheAgent(@RequestParam("agentname")String agentname) {
        Page<Agent> p = agentService.findByPage(1, 10, "sort");
        Map<String, Object> data = new HashMap<>();
        List<Agent>item = new ArrayList<>();
        for (Agent u: p){
            if (u.getAgentName().contains(agentname)){
                item.add(u);
            }
        }
        data.put("total", item.size());
        data.put("items", item);

        return new AjaxResult(200, data);
    }

    //2.1 中介更新
    @PutMapping("/updateAgent")
    @ApiOperation(value = "进行中介更新",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentId",value = "中介Id",dataType = "String",required = true),
            @ApiImplicitParam(name = "agentName",value = "中介姓名",dataType = "String",required = true),
            @ApiImplicitParam(name = "sex",value = "中介性别",dataType = "String",required = true),
            @ApiImplicitParam(name = "createTime",value = "更新时间",dataType = "String",required = true),
            @ApiImplicitParam(name = "phoneNumber",value = "手机号码",dataType = "String",required = true),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",required = true)

    })
    public AjaxResult updateAgent(@RequestParam("agentId") Long agentid, @RequestParam("agentName") String agentname, @RequestParam("sex") String sex, @RequestParam("createTime") String hiredate, @RequestParam("phoneNumber") String phone, @RequestParam("password") String password) {
       try {
            Agent agent = agentService.findByAgentId(agentid);
            agent.setAgentName(agentname);
            agent.setSex(sex);
            agent.setPhoneNumber(phone);
            agent.setPassword(password);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime parsedDate = LocalDateTime.parse(hiredate, formatter);
            agent.setCreateTime(parsedDate);
            System.out.println(agent.getCreateTime());
            agentService.update(agent);
            return new AjaxResult(200, agent);

        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }

    //2.2 根据姓名查找中介123456
    @ApiOperation(value = "根据姓名查找中介",httpMethod = "GET")
    @ApiImplicitParam(name = "agentname",value = "中介名字",dataType = "String",required = true)
    @GetMapping("/findAgentByName")
    public AjaxResult findAgentByName(@PathVariable("agentname") String agentname) {
        try {
            Agent agent = agentService.findByName(agentname);
            Map<String, Object> data = new HashMap<>();
            data.put("total", 1);
            data.put("items", agent);
            return new AjaxResult(200, data);

        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }

    //2.3添加中介员工
    @PutMapping("/addAgent")
    @ApiOperation(value = "添加中介",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentName",value = "中介Id",dataType = "String",required = true),
            @ApiImplicitParam(name = "sex",value = "中介性别",dataType = "String",required = true),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",required = true),
            @ApiImplicitParam(name = "phoneNumber",value = "手机号码",dataType = "String",required = true)


    })
    public AjaxResult addAgent(@RequestParam("agentName") String agentname, @RequestParam("sex") String sex, @RequestParam("password") String password, @RequestParam("phoneNumber") String phone) {
      try {
            Agent agent = new Agent();
            agent.setAgentName(agentname);
            agent.setSex(sex);
            agent.setPhoneNumber(phone);
            agent.setPassword(password);
            System.out.println(agent.getCreateTime());
            agentService.update(agent);
            return new AjaxResult(200, agent);

        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }

    //3.1 查询统计：查看买房交易记录
    @ApiOperation(
            value = "查看买房交易记录"
    )
    @GetMapping("/findAllSale")

    public AjaxResult findAllSale() {
        List<Map<String, String>> items = saleService.findAllSale();
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    //3.2 查询统计：查看租房记录
    @GetMapping("/findAllRent")
    @ApiOperation(
            value = "查看租房交易记录"
    )
    public AjaxResult findAllRent() {
        List<Map<String, String>> items = rentService.findAllRent();
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

}
