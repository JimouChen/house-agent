package com.houseagent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
@ApiModel("中介实体类")
@Table(name = "t_agent")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Agent {
    @ApiModelProperty("中介id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id")

    private Long agentId;
    @ApiModelProperty("中介名字")
    @Column(name = "agent_name")
    private String agentName;
    @ApiModelProperty("中介登录密码")
    private String password;
    @ApiModelProperty("中介联系方式")
    @Column(name = "phone_number")
    private String phoneNumber;
    @ApiModelProperty("中介性别")
    private String sex;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @ApiModelProperty(value = "记录创建时间")
    private LocalDateTime createTime;
    @LastModifiedDate
    @ApiModelProperty(value = "记录更新时间")
    private LocalDateTime updateTime;


    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<House> houses;


}
