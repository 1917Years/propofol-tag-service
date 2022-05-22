package propofol.tagservice.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import propofol.tagservice.domain.exception.NotFoundTagException;
import propofol.tagservice.domain.tag.entity.Tag;
import propofol.tagservice.domain.tag.repository.TagRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    public String saveTag(Tag tag) {
        tagRepository.save(tag);
        return  "ok";
    }

    public Page<Tag> getPageTags(int page, String keypoint) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "id"));

        if(keypoint == null) return tagRepository.findPageTags(pageRequest);
        else return tagRepository.findPageTagsByKeypoint(pageRequest, keypoint);
    }

    public Slice<Tag> getSliceTageByKeypoint(String keypoint, Integer page){
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "id"));
        return tagRepository.findSliceTagsByKeypoint(pageRequest, keypoint);
    }

    public String deleteTag(String tagName) {
        Tag tag = findTag(tagName);
        tagRepository.delete(tag);
        return "ok";
    }

    @Transactional
    public String updateTag(String tagName, String name) {
        Tag tag = findTag(tagName);
        tag.changeTag(name);
        return "ok";
    }

    private Tag findTag(String tagName) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow(() -> {
            throw new NotFoundTagException("태그를 찾을 수 없습니다.");
        });
        return tag;
    }

    public List<Tag> getTagsByIds(List<Long> ids) {
        return tagRepository.findByIds(ids);
    }

    public List<Tag> getTagsByProjectBoardIds(Set<Long> ids) {
        return tagRepository.findBySetIds(ids);
    }
}
