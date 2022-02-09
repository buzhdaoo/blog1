package com.blog.service;

import com.blog.NotFoundException;
import com.blog.dao.BlogDao;
import com.blog.po.Blog;
import com.blog.po.Type;
import com.blog.util.MarkdownUtils;
import com.blog.util.MyBeanUtils;
import com.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @Author:Administrator
 * @DATE:2022/1/26/026 Description:
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;
    //开启事务
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogDao.getById (id);
    }
    //开启事务
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        /* //Specification<Blog> ():处理动态查询条件
         * //Root<Blog> root ：需要查询的对象
         * //CriteriaQuery<?> query：查询条件容器
         * //CriteriaBuilder criteriaBuilder：设置具体条件的表达式
         */
        return blogDao.findAll (new Specification<Blog> () {
            @Override
            public Predicate toPredicate(Root<Blog> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<> ();
                //非空判断
                if (!"".equals (blog.getTitle ()) && blog.getTitle ()!=null){
                    predicates.add (criteriaBuilder.like (root.<String>get ("title"),
                                                        "%"+blog.getTitle ()+"%"));
                }if (blog.getTypeId() != null){
                    predicates.add (criteriaBuilder.equal (root.<Type>get("type").get ("id"),
                                    blog.getTypeId()));
                }
                if (blog.isRecommend ()){
                    predicates.add (criteriaBuilder.equal (root.<Boolean>get("recommend"),
                                    blog.isRecommend ()));
                }
                query.where (predicates.toArray (new Predicate[predicates.size ()]));
                return null;
            }
        },pageable);
    }

    //开启事务
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId ()==null){
        blog.setCreateTime (new Date ());
        blog.setUpdateTime (new Date ());
        Integer views=blog.getViews ();
            if(views==null){
            blog.setViews (0);
            }
        }else {
            blog.setUpdateTime (new Date ());
        }
        return blogDao.save (blog);
    }
    //开启事务
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b=blogDao.getById (id);
        Integer views=blog.getViews ();
        if(views==null){
            blog.setViews (0);
        }

        if (b == null){
            throw new NotFoundException ("该博客不存在！！！");
        }
        BeanUtils.copyProperties (blog,b, MyBeanUtils.getNullPropertyNames (blog));
        b.setUpdateTime (new Date ());
        return blogDao.save (b);
    }
    //开启事务
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteById (id);
    }

    //开启事务
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll (pageable);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogDao.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query,Pageable pageable) {

        return blogDao.findByQuery (query,pageable);
    }
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogDao.getById (id);

        if (blog == null){
            throw new NotFoundException ("该博客不存在！！！");
        }

        Blog b = new Blog ( );
        BeanUtils.copyProperties (blog, b);
        String content = b.getContent ( );
        b.setContent (MarkdownUtils.markdownToHtmlExtensions (content));

        blogDao.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Long TagId ) {
        return blogDao.findAll (new Specification<Blog> ( ) {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join=root.join ("tags");


                return criteriaBuilder.equal (join.get ("id"),TagId);
            }
        },pageable);

    }

    @Override
    public Map<String, List<Blog>> archivesBlog() {

        List<String>years=blogDao.findGroupYear();

        Map<String,List<Blog>>map=new HashMap<> ();
        for (String year:years ){
            map.put (year,blogDao.findByYear (year));
        }
        return map;
    }

    @Override
    public Long countBlog() {

        return blogDao.count ();
    }
}
