
package com.houseagent.controller;

import com.houseagent.entity.Agent;
import com.houseagent.entity.Rent;
import com.houseagent.entity.Sale;
import com.houseagent.entity.User;
import com.houseagent.service.AgentService;
import com.houseagent.service.RentService;
import com.houseagent.service.SaleService;
import com.houseagent.service.UserService;
import com.houseagent.utils.AjaxResult;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import sun.management.resources.agent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@ResponseBody
@RequestMapping("/agent")
@Api(tags = {"中介控制器"})
public class AgentController {
    @Autowired
    private AgentService agentService;
    @Autowired
    private RentService rentService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private UserService userService;

    @ApiOperation("仅方便swagger文档agent实体类")
    @PostMapping("/agent1")
    public Agent agent1() {
        return agent1();
    }

    @PostMapping("/login")

    @ApiImplicitParam(name = "登录请求", value = "进行中介登录")
    @ApiOperation(value = "中介登录", notes = "中介通过用户名称、密码进行登录", httpMethod = "POST")
    public AjaxResult login(@RequestParam("username") @ApiParam(value = "用户名称", required = true) String username,
                            @RequestParam("password") @ApiParam(value = "用户密码", required = true) String password) {
        System.out.println("login.....");
        Subject subject = SecurityUtils.getSubject();
        //把客户端表单拿到的用户名密码封装到token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //交给shiro去处理，自动进入UserRealm里面的认证方法，先认证，后授权
        try {
            subject.login(token);//成功后下一步进入管理页面
            Agent agent = (Agent) subject.getPrincipal();
            //拿到当前用户id或者其他信息返回给前端
//            subject.getSession().setAttribute("userId", user.getUserId());
            Map<String, Object> data = new HashMap<>();
            data.put("token", "agent-token");
            data.put("userId", agent.getAgentId());
            //拿到当前用户id或者其他信息返回给前端
//            subject.getSession().setAttribute("userId", user.getUserId());
            return new AjaxResult(200, data);
        } catch (UnknownAccountException e) {//失败就进入登录页面
            return new AjaxResult(500, "用户名不存在");
        } catch (IncorrectCredentialsException e) {
            return new AjaxResult(500, "密码错误");
        }

    }

    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @PostMapping("/logout")//登出
    public AjaxResult logout() {
        //拿到当前session
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        //然后跳回到login页面

        return new AjaxResult(200, "登出成功");
    }

    //中介查看自己经手的房屋

    @ApiOperation(value = "查看个人经手的房屋", notes = "根据中介的id 查看个人经手的客房", httpMethod = "GET")
    @GetMapping("/findAll/{id}")
    public AjaxResult findByAgentId(@PathVariable("id") @ApiParam(value = "中介id", required = true) Long id) {
        List<Map<String, String>> data = agentService.findAllByAgentId(id);
        return new AjaxResult(200, data);
    }

