package propofol.tagservice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import propofol.tagservice.api.controller.dto.TagResponse;
import propofol.tagservice.api.controller.dto.TagePageResponse;
import propofol.tagservice.api.controller.dto.TageSliceResponse;
import propofol.tagservice.domain.tag.entity.Tag;
import propofol.tagservice.domain.tag.service.TagService;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    /**
     * 페이지 단위 태그 조회
     */
    @GetMapping
    public TagePageResponse getPageTags(@RequestParam("page") Integer page){
        Page<Tag> pageTags = tagService.getPageTags(page);
        return createTagPageResponse(pageTags);

    }

    /**
     * 태그 keyPoint 조회
     */
    @GetMapping("/slice")
    public TageSliceResponse getSliceTags(@RequestParam("keypoint") String keypoint,
                                          @RequestParam("page") Integer page){
        Slice<Tag> sliceTage = tagService.getSliceTage(keypoint.toUpperCase(Locale.ROOT), page);
        return createSliceTags(sliceTage);

    }

    private TageSliceResponse createSliceTags(Slice<Tag> sliceTage) {
        TageSliceResponse tageSliceResponse = new TageSliceResponse();
        tageSliceResponse.setHasNext(sliceTage.hasNext());
        tageSliceResponse.setNowPageNumber(sliceTage.getNumber() + 1);
        List<TagResponse> tags = tageSliceResponse.getTags();
        sliceTage.getContent().forEach(tag -> {
            tags.add(new TagResponse(tag.getName()));
        });
        return tageSliceResponse;
    }

    private TagePageResponse createTagPageResponse(Page<Tag> pageTags) {
        TagePageResponse tagePageResponse = new TagePageResponse();
        tagePageResponse.setPageTotalCount(pageTags.getTotalPages());
        tagePageResponse.setTotalCount(pageTags.getTotalElements());
        List<TagResponse> tags = tagePageResponse.getTags();
        pageTags.getContent().forEach(tag -> {
            tags.add(new TagResponse(tag.getName()));
        });
        return tagePageResponse;
    }
}
