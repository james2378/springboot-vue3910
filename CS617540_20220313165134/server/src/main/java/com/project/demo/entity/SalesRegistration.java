package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *销售登记：(SalesRegistration)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "SalesRegistration")
public class SalesRegistration implements Serializable {

    //SalesRegistration编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_registration_id")
    private Integer sales_registration_id;
   // 商品编号
   @Basic
    private String commodity_number;
   // 商品类型
   @Basic
    private String commodity_type;
   // 商品名称
   @Basic
    private String trade_name;
   // 销售数量
   @Basic
    private Integer sales_volumes;
   // 备注
   @Basic
    private String remarks;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
