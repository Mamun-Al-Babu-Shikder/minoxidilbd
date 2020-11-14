package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.Blog;
import com.mcubes.minoxidilbd.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by A.A.MAMUN on 10/27/2020.
 */
@Service
public class BlogService {

    private static final int defaultSize = 9;
    @Autowired
    private BlogRepository blogRepository;

    public Page<Blog> getBlog(int page, int size){
        return  blogRepository.findPageableBlog(PageRequest.of(page, size));
    }

    public Optional<Blog> getBlogById(long id){
        return blogRepository.findById(id);
    }

    public List<Blog> fetchLatestBlog(long id){
        return blogRepository.findLatestBlog(id, PageRequest.of(0, 5));
    }
}
