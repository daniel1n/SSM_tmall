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
 * @author qqlin
 * @date 2020-5-18
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

    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) throws IOException {
        categoryService.delete(id);

        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String edit(int id, Model model) throws IOException {
        Category category = categoryService.get(id);
        model.addAttribute("category", category);
        return "admin/editCategory";
    }

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