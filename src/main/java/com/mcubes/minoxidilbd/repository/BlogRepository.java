package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by A.A.MAMUN on 10/27/2020.
 */
@Repository
public interface BlogRepository extends CrudRepository<Blog, Long> {

    @Query("select b from Blog b order by b.id desc")
    Page<Blog> findPageableBlog(Pageable pageable);

    @Query("select b from Blog b where not b.id = ?1 order by b.id desc")
    List<Blog> findLatestBlog(long id, Pageable pageable);

    @Modifying
    @Query("update Blog b set b.numberOfComment=?1 where b.id=?2")
    void updateBlogCommentNumber(int numberOfComment, long id);
}
