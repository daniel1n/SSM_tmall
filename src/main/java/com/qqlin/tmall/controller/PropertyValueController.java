package com.qqlin.tmall.controller;

import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.pojo.PropertyValue;
import com.qqlin.tmall.service.ProductService;
import com.qqlin.tmall.service.PropertyValueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
@Controller
@RequestMapping("")
public class PropertyValueController {
    final
    PropertyValueService propertyValueService;

    final
    ProductService productService;

    public PropertyValueController(PropertyValueService propertyValueService, ProductService productService) {
        this.propertyValueService = propertyValueService;
        this.productService = productService;
    }

    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model, int pid) {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValueList = propertyValueService.list(product.getId());

        model.addAttribute("p", product);
        model.addAttribute("pvs", propertyValueList);

        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);
        return "success";
    }
}
