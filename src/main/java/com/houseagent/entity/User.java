package com.houseagent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "t_user")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "用户实体类")
public class User {
    @ApiModelProperty(value = "用户id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @ApiModelProperty(value = "用户名称")
    private String username;
    @ApiModelProperty(value = "用户登录密码")
    private String password;
    @ApiModelProperty(value = "用户手机号码")
    private String phoneNumber;
    @ApiModelProperty(value = "性别：男/女")
    private String sex;
    @ApiModelProperty(value = "记录创建时间")
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;
    @ApiModelProperty(value = "记录修改时间")
    @LastModifiedDate
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<House> houses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Rent> rents;

}
