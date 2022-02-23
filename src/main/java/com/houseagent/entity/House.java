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

@Table(name = "t_house")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ApiModel("房源实体类")
public class House {
    @Id
    @Column(name = "house_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "房源id")
    private Long houseId;
    @ApiModelProperty(value = "楼盘名称")
    @Column(name = "house_name")
    private String houseName;
    @ApiModelProperty(value = "发布状况")
    @Column(name = "is_published")
    private String isPublished;
    @ApiModelProperty(value = "出租状况")
    @Column(name = "is_rent")
    private String isRent;
    @ApiModelProperty(value = "销售状况")
    @Column(name = "is_sold")
    private String isSold;
    @ApiModelProperty(value = "交易状况")
    @Column(name = "is_finished")
    private String isFinished;
    @ApiModelProperty(value = "楼盘面积")
    private double area;
    @ApiModelProperty(value = "租金/月")
    @Column(name = "rent_money")
    private Double rentMoney;
    @ApiModelProperty("单元价格/平方米")
    @Column(name = "unit_price")
    private Double unitPrice;
    @ApiModelProperty(value = "厅室")
    private String room;
    @ApiModelProperty(value = "记录创建时间")
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新时间")
    @LastModifiedDate
    private LocalDateTime updateTime;


    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="owner_id")//(外键)
    @JsonIgnore
    private User user;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="agent_id")
    @JsonIgnore
    private Agent agent;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Rent> rents;

}
