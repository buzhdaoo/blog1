package com.blog.service;

import com.blog.NotFoundException;
import com.blog.dao.TagDao;
import com.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/26/026 Description:
 **/
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    //开启事务
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagDao.save (tag);
    }
    //开启事务
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagDao.getById (id);
    }
    //开启事务
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagDao.findAll (pageable);
    }
    //开启事务
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1=tagDao.getById (id);
        if (tag1 == null){
            throw new NotFoundException ("不存在该类型");
        }
        //把type里的值复制到type1里
        BeanUtils.copyProperties (tag,tag1);
        return tagDao.save (tag1);
    }
    //开启事务
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagDao.deleteById (id);
    }
    //开启事务
    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagDao.findByName (name);
    }
    //开启事务
    @Transactional
    @Override
    public List<Tag> listTag() {

        return tagDao.findAll ();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagDao.findAllById (convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable =  PageRequest.of(0, size,sort);
        return tagDao.findTop (pageable);
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<> ();
        if (!"".equals (ids) && ids !=null){
            String[] idArray=ids.split (",");
            for (String s : idArray) {
                list.add (new Long (s));
            }
        }
        return list;
    }
}
