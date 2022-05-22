package propofol.tagservice.domain.tag.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import propofol.tagservice.domain.tag.entity.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t")
    Page<Tag> findPageTags(Pageable pageable);

    @Query("select t from Tag t where upper(t.name) like upper(concat(:keypoint, '%'))")
    Slice<Tag> findSliceTagsByKeypoint(Pageable pageable, @Param(value = "keypoint") String keypoint);

    @Query("select t from Tag t where upper(t.name) like upper(concat(:keypoint, '%'))")
    Page<Tag> findPageTagsByKeypoint(Pageable pageable, @Param(value = "keypoint") String keypoint);

    Optional<Tag> findByName(String name);

    @Query("select t from Tag t where t.id in :ids")
    List<Tag> findByIds(@Param("ids") Collection<Long> ids);

    @Query("select t from Tag t where t.id in :ids")
    List<Tag> findBySetIds(@Param("ids") Collection<Long> ids);
}