    //中介查看自己已经完成的售房订单
    //前端：1 查看买卖房记录
    @GetMapping("/findSaleFinish/{id}")
    @ApiOperation(value = "查看买房记录", notes = "根据中介的id查看买房记录", httpMethod = "GET")
    public AjaxResult findSaleByAgentId(@PathVariable("id") @ApiParam(value = "中介ID", required = true) Long id) {
        List<Map<String, String>> items = agentService.allSaleFinished(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    //中介查看自己已经完成的租房订单
    //前端：2 查看租房记录
    @ApiOperation(value = "查看租房记录", notes = "根据中介的id查看租房记录", httpMethod = "GET")
    @GetMapping("/findRentFinish/{id}")
    public AjaxResult findRentByAgentId(@PathVariable("id") @ApiParam(value = "中介ID", required = true) Long id) {
        List<Map<String, String>> items = agentService.allRentFinished(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    //中介查看用户待发布新房源
    @ApiOperation(value = "中介查看用户待发布新房源", notes = "根据中介id查看用户待发布新房源", httpMethod = "GET")
    @GetMapping("/findWaitPublish/{id}")
    public AjaxResult findWaitPublish(@PathVariable("id") @ApiParam(value = "中介ID", required = true) Long id) {
        List<Map<String, String>> items = agentService.waitPubish(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    //中介查看用户待发布新房源
    //前端：3 租房信息发布
    @GetMapping("/findRentWaitPublish/{id}")
    @ApiOperation(value = "查看租房信息发布", httpMethod = "GET", notes = "根据中介id查找租房信息发布情况")

    public AjaxResult findRentWaitPublish(@PathVariable("id") @ApiParam(value = "中介id", required = true) Long id) {
        List<Map<String, String>> items = agentService.rentWaitPubish(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
        //拿到当前用户id或者其他信息返回给前端
//            subject.getSession().setAttribute("userId", user.getUserId());


    }

    //中介查看用户待发布新房源
    //前端：4 买卖房信息发布
    @GetMapping("/findSaleWaitPublish/{id}")
    @ApiOperation(value = "买卖房信息发布", httpMethod = "GET", notes = "根据中介id查找租房记录")

    public AjaxResult findSaleWaitPublish(@PathVariable("id") @ApiParam(value = "中介id", required = true) Long id) {
        List<Map<String, String>> items = agentService.saleWaitPubish(id);
        Map<String, Object> data = new Hashtable<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    //中介确认用户待发布新房源
    //前端：5 发布房源
    @GetMapping("/setPublish/{id}")
    @ApiOperation(value = "发布", httpMethod = "GET", notes = "根据房屋id修改房源发布状况，表示已发布状况")
    public AjaxResult setPublish(@PathVariable("id") @ApiParam(value = "房屋id", required = true) Long id) {
        agentService.setPubish(id);
        List<Map<String, String>> sales = agentService.saleWaitPubish(id);
        List<Map<String, String>> rents = agentService.saleWaitPubish(id);
        Map<String, Object> data = new HashMap<>();
        data.put("sales", sales);
        data.put("rents", rents);
        data.put("saletotal", rents.size());
        data.put("trentotal", sales.size());
        //如果需要重新查询，则将data返回给前端
        return new AjaxResult(200, "操作成功，" + id + "号房源已发布");
    }


    //前端：6 租赁合同管理123456
    @GetMapping("/findRentWaitFinish/{id}")
    @ApiOperation(value = "租赁合同管理", httpMethod = "GET")
    public AjaxResult findRentWaitFinish(@PathVariable("id") @ApiParam(value = "中介id", required = true) Long agentId) {
        List<Map<String, String>> items = agentService.rentWaitFinish(agentId);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @GetMapping("/rentSearch")
    @ApiOperation(value = "租赁合同管理模糊查询", httpMethod = "GET")
    public AjaxResult findRentWaitFinish(@RequestParam("id") @ApiParam(value = "中介id", required = true) Long id,
                                         @RequestParam("houseName") @ApiParam(value = "房屋名称", required = true) String houseName) {
        List<Map<String, String>> items = agentService.rentSearch(id, "%" + houseName + "%");
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @GetMapping("/buySearch")
    @ApiOperation(value = "买卖合同管理模糊查询", httpMethod = "GET")
    public AjaxResult buySearch(@RequestParam("id") @ApiParam(value = "中介id", required = true) Long id,
                                @RequestParam("houseName") @ApiParam(value = "房屋名称", required = true) String houseName) {
        List<Map<String, String>> items = agentService.buySearch(id, "%" + houseName + "%");
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }


    //前端：7 买卖合同管理
    @ApiOperation(value = "买卖合同管理", httpMethod = "GET")
    @GetMapping("/findSaleWaitFinish/{id}")
    public AjaxResult findSaleWaitFinish(@PathVariable("id") @ApiParam(value = "中介id", required = true) Long agentId) {
        List<Map<String, String>> items = agentService.saleWaitFinish(agentId);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    //6.1 中介确认用户租房交易的房源
    @PostMapping("/setRentFinish")
    @ApiOperation(value = "中介确认用户租房交易的房源", httpMethod = "POST")
    public AjaxResult setRentFinish(@RequestParam("rent_id") @ApiParam(value = "租房记录id", required = true) Long rentid,
                                    @RequestParam("house_name") String house_name,
                                    @RequestParam("rent_length") int length,
                                    @RequestParam("agent_id") Long agentid,
                                    @RequestParam("area") double area,
                                    @RequestParam("rent_money") double rent_money,
                                    @RequestParam("room") String room,
                                    @RequestParam("house_id") Long house_id) {
        userService.editRent(house_name, agentid, area, rent_money, room, house_id);
        rentService.editLength(rentid,length);
        agentService.setFinish(house_id, 2);
        rentService.setIsValid(rentid, 2);
        return new AjaxResult(200, "操作成功，" + house_id + "号房源已完成租房交易");
    }

    //6.2 中介取消用户租房交易的房源
    @GetMapping("/deleteRentFinish/{house_id}/{rent_id}")
    @ApiOperation(value = "中介取消用户租房交易的房源", httpMethod = "POST")
    public AjaxResult deleteRentFinish(@PathVariable("house_id")
                                       @ApiParam(value = "房屋id", required = true) Long houseid,
                                       @PathVariable("rent_id")
                                       @ApiParam(value = "租房记录id", required = true) Long rentid) {
        agentService.setFinish(houseid, 0);
        rentService.setIsValid(rentid, 0);
        return new AjaxResult(200, "操作成功，" + houseid + "号房源已取消租房交易");
    }

    //7.1中介确认用户卖房交易的房源
    @PostMapping("/setSaleFinish")
    @ApiOperation(value = "中介确认用户卖房交易的房源", httpMethod = "POST")
    public AjaxResult setSaleFinish(@RequestParam("sale_id") Long saleid,
                                    @RequestParam("house_name") String house_name,
                                    @RequestParam("agent_id") Long agentid,
                                    @RequestParam("area") double area,
                                    @RequestParam("unit_price") double unit_price,
                                    @RequestParam("room") String room,
                                    @RequestParam("house_id") Long house_id) {
        userService.edit(house_name, agentid, area, unit_price, room, house_id);
        agentService.setFinish(house_id, 2);
        saleService.setIsValid(saleid, 2);
        return new AjaxResult(200, "操作成功，" + house_id + "号房源已完成卖房交易");
    }

    //7.2 中介取消用户租房交易的房源
    @GetMapping("/deleteSaleFinish/{house_id}/{sale_id}")
    @ApiOperation(value = "中介取消用户卖房交易的房源", httpMethod = "POST")
    public AjaxResult deleteSaleFinish(@PathVariable("house_id")
                                       @ApiParam(value = "房屋id", required = true) Long houseid,
                                       @ApiParam(value = "买卖房id", required = true)
                                       @PathVariable("sale_id") Long saleid) {
        agentService.setFinish(houseid, 0);
        saleService.setIsValid(saleid, 0);
        return new AjaxResult(200, "操作成功，" + houseid + "号房源已取消租房交易");
    }

    @ApiOperation(value = "根据中介个人信息")
    @GetMapping("/find/{id}")
    public AjaxResult myFindByUserId(@PathVariable("id") @ApiParam(value = "中介ID", required = true) Long id) {
        try {
            Agent agent = agentService.findByAgentId(id);
            Map<String, Object> data = new HashMap<>();
            data.put("agentId", agent.getAgentId());
            data.put("agentName", agent.getAgentName());
            data.put("password", agent.getPassword());
            data.put("phoneNumber", agent.getPhoneNumber());
            return new AjaxResult(200, data);
        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }


    @PutMapping("/updateAgent")
    @ApiOperation(value = "进行中介更新", httpMethod = "PUT")
    public AjaxResult updateAgent(Agent agent) {
        try {
            agentService.update(agent);
            return new AjaxResult(200, agent);

        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }

    @ApiOperation(value = "中介信息", httpMethod = "GET")
    @GetMapping("/info")
    public AjaxResult agentInfo() {
        Map<String, Object> data = new HashMap();
        ArrayList<String> roles = new ArrayList<>();
        roles.add("agent");
        String introduction = "I am a agent";
        String name = "Agent";
        String avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
        data.put("roles", roles);
        data.put("introduction", introduction);
        data.put("name", name);
        data.put("avatar", avatar);
        return new AjaxResult(200, data);
    }


}
