package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *员工信息：(EmployeeInformation)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "EmployeeInformation")
public class EmployeeInformation implements Serializable {

    //EmployeeInformation编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_information_id")
    private Integer employee_information_id;
   // 员工编号
   @Basic
    private String employee_number;
   // 姓名
   @Basic
    private String full_name;
   // 性别
   @Basic
    private String gender;
   // 联系方式
   @Basic
    private String contact_information;
   // 部门
   @Basic
    private String department;
   // 职务
   @Basic
    private String post;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
