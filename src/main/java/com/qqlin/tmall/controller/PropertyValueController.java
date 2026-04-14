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
 * Controller handling admin property value management.
 * Property values are the actual specifications/values for products.
 * All endpoints are prefixed with /admin_propertyValue for admin backend management.
 *
 * @author qqlin
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

    /**
     * Display property value edit page for a product.
     *
     * URL: /admin_propertyValue_edit
     * Method: GET
     * Parameters:
     *   - int pid: Product ID (required)
     * Returns: "admin/editPropertyValue" (admin/editPropertyValue.jsp)
     *
     * Business Logic:
     * 1. Fetch product by ID
     * 2. Initialize property values for the product (create if not exist)
     * 3. Fetch all property values for the product
     * 4. Return model with product and property values for editing
     */
    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model, int pid) {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValueList = propertyValueService.list(product.getId());

        model.addAttribute("p", product);
        model.addAttribute("pvs", propertyValueList);

        return "admin/editPropertyValue";
    }

    /**
     * Update a property value (AJAX endpoint).
     *
     * URL: /admin_propertyValue_update
     * Method: POST
     * Parameters:
     *   - PropertyValue propertyValue: PropertyValue object with id and value (required)
     * Returns: "success"
     *
     * Business Logic:
     * 1. Update property value in database
     * 2. Return success response
     */
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);
        return "success";
    }
}
