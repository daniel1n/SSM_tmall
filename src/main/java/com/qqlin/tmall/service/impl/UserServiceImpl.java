package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.UserMapper;
import com.qqlin.tmall.pojo.User;
import com.qqlin.tmall.pojo.UserExample;
import com.qqlin.tmall.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
@Service
public class UserServiceImpl implements UserService {

    final
    UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");

        return userMapper.selectByExample(example);
    }

    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> result = userMapper.selectByExample(example);
        if (!result.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public User get(String name, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> result = userMapper.selectByExample(example);
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

}
