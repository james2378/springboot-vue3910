package com.project.demo.controller;

import com.project.demo.entity.SalesSystem;
import com.project.demo.service.SalesSystemService;
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
 *销售系统：(SalesSystem)表控制层
 *
 */
@RestController
@RequestMapping("/sales_system")
public class SalesSystemController extends BaseController<SalesSystem,SalesSystemService> {

    /**
     *销售系统对象
     */
    @Autowired
    public SalesSystemController(SalesSystemService service) {
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
