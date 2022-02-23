package com.houseagent.controller;


import com.houseagent.dao.SaleDao;
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
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@Api(tags = {"用户控制器"})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RentService rentService;
    @Autowired
    private SaleService saleService;

    @PostMapping("/add")
    @ApiOperation(value = "新添用户")
    private AjaxResult saveUser(User user) {
        try {
            userService.add(user);
            return new AjaxResult(200, user);
        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }

    @ApiOperation(value = "查找用户")
    @GetMapping("/findAllUser")
    List<User> findAll() {
        return userService.findAllUser();
    }

    @ApiOperation(value = "根据用户个人信息")
    @GetMapping("/find/{id}")
    public AjaxResult myFindByUserId(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        try {
            User user = userService.findById(id);
            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getUserId());
            data.put("username", user.getUsername());
            data.put("password", user.getPassword());
            data.put("phoneNumber", user.getPhoneNumber());
            return new AjaxResult(200, data);
        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }

    //因为是get请求,分页查询让前端传参数
//    @GetMapping("/findAll/{page}/{limit}")
//    public AjaxResult findAll(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
//        //从第page-1页开始，每页查size条
//        Page<User> p = userService.findByPage(page, limit);
//        Map<String, Object> data = new HashMap<>();
//        data.put("total", p.getTotalElements());
//        data.put("items", p.getContent());
//        return new AjaxResult(200, data);
//    }

    @GetMapping("/findAll")
    @ApiOperation("分页查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "limit", value = "数据条数", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "sort", value = "分类方式", dataType = "String", required = true)

    })
    public AjaxResult findAll(@RequestParam("page") Integer page,
                              @RequestParam("limit") Integer limit,
                              @RequestParam("sort") String sort) {
        //从第page-1页开始，每页查size条
        Page<User> p = userService.findByPage(page, limit, sort);
        Map<String, Object> data = new HashMap<>();
        data.put("total", p.getTotalElements());
        data.put("items", p.getContent());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping("/update")//表单一定要写上userId
    public AjaxResult update(User user) {
        try {
            userService.update(user);
            return new AjaxResult(200, user);

        } catch (Exception e) {
            return new AjaxResult(400, e);
        }
    }

    @ApiOperation(value = "登录", notes = "根据用户名、密码进行登录")
    @PostMapping("/login")
    public AjaxResult login(@RequestParam("username") @ApiParam(value = "用户名", required = true) String username,
                            @RequestParam("password") @ApiParam(value = "密码", required = true) String password) {
        Subject subject = SecurityUtils.getSubject();
        //把客户端表单拿到的用户名密码封装到token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //交给shiro去处理，自动进入UserRealm里面的认证方法，先认证，后授权
        try {
            subject.login(token);//成功后下一步进入管理页面
            User user = (User) subject.getPrincipal();
            //拿到当前用户id或者其他信息返回给前端
//            subject.getSession().setAttribute("userId", user.getUserId());
            Map<String, Object> data = new HashMap<>();
            data.put("token", "user-token");
            data.put("userId", user.getUserId());
            return new AjaxResult(200, data);
        } catch (UnknownAccountException e) {//失败就进入登录页面
            return new AjaxResult(500, "用户名不存在");
        } catch (IncorrectCredentialsException e) {
            return new AjaxResult(500, "密码错误");
        }

    }

    @ApiOperation(value = "登出")
    @PostMapping("/logout")//登出
    public AjaxResult logout() {
        //拿到当前session
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
        //然后跳回到login页面

        return new AjaxResult(200, "登出成功");
    }

    @ApiOperation(value = "完成卖房操作")
    @GetMapping("/allSale/{id}")
    public AjaxResult allSale(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.allSale(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "完成房子租出去的操作")
    @GetMapping("/allRent/{id}")
    public AjaxResult allRent(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.allRent(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "查看买房记录")
    @GetMapping("/buyRecord/{id}")
    public AjaxResult buyRecord(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.buyRecord(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "查看买房记录")
    @GetMapping("/saleRecord/{id}")
    public AjaxResult saleRecord(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.saleRecord(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "查看自己租入房子记录")
    @GetMapping("/rentInRecord/{id}")
    public AjaxResult rentRecord(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.rentInRecord(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "查看自己租出房子记录")
    @GetMapping("/rentOutRecord/{id}")
    public AjaxResult rentOutRecord(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.rentOutRecord(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "买房页面的展示", notes = "根据用户ID展示买房子页面")
    @GetMapping("/buyHouse/{id}")
    public AjaxResult buyHouse(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.buyHouses(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }


    @ApiOperation("点击买，触发操作")
    //@PostMapping("/buyClick")
    @GetMapping("/buyClick/{buyerId}/{houseId}")
    public AjaxResult handleBuy(@PathVariable("buyerId") @ApiParam(value = "购买者ID", required = true) Long buyerId,
                                @PathVariable("houseId") @ApiParam(value = "房源ID", required = true) Long houseId) {

        userService.buyClick(houseId);
        Sale sale = new Sale();
        sale.setValid("1");
        saleService.add(houseId, buyerId, sale);
        return new AjaxResult(200, "操作成功，等待中介确认买房");
    }

    @ApiOperation(value = "点击租入房子页面的租房按钮")
    //@PostMapping("/rentInClick")
    @GetMapping("/renInClick/{renterId}/{houseId}/{rentLength}")
    public AjaxResult rentInClick(@PathVariable("renterId") @ApiParam(value = "租户ID", required = true) Long renterId,
                                  @PathVariable("houseId") @ApiParam(value = "房源ID", required = true) Long houseId,
                                  @PathVariable("rentLength") @ApiParam(value = "租的时间/月", required = true) int rentLength) {
        userService.rentInClick(houseId);
        //把记录写到rent_record表中
        Rent rent = new Rent();
        rent.setValid("1");
        rent.setRentLength(rentLength);
        rentService.add(houseId, renterId, rent);
        return new AjaxResult(200, "操作成功，等待中介确认租房");
    }

    @ApiOperation(value = "卖房页面的展示")
    @GetMapping("/saleHouse/{id}")
    public AjaxResult saleHouse(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.saleHouses(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "查看可租入房子的展示页面")
    @GetMapping("/rentInHouse/{id}")
    public AjaxResult rentInHouse(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.rentInHouse(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "可租入房子页面的搜索模糊查询")
    @GetMapping("/rentInSearch")
    public AjaxResult rentInSearch(@RequestParam("id") @ApiParam(value = "用户ID", required = true) Long id,
                                   @RequestParam("houseName") @ApiParam(value = "房屋名称", required = true) String houseName

    ) {
        List<Map<String, String>> need = userService.rentInSearch(id, "%" + houseName + "%");
        Map<String, Object> data = new HashMap<>();
        data.put("items", need);
        data.put("total", need.size());
        return new AjaxResult(200, data);
    }

    @ApiOperation(value = "买房子页面的搜索模糊查询")
    @GetMapping("/buySearch")
    public AjaxResult buySearch(@RequestParam("id") @ApiParam(value = "用户ID", required = true) Long id,
                                @RequestParam("houseName") @ApiParam(value = "房屋名称", required = true) String houseName

    ) {
        List<Map<String, String>> need = userService.buySearch(id, "%" + houseName + "%");
        Map<String, Object> data = new HashMap<>();
        data.put("items", need);
        data.put("total", need.size());
        return new AjaxResult(200, data);
    }


    @ApiOperation(value = "点击租入房子页面的租房按钮")
    @GetMapping("/rentOutHouse/{id}")
    public AjaxResult rentOutHouse(@PathVariable("id") @ApiParam(value = "用户ID", required = true) Long id) {
        List<Map<String, String>> items = userService.rentOutHouses(id);
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("total", items.size());
        return new AjaxResult(200, data);
    }

    @GetMapping("/info")
    @ApiOperation(value = "用户信息")
    public AjaxResult userInfo() {
        Map<String, Object> data = new HashMap();
        ArrayList<String> roles = new ArrayList<>();
        roles.add("user");
        String introduction = "I am a user";
        String name = "User";
        String avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
        data.put("roles", roles);
        data.put("introduction", introduction);
        data.put("name", name);
        data.put("avatar", avatar);
        return new AjaxResult(200, data);
    }

    //卖房点击edit，把可选中介的信息返回给前端
    @ApiOperation(value = "卖房点击edit，把可选中介的信息返回给前端")
    @GetMapping("/agentChoose")
    public AjaxResult findAgent() {
        List<Agent> agents = userService.agentChoose();
        List<Object> data = new ArrayList<>();

        for (Agent agent : agents) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("chooseagentid", agent.getAgentId());
            temp.put("name", agent.getAgentName());
            data.add(temp);
        }
        return new AjaxResult(200, data);
    }

    //卖房编辑后点击confirm确认提交表单
    @ApiOperation(value = "卖房编辑后点击confirm确认提交表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "house_name", value = "房源名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "chooseagentid", value = "选择中介id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "area", value = "面积", dataType = "double", required = true),
            @ApiImplicitParam(name = "unit_price", value = "单元价格/平方米", dataType = "double", required = true),
            @ApiImplicitParam(name = "room", value = "厅室", dataType = "String", required = true),
            @ApiImplicitParam(name = "house_id", value = "房源ID", dataType = "Long", required = true),


    })
    @PostMapping("/confirmEditSale")
    public AjaxResult editSaleHouse(@RequestParam("house_name") String house_name,
                                    @RequestParam("chooseagentid") Long chooseagentid,
                                    @RequestParam("area") double area,
                                    @RequestParam("unit_price") double unit_price,
                                    @RequestParam("room") String room,
                                    @RequestParam("house_id") Long house_id) {
        userService.edit(house_name, chooseagentid, area, unit_price, room, house_id);
        return new AjaxResult(200, "修改卖房信息成功");
    }

    //租房编辑后点击confirm确认提交表单
    @ApiOperation(value = "租房编辑后点击confirm确认提交表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "house_name", value = "房源名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "chooseagentid", value = "选择中介id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "area", value = "面积", dataType = "double", required = true),
            @ApiImplicitParam(name = "rent_money", value = "租房价格", dataType = "double", required = true),
            @ApiImplicitParam(name = "room", value = "厅室", dataType = "String", required = true),
            @ApiImplicitParam(name = "house_id", value = "房源ID", dataType = "Long", required = true),


    })
    @PostMapping("/confirmEditRent")
    public AjaxResult editRentHouse(@RequestParam("house_name") String house_name,
                                    @RequestParam("chooseagentid") Long chooseagentid,
                                    @RequestParam("area") double area,
                                    @RequestParam("rent_money") double rent_money,
                                    @RequestParam("room") String room,
                                    @RequestParam("house_id") Long house_id) {
        userService.editRent(house_name, chooseagentid, area, rent_money, room, house_id);
        return new AjaxResult(200, "修改租房信息成功");
    }

    //卖房页面点击删除房子
    @ApiOperation(value = "卖房页面点击删除房子")
    @ApiImplicitParam(name = "houseId", value = "房源ID", dataType = "Long", required = true)
    @GetMapping("/deleteSaleHouse/{houseId}")
    public AjaxResult deleteSaleHouse(@PathVariable("houseId") Long houseId) {
        userService.deleteHouse(houseId);
        return new AjaxResult(200, "删除成功");
    }

    @ApiOperation(value = "租房页面点击删除房子")
    @ApiImplicitParam(name = "houseId", value = "房源ID", dataType = "Long", required = true)
    @GetMapping("/deleteRentHouse/{houseId}")
    public AjaxResult deleteRentHouse(@PathVariable("houseId") Long houseId) {
        userService.deleteRentHouse(houseId);
        return new AjaxResult(200, "删除成功");
    }

    @ApiOperation("卖房界面增加房子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户Id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "house_name", value = "房源名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "chooseagentid", value = "选择中介id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "area", value = "面积", dataType = "double", required = true),
            @ApiImplicitParam(name = "unit_price", value = "单元价格/平方米", dataType = "double", required = true),
            @ApiImplicitParam(name = "room", value = "厅室", dataType = "String", required = true),
            @ApiImplicitParam(name = "room", value = "厅室", dataType = "String", required = true),
    })
    @PostMapping("/addSaleHouse")//user_id就是owner_id
    public AjaxResult addSaleHouse(@RequestParam("user_id") Long user_id,
                                   @RequestParam("house_name") String house_name,
                                   @RequestParam("chooseagentid") Long chooseagentid,
                                   @RequestParam("area") double area,
                                   @RequestParam("unit_price") double unit_price,
                                   @RequestParam("room") String room) {
        userService.addSaleHouse(user_id, house_name, chooseagentid, area, unit_price, room);
        return new AjaxResult(200, "添加房子成功");
    }


    @ApiOperation("租房界面增加房子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id", value = "用户Id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "house_name", value = "房源名称", dataType = "String", required = true),
            @ApiImplicitParam(name = "chooseagentid", value = "选择中介id", dataType = "Long", required = true),
            @ApiImplicitParam(name = "area", value = "面积", dataType = "double", required = true),
            @ApiImplicitParam(name = "unit_price", value = "单元价格/平方米", dataType = "double", required = true),
            @ApiImplicitParam(name = "room", value = "厅室", dataType = "String", required = true),
            @ApiImplicitParam(name = "room", value = "厅室", dataType = "String", required = true),
    })
    @PostMapping("/addRentHouse")//user_id就是owner_id
    public AjaxResult addRentHouse(@RequestParam("user_id") Long user_id,
                                   @RequestParam("house_name") String house_name,
                                   @RequestParam("chooseagentid") Long chooseagentid,
                                   @RequestParam("area") double area,
                                   @RequestParam("rent_money") double rent_money,
                                   @RequestParam("room") String room) {
        userService.addRentHouse(user_id, house_name, chooseagentid, area, rent_money, room);
        return new AjaxResult(200, "添加房子成功");
    }

}
