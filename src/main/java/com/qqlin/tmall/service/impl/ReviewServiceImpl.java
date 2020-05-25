package com.qqlin.tmall.service.impl;

import com.qqlin.tmall.mapper.ReviewMapper;
import com.qqlin.tmall.pojo.Review;
import com.qqlin.tmall.pojo.ReviewExample;
import com.qqlin.tmall.pojo.User;
import com.qqlin.tmall.service.ReviewService;
import com.qqlin.tmall.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-21
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    final
    ReviewMapper reviewMapper;
    final
    UserService userService;

    public ReviewServiceImpl(ReviewMapper reviewMapper, UserService userService) {
        this.reviewMapper = reviewMapper;
        this.userService = userService;
    }

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");

        List<Review> result = reviewMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    private void setUser(List<Review> reviews) {
        for (Review review : reviews) {
            setUser(review);
        }
    }

    private void setUser(Review review) {
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }

    @Override
    public int getCount(int pid) {
        return 0;
    }
}
