package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/27/2020.
 */
@Repository
public interface BlogCommentRepository extends CrudRepository<BlogComment, Long> {

    @Query("select c from BlogComment c where c.blogId=?1 order by c.id desc")
    Page<BlogComment> findPageableBlogById(long bid, Pageable pageable);

    int countBlogCommentByBlogId(long blogId);

}
