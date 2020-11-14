package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.Blog;
import com.mcubes.minoxidilbd.entity.BlogComment;
import com.mcubes.minoxidilbd.repository.BlogRepository;
import com.mcubes.minoxidilbd.service.BlogCommentService;
import com.mcubes.minoxidilbd.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/26/2020.
 */
@Controller
public class BlogController {

    public static final Logger LOG = Logger.getLogger(BlogComment.class.getName());

    @Autowired
    private CommonData commonData;
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogCommentService blogCommentService;

    @RequestMapping("/blog")
    public String blog(Principal principal, Model model, @RequestParam(name = "page", defaultValue = "1") int page){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        page = (page - 1) < 0 ? 0 : page - 1;
        Page<Blog> blogPage = blogService.getBlog(page, 9);
        model.addAttribute("blogPage", blogPage);

        return "client/pages/blog";
    }

    @RequestMapping("/blog-details")
    public String blogDetails(Principal principal, Model model, @RequestParam(name = "b") long bid){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        Optional<Blog> blogOptional = blogService.getBlogById(bid);
        if(!blogOptional.isPresent()){
            return "redirect:/error";
        }
        List<Blog> latestBlog =  blogService.fetchLatestBlog(bid);
        model.addAttribute("latestBlog", latestBlog);
        model.addAttribute("blog", blogOptional.get());
        return "client/pages/blog_details";
    }

    @ResponseBody
    @RequestMapping("/blog-comments")
    public Page<BlogComment> fetchBlogComments(@RequestParam long bid, @RequestParam(name = "page", defaultValue = "0")
            int page){
        page = page < 0 ? 0 : page;
        return blogCommentService.getBlogComments(bid, page);
    }

    @ResponseBody
    @RequestMapping("/save_comment")
    public boolean saveComment(@Valid @ModelAttribute BlogComment comment, BindingResult result){
        LOG.info("# Comment : " + comment);
        if(result.hasErrors())
            return false;
        return blogCommentService.saveBlogComment(comment);
    }

}
