package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.BlogComment;
import com.mcubes.minoxidilbd.repository.BlogCommentRepository;
import com.mcubes.minoxidilbd.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/27/2020.
 */
@Service
@Transactional
public class BlogCommentService {

    public static final Logger LOG = Logger.getLogger(BlogCommentService.class.getName());
    private static final int defaultSize = 5;
    @Autowired
    private BlogCommentRepository blogCommentRepository;
    @Autowired
    private BlogRepository blogRepository;

    public Page<BlogComment> getBlogComments(long blogId, int page){
        return blogCommentRepository.findPageableBlogById(blogId, PageRequest.of(page, defaultSize));
    }

    public boolean saveBlogComment(BlogComment comment){
        try{
            blogCommentRepository.save(comment);
            int count = blogCommentRepository.countBlogCommentByBlogId(comment.getBlogId());
            blogRepository.updateBlogCommentNumber(count, comment.getBlogId());
        }catch (Exception e){
            LOG.warning("# Ex : " + e.getMessage());
            return false;
        }
        return true;
    }
}
