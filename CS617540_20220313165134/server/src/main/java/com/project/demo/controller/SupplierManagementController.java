package com.project.demo.controller;

import com.project.demo.entity.SupplierManagement;
import com.project.demo.service.SupplierManagementService;
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
 *进货商管理：(SupplierManagement)表控制层
 *
 */
@RestController
@RequestMapping("/supplier_management")
public class SupplierManagementController extends BaseController<SupplierManagement,SupplierManagementService> {

    /**
     *进货商管理对象
     */
    @Autowired
    public SupplierManagementController(SupplierManagementService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        return success(1);
    }

}
