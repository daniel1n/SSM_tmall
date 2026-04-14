package com.qqlin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqlin.tmall.pojo.Category;
import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.service.CategoryService;
import com.qqlin.tmall.service.ProductService;
import com.qqlin.tmall.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller handling admin product CRUD operations.
 * All endpoints are prefixed with /admin_product for admin backend management.
 *
 * @author qqlin
 */
@Controller
@RequestMapping("")
public class ProductController {
    final
    CategoryService categoryService;
    final
    ProductService productService;

    public ProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    /**
     * Add a new product.
     *
     * URL: /admin_product_add
     * Method: POST
     * Parameters:
     *   - Product product: Product object with name, subTitle, originalPrice, promotePrice, stock, cid (required)
     * Returns: Redirect to "admin_product_list" with category ID
     *
     * Business Logic:
     * 1. Add product to database
     * 2. Redirect to product list for the category
     */
    @RequestMapping("admin_product_add")
    public String add(Model model, Product product) {
        productService.add(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    /**
     * Delete a product by ID.
     *
     * URL: /admin_product_delete
     * Method: GET
     * Parameters:
     *   - int id: Product ID to delete (required)
     * Returns: Redirect to "admin_product_list" with category ID
     *
     * Business Logic:
     * 1. Fetch product to get its category ID
     * 2. Delete product from database
     * 3. Redirect to product list for the category
     */
    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    /**
     * Display product edit form.
     *
     * URL: /admin_product_edit
     * Method: GET
     * Parameters:
     *   - int id: Product ID to edit (required)
     * Returns: "admin/editProduct" (admin/editProduct.jsp)
     *
     * Business Logic:
     * 1. Fetch product by ID
     * 2. Fetch associated category
     * 3. Return model with product for editing form
     */
    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id) {
        Product product = productService.get(id);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        model.addAttribute("product", product);
        return "admin/editProduct";
    }

    /**
     * Update an existing product.
     *
     * URL: /admin_product_update
     * Method: POST
     * Parameters:
     *   - Product product: Product object with updated fields (required)
     * Returns: Redirect to "admin_product_list" with category ID
     *
     * Business Logic:
     * 1. Update product in database
     * 2. Redirect to product list for the category
     */
    @RequestMapping("admin_product_update")
    public String update(Product product) {
        productService.update(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    /**
     * Display paginated list of products for a category.
     *
     * URL: /admin_product_list
     * Method: GET
     * Parameters:
     *   - int cid: Category ID (required)
     *   - Page page: Pagination parameters (start, count) (required)
     * Returns: "admin/listProduct" (admin/listProduct.jsp)
     *
     * Business Logic:
     * 1. Fetch category by ID
     * 2. Use PageHelper for pagination
     * 3. Fetch products for the category
     * 4. Calculate total count for pagination
     * 5. Set pagination parameter with category ID
     * 6. Return model with products, category, and page info
     */
    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page) {
        Category category = categoryService.get(cid);

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> products = productService.list(cid);

        int total = (int) new PageInfo<>(products).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + category.getId());

        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("page", page);

        return "admin/listProduct";
    }
}
