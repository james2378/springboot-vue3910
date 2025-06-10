package com.project.demo.controller;

import com.project.demo.entity.SalesRegistration;
import com.project.demo.service.SalesRegistrationService;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *销售登记：(SalesRegistration)表控制层
 *
 */
@RestController
@RequestMapping("/sales_registration")
public class SalesRegistrationController extends BaseController<SalesRegistration,SalesRegistrationService> {

    /**
     *销售登记对象
     */
    @Autowired
    public SalesRegistrationController(SalesRegistrationService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        String sql = "SELECT MAX(sales_registration_id) AS max FROM "+"sales_registration";
        Query select = service.runCountSql(sql);
        Integer max = (Integer) select.getSingleResult();
        sql = ("SELECT count(*) count FROM `commodity_management` INNER JOIN `sales_registration` ON commodity_management.commodity_number=sales_registration.commodity_number WHERE commodity_management.inventory_quantity &#60; sales_registration.sales_volumes AND sales_registration.sales_registration_id="+max).replaceAll("&#60;","<");
        select = service.runCountSql(sql);
        Integer count = Integer.valueOf(String.valueOf(select.getSingleResult()));
        if(count>0){
            sql = "delete from "+"sales_registration"+" WHERE "+"sales_registration_id"+" ="+max;
            select = service.runCountSql(sql);
            select.executeUpdate();
            return error(30000,"库存不足!");
        }
        sql = "UPDATE `commodity_management` INNER JOIN `sales_registration` ON commodity_management.commodity_number=sales_registration.commodity_number SET commodity_management.inventory_quantity = commodity_management.inventory_quantity - sales_registration.sales_volumes WHERE sales_registration.sales_registration_id="+max;
        select = service.runCountSql(sql);
        select.executeUpdate();
        return success(1);
    }

}
