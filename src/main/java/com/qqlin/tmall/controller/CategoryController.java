package com.qqlin.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqlin.tmall.pojo.Category;
import com.qqlin.tmall.service.CategoryService;
import com.qqlin.tmall.util.ImageUtil;
import com.qqlin.tmall.util.Page;
import com.qqlin.tmall.util.UploadedImageFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Controller handling admin category CRUD operations.
 * All endpoints are prefixed with /admin_category for admin backend management.
 *
 * @author qqlin
 */
@Controller
@RequestMapping("")
public class CategoryController {

    /**
     * 这里没有用@Autowired注入
     * 而使用了构造方法注入
     */
    final
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Display paginated list of all categories.
     *
     * URL: /admin_category_list
     * Method: GET
     * Parameters:
     *   - Page page: Pagination parameters (start, count) (required)
     * Returns: "admin/listCategory" (admin/listCategory.jsp)
     *
     * Business Logic:
     * 1. Use PageHelper for pagination
     * 2. Fetch all categories from database
     * 3. Calculate total count for pagination
     * 4. Return model with categories and page info
     */
    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        /**
         * 使用了分页插件PageHelper,
         * 设置了每页查询开头、结尾以及总数
         */
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> categories = categoryService.list();
        int total = (int) new PageInfo<>(categories).getTotal();
        page.setTotal(total);

        /**
         * 分别将“categories"和”page“存入model中，
         * 然后再分别转发给/WEB-INF/jsp/admin/listCategory中的
         * --- <c:forEach items="${categories}" var="category">
         * 以及/WEB-INF/jsp/include/admin/adminPage.jsp中的
         * --- <ul class="pagination">
         */
        model.addAttribute("categories", categories);
        model.addAttribute("page", page);

        /**
         * 跳转到分类查询的页面/WEB-INF/jsp/admin/listCategory.jsp
         */
        return "admin/listCategory";
    }

    /**
     * Add a new category.
     *
     * URL: /admin_category_add
     * Method: POST
     * Parameters:
     *   - Category category: Category object with name (required)
     *   - HttpSession session: Session for getting real path (required)
     *   - UploadedImageFile uploadedImageFile: Category image file (required)
     * Returns: Redirect to "admin_category_list"
     *
     * Business Logic:
     * 1. Add category to database to get ID
     * 2. Create image directory if not exists
     * 3. Save uploaded image as category ID.jpg
     * 4. Convert image to JPG format
     * 5. Redirect to category list
     */
    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.add(category);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        
        File file = new File(imageFolder, category.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return "redirect:/admin_category_list";
    }

    /**
     * Delete a category by ID.
     *
     * URL: /admin_category_delete
     * Method: GET
     * Parameters:
     *   - int id: Category ID to delete (required)
     * Returns: Redirect to "admin_category_list"
     *
     * Business Logic:
     * 1. Delete category from database
     * 2. Delete associated category image file
     * 3. Redirect to category list
     */
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) throws IOException {
        categoryService.delete(id);

        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        return "redirect:/admin_category_list";
    }

    /**
     * Display category edit form.
     *
     * URL: /admin_category_edit
     * Method: GET
     * Parameters:
     *   - int id: Category ID to edit (required)
     * Returns: "admin/editCategory" (admin/editCategory.jsp)
     *
     * Business Logic:
     * 1. Fetch category by ID
     * 2. Return model with category for editing form
     */
    @RequestMapping("admin_category_edit")
    public String edit(int id, Model model) throws IOException {
        Category category = categoryService.get(id);
        model.addAttribute("category", category);
        return "admin/editCategory";
    }

    /**
     * Update an existing category.
     *
     * URL: /admin_category_update
     * Method: POST
     * Parameters:
     *   - Category category: Category object with updated name (required)
     *   - HttpSession session: Session for getting real path (required)
     *   - UploadedImageFile uploadedImageFile: New category image (optional)
     * Returns: Redirect to "admin_category_list"
     *
     * Business Logic:
     * 1. Update category in database
     * 2. If new image uploaded, save it as category ID.jpg
     * 3. Convert image to JPG format if applicable
     * 4. Redirect to category list
     */
    @RequestMapping("admin_category_update")
    public String update(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(category);
        MultipartFile image = uploadedImageFile.getImage();
        if (null != image && !image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, category.getId() + ".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }

}