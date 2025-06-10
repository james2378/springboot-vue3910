package com.project.demo.controller;

import com.project.demo.entity.PurchaseRegistration;
import com.project.demo.service.PurchaseRegistrationService;
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
 *进货登记：(PurchaseRegistration)表控制层
 *
 */
@RestController
@RequestMapping("/purchase_registration")
public class PurchaseRegistrationController extends BaseController<PurchaseRegistration,PurchaseRegistrationService> {

    /**
     *进货登记对象
     */
    @Autowired
    public PurchaseRegistrationController(PurchaseRegistrationService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        String sql = "SELECT MAX(purchase_registration_id) AS max FROM "+"purchase_registration";
        Query select = service.runCountSql(sql);
        Integer max = (Integer) select.getSingleResult();
        sql = "UPDATE `commodity_management` INNER JOIN `purchase_registration` ON commodity_management.commodity_number=purchase_registration.commodity_number SET commodity_management.inventory_quantity = commodity_management.inventory_quantity + purchase_registration.purchase_quantity WHERE purchase_registration.purchase_registration_id="+max;
        select = service.runCountSql(sql);
        select.executeUpdate();
        return success(1);
    }

}
