package com.qqlin.tmall.controller;

import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.pojo.ProductImage;
import com.qqlin.tmall.service.ProductImageService;
import com.qqlin.tmall.service.ProductService;
import com.qqlin.tmall.util.ImageUtil;
import com.qqlin.tmall.util.UploadedImageFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Controller handling admin product image management.
 * Manages both single product images and detail images.
 * All endpoints are prefixed with /admin_productImage for admin backend management.
 *
 * @author qqlin
 */
@Controller
@RequestMapping("")
public class ProductImageController {
    final
    ProductService productService;

    final
    ProductImageService productImageService;

    public ProductImageController(ProductService productService, ProductImageService productImageService) {
        this.productService = productService;
        this.productImageService = productImageService;
    }

    /**
     * Add a new product image.
     *
     * URL: /admin_productImage_add
     * Method: POST
     * Parameters:
     *   - ProductImage productImage: ProductImage object with pid and type (required)
     *   - HttpSession session: Session for getting real path (required)
     *   - UploadedImageFile uploadedImageFile: Image file to upload (required)
     * Returns: Redirect to "admin_productImage_list" with product ID
     *
     * Business Logic:
     * 1. Add product image to database to get ID
     * 2. Determine image folder based on type (single or detail)
     * 3. Save uploaded image as ID.jpg
     * 4. Convert image to JPG format
     * 5. If single type, create small (56x56) and middle (217x190) versions
     * 6. Redirect to product image list
     */
    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession session, UploadedImageFile uploadedImageFile) {
        productImageService.add(productImage);
        String fileName = productImage.getId() + ".jpg";
        String imageFolder;
        String imageFolderSmall = null;
        String imageFolderMiddle = null;

        /**
         * 定位到存放产品图片的目录
         */
        if (ProductImageService.type_single.equals(productImage.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolderSmall = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolderMiddle = session.getServletContext().getRealPath("img/productSingle_middle");
        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }
        

        File file = new File(imageFolder, fileName);
        file.getParentFile().mkdirs();

        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);

            if (ProductImageService.type_single.equals(productImage.getType())) {
                File fileSmall = new File(imageFolderSmall, fileName);
                File fileMiddle = new File(imageFolderMiddle, fileName);

                ImageUtil.resizeImage(file, 56, 56, fileSmall);
                ImageUtil.resizeImage(file, 217, 190, fileMiddle);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid=" + productImage.getPid();
    }

    /**
     * Delete a product image by ID.
     *
     * URL: /admin_productImage_delete
     * Method: GET
     * Parameters:
     *   - int id: ProductImage ID to delete (required)
     * Returns: Redirect to "admin_productImage_list" with product ID
     *
     * Business Logic:
     * 1. Fetch product image to get its type and product ID
     * 2. Delete the main image file
     * 3. If single type, also delete small and middle image versions
     * 4. Delete product image from database
     * 5. Redirect to product image list
     */
    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession session) {
        final ProductImage productImage = productImageService.get(id);

        String fileName = productImage.getId() + ".jpg";
        String imageFolder;
        String imageFolderSmall = null;
        String imageFolderMiddle = null;

        if (ProductImageService.type_single.equals(productImage.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolderSmall = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolderMiddle = session.getServletContext().getRealPath("img/productSingle_middle");
            File imageFile = new File(imageFolder, fileName);
            File fileSmall = new File(imageFolderSmall, fileName);
            File fileMiddle = new File(imageFolderMiddle, fileName);

            imageFile.delete();
            fileSmall.delete();
            fileMiddle.delete();

        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder, fileName);
            imageFile.delete();
        }

        productImageService.delete(id);

        return "redirect:admin_productImage_list?pid=" + productImage.getPid();
    }

    /**
     * Display product image list for a product.
     *
     * URL: /admin_productImage_list
     * Method: GET
     * Parameters:
     *   - int pid: Product ID (required)
     * Returns: "admin/listProductImage" (admin/listProductImage.jsp)
     *
     * Business Logic:
     * 1. Fetch product by ID
     * 2. Fetch all single images for the product
     * 3. Fetch all detail images for the product
     * 4. Return model with product and both image lists
     */
    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        Product product = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("product", product);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);

        return "admin/listProductImage";
    }
}
