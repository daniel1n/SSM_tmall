package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.PropertyMapper;
import com.qqlin.tmall.pojo.Property;
import com.qqlin.tmall.pojo.PropertyExample;
import com.qqlin.tmall.service.PropertyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    final
    PropertyMapper propertyMapper;

    public PropertyServiceImpl(PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
    }

    @Override
    public void add(Property property) {
        propertyMapper.insert(property);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Property property) {
        propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Override
    public Property get(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int cid) {
        PropertyExample example = new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");

        return propertyMapper.selectByExample(example);
    }
}
