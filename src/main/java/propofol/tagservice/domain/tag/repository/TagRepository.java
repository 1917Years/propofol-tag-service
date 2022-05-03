package propofol.tagservice.domain.tag.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import propofol.tagservice.domain.tag.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Transactional(readOnly = true)
    @Query("select t from Tag t")
    Page<Tag> findPageTags(Pageable pageable);

    @Transactional(readOnly = true)
    @Query("select t from Tag t where t.name like :keypoint%")
    Slice<Tag> findSliceTags(Pageable pageable, @Param(value = "keypoint") String keypoint);

    Optional<Tag> findByName(String name);
}
