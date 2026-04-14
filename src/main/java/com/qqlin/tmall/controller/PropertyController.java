package com.qqlin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqlin.tmall.pojo.Category;
import com.qqlin.tmall.pojo.Property;
import com.qqlin.tmall.service.CategoryService;
import com.qqlin.tmall.service.PropertyService;
import com.qqlin.tmall.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller handling admin property (specification) CRUD operations.
 * All endpoints are prefixed with /admin_property for admin backend management.
 *
 * @author qqlin
 */
@Controller
@RequestMapping("")
public class PropertyController {

    final
    PropertyService propertyService;

    final
    CategoryService categoryService;

    public PropertyController(PropertyService propertyService, CategoryService categoryService) {
        this.propertyService = propertyService;
        this.categoryService = categoryService;
    }

    /**
     * Add a new property (specification) for a category.
     *
     * URL: /admin_property_add
     * Method: POST
     * Parameters:
     *   - Property property: Property object with name and cid (required)
     * Returns: Redirect to "admin_property_list" with category ID
     *
     * Business Logic:
     * 1. Add property to database
     * 2. Redirect to property list for the category
     */
    @RequestMapping("admin_property_add")
    public String add(Property property) {
        propertyService.add(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    /**
     * Delete a property by ID.
     *
     * URL: /admin_property_delete
     * Method: GET
     * Parameters:
     *   - int id: Property ID to delete (required)
     * Returns: Redirect to "admin_property_list" with category ID
     *
     * Business Logic:
     * 1. Fetch property to get its category ID
     * 2. Delete property from database
     * 3. Redirect to property list for the category
     */
    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    /**
     * Display property edit form.
     *
     * URL: /admin_property_edit
     * Method: GET
     * Parameters:
     *   - int id: Property ID to edit (required)
     * Returns: "admin/editProperty" (admin/editProperty.jsp)
     *
     * Business Logic:
     * 1. Fetch property by ID
     * 2. Fetch associated category
     * 3. Return model with property for editing form
     */
    @RequestMapping("admin_property_edit")
    public String edit(Model model, int id) {
        Property property = propertyService.get(id);
        Category category = categoryService.get(property.getCid());
        property.setCategory(category);
        model.addAttribute("property", property);
        return "admin/editProperty";
    }

    /**
     * Update an existing property.
     *
     * URL: /admin_property_update
     * Method: POST
     * Parameters:
     *   - Property property: Property object with updated name (required)
     * Returns: Redirect to "admin_property_list" with category ID
     *
     * Business Logic:
     * 1. Update property in database
     * 2. Redirect to property list for the category
     */
    @RequestMapping("admin_property_update")
    public String update(Property property) {
        propertyService.update(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    /**
     * Display paginated list of properties for a category.
     *
     * URL: /admin_property_list
     * Method: GET
     * Parameters:
     *   - int cid: Category ID (required)
     *   - Page page: Pagination parameters (start, count) (required)
     * Returns: "admin/listProperty" (admin/listProperty.jsp)
     *
     * Business Logic:
     * 1. Fetch category by ID
     * 2. Use PageHelper for pagination
     * 3. Fetch properties for the category
     * 4. Calculate total count for pagination
     * 5. Set pagination parameter with category ID
     * 6. Return model with properties, category, and page info
     */
    @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page) {
        Category category = categoryService.get(cid);

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> properties = propertyService.list(cid);

        int total = (int) new PageInfo<>(properties).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + category.getId());

        model.addAttribute("properties", properties);
        model.addAttribute("category", category);
        model.addAttribute("page", page);

        return "admin/listProperty";
    }
}
