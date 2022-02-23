package com.houseagent.realm;
import com.houseagent.entity.Agent;
import com.houseagent.service.AgentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class AgentRealm extends AuthorizingRealm {
    @Autowired
    private AgentService agentService;

    @Override//授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //拿到当前认证成功后的用户信息
        Subject subject = SecurityUtils.getSubject();
        Agent agent = (Agent) subject.getPrincipal();
        //设置权限，从数据库拿出来设

        //设置角色（如果有的话）
        return null;
    }

    @Override//认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Agent agent = agentService.findByName(token.getUsername());//先验证用户名
        //再验证数据库密码和token的密码
        if (agent != null) {
            return new SimpleAuthenticationInfo("", agent.getPassword(),"");
        }
        return null;
    }
}
