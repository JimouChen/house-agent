package com.houseagent.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "t_admin")
@ApiModel("管理员实体类")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)

public class Admin {
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("管理员ID")
    private Long adminId;
    @Column(name = "admin_name")
    private String adminName;
    @ApiModelProperty("管理员密码")
    private String password;
    @CreatedDate
    @Column(nullable = false, updatable = false)

    @ApiModelProperty("记录创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("记录修改时间")
    @LastModifiedDate private LocalDateTime updateTime;


}
