package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *进货商管理：(SupplierManagement)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "SupplierManagement")
public class SupplierManagement implements Serializable {

    //SupplierManagement编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_management_id")
    private Integer supplier_management_id;
   // 商家名称
   @Basic
    private String business_name;
   // 联系人
   @Basic
    private String contacts;
   // 联系方式
   @Basic
    private String contact_information;
   // 备注
   @Basic
    private String remarks;
   // 供货类型
   @Basic
    private String type_of_supply;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
