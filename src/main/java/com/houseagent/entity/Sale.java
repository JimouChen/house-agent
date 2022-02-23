package com.houseagent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "t_sale_record")
@ApiModel("Sale实体类")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Sale {
    //

    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("售房记录id")
    private Long saleId;
    @ApiModelProperty( "卖房时间")
    @CreatedDate
    @Column(name = "buy_time", updatable = false, nullable = false)
    private LocalDateTime buyTime;
    @ApiModelProperty(value = "显示情况：不显示：0；已显示：1")
    @Column(name = "valid")
    private String valid;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="buyer_id")//(外键)
    @JsonIgnore
    private User user;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="house_id")
    @JsonIgnore
    private House house;
}
