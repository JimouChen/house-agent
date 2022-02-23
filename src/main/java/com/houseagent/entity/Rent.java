package com.houseagent.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
@ApiModel("Rent实体类" )

@Table(name = "t_rent_record")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Rent {
    @ApiModelProperty(value = "租房记录id")
    @Id
    @Column(name = "rent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;


    @ApiModelProperty(value = "租的时间/月")
    @Column(name = "rent_length")
    private int rentLength;
    @ApiModelProperty(value = "记录创建时间")
    @CreatedDate
    @Column(name = "begin_time", updatable = false, nullable = false)
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "显示情况：不显示：0；已显示：1")
    @Column(name = "valid")
    private String valid;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="renter_id")//(外键)
    @JsonIgnore
    private User user;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="house_id")
    @JsonIgnore
    private House house;


}
