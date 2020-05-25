package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.ProductImageMapper;
import com.qqlin.tmall.pojo.ProductImage;
import com.qqlin.tmall.pojo.ProductImageExample;
import com.qqlin.tmall.service.ProductImageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    final
    ProductImageMapper productImageMapper;

    public ProductImageServiceImpl(ProductImageMapper productImageMapper) {
        this.productImageMapper = productImageMapper;
    }

    @Override
    public void add(ProductImage productImage) {
        productImageMapper.insert(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageMapper.updateByPrimaryKeySelective(productImage);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int pid, String type) {
        ProductImageExample example = new ProductImageExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andTypeEqualTo(type);
        example.setOrderByClause("id desc");

        return productImageMapper.selectByExample(example);
    }
}
