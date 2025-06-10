package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *进货登记：(PurchaseRegistration)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "PurchaseRegistration")
public class PurchaseRegistration implements Serializable {

    //PurchaseRegistration编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_registration_id")
    private Integer purchase_registration_id;
   // 商品编号
   @Basic
    private String commodity_number;
   // 商品类型
   @Basic
    private String commodity_type;
   // 商品名称
   @Basic
    private String trade_name;
   // 进货数量
   @Basic
    private Integer purchase_quantity;
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
