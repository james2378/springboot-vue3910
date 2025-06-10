package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *会员用户：(MemberUser)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "MemberUser")
public class MemberUser implements Serializable {

    //MemberUser编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_user_id")
    private Integer member_user_id;
   // 姓名
   @Basic
    private String full_name;
   // 性别
   @Basic
    private String gender;
    // 用户编号
    @Id
    @Column(name = "user_id")
    private Integer userId;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
