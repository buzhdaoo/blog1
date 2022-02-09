package com.blog.service;

import com.blog.NotFoundException;
import com.blog.dao.TypeDao;
import com.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/26/026 Description:
 **/
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    //开启事务
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeDao.save (type);
    }

    //开启事务
    @Transactional
    @Override
    public Type getType( Long id) {
        return typeDao.getById (id);
    }


    //开启事务
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll (pageable);
    }

    //开启事务
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1=typeDao.getById (id);
        if (type1 == null){
            throw new NotFoundException ("不存在该类型");
        }
        //把type里的值复制到type1里
        BeanUtils.copyProperties (type,type1);
        return typeDao.save (type1);
    }

    //开启事务
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeDao.deleteById (id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByName (name);
    }

    //开启事务
    @Transactional
    @Override
    public List<Type> listType() {
        return typeDao.findAll ();
    }
    //开启事务
    @Transactional
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable =  PageRequest.of(0, size,sort);
        return typeDao.findTop (pageable);
    }
}
