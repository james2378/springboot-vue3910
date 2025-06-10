package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *财务统计：(FinancialStatistics)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "FinancialStatistics")
public class FinancialStatistics implements Serializable {

    //FinancialStatistics编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "financial_statistics_id")
    private Integer financial_statistics_id;
   // 营业额
   @Basic
    private String turnover;
   // 日期
   @Basic
    private Timestamp date;
   // 统计人
   @Basic
    private String statistician;
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
