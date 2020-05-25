package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.PropertyValueMapper;
import com.qqlin.tmall.pojo.Product;
import com.qqlin.tmall.pojo.Property;
import com.qqlin.tmall.pojo.PropertyValue;
import com.qqlin.tmall.pojo.PropertyValueExample;
import com.qqlin.tmall.service.PropertyService;
import com.qqlin.tmall.service.PropertyValueService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    final
    PropertyService propertyService;

    final
    PropertyValueMapper propertyValueMapper;

    public PropertyValueServiceImpl(PropertyValueMapper propertyValueMapper, PropertyService propertyService) {
        this.propertyValueMapper = propertyValueMapper;
        this.propertyService = propertyService;
    }


    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.list(product.getCid());

        for (Property property :
                properties) {
            PropertyValue propertyValue = get(property.getId(), product.getId());
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());
                propertyValueMapper.insert(propertyValue);
            }
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        final PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        if (propertyValues.isEmpty()) {
            return null;
        }
        return propertyValues.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        for (PropertyValue propertyValue : propertyValues) {
            Property property = propertyService.get(propertyValue.getPtid());
            propertyValue.setProperty(property);
        }

        return propertyValues;
    }
}
