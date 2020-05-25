package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.ProductMapper;
import com.qqlin.tmall.pojo.Category;
import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.pojo.ProductExample;
import com.qqlin.tmall.pojo.ProductImage;
import com.qqlin.tmall.service.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
@Service
public class ProductServiceImpl implements ProductService {

    final
    ReviewService reviewService;

    final
    OrderItemService orderItemService;

    final
    ProductMapper productMapper;

    final
    CategoryService categoryService;

    final
    ProductImageService productImageService;

    public ProductServiceImpl(ProductMapper productMapper, CategoryService categoryService, ProductImageService productImageService, OrderItemService orderItemService, ReviewService reviewService) {
        this.productMapper = productMapper;
        this.categoryService = categoryService;
        this.productImageService = productImageService;
        this.orderItemService = orderItemService;
        this.reviewService = reviewService;
    }

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setFirstProductImage(product);
        setCategory(product);
        return product;
    }

    private void setCategory(Product product) {
        int cid = product.getCid();
        Category category = categoryService.get(cid);
        product.setCategory(category);
    }

    private void setCategory(List<Product> products) {
        for (Product product : products) {
            setCategory(product);
        }
    }

    @Override
    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List result = productMapper.selectByExample(example);
        setCategory(result);
        setFirstProductImage(result);
        return result;
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> pis = productImageService.list(product.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            product.setFirstProductImage(pi);
        }
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for (Category category : cs) {
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.getSaleCount(product.getId());
        product.setSaleCount(saleCount);

        int reviewCount = reviewService.getCount(product.getId());
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        List result = productMapper.selectByExample(example);
        setFirstProductImage(result);
        setCategory(result);
        return result;
    }
}
