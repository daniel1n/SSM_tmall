package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.CategoryMapper;
import com.qqlin.tmall.pojo.Category;
import com.qqlin.tmall.pojo.CategoryExample;
import com.qqlin.tmall.service.CategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-18
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    final
    CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Cacheable(value = "categories", key = "'all'")
    public List<Category> list() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Cacheable(value = "categories", key = "#id")
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }


}
