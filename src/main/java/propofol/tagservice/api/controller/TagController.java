package propofol.tagservice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import propofol.tagservice.api.controller.dto.*;
import propofol.tagservice.domain.tag.entity.Tag;
import propofol.tagservice.domain.tag.service.TagService;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;
    private final ModelMapper modelMapper;

    /**
     * 태그 page 조회
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getPageTags(@RequestParam("page") int page,
                                   @RequestParam(value = "keypoint", required = false) String keypoint) {
        Page<Tag> pageTags = tagService.getPageTags(page, keypoint);
        return new ResponseDto<>(HttpStatus.OK.value(), "success",
                "태그 조회 성공!", createTagPageResponse(pageTags));
    }

    /**
     * 태그 keyPoint Slice 조회
     */
    @GetMapping("/slice")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getSliceTags(@RequestParam("keypoint") String keypoint,
                                    @RequestParam("page") Integer page){
        Slice<Tag> sliceTage = tagService.getSliceTageByKeypoint(keypoint.toUpperCase(Locale.ROOT), page);
        return new ResponseDto<>(HttpStatus.OK.value(), "success",
                "태그 조회 성공!", createSliceTags(sliceTage));
    }

    /**
     * 태그 이름 List 조회
     */
    @GetMapping("/ids")
    @ResponseStatus(HttpStatus.OK)
    public TagsResponseDto getTagsByNames(@RequestParam("ids") List<Long> ids){
        TagsResponseDto responseDto = createTagsResponse(ids);

        return responseDto;
    }

    /**
     * 게시글 아이디 Set 조회
     */
    @GetMapping("/setIds")
    @ResponseStatus(HttpStatus.OK)
    public TagsResponseDto getTagsBySetProjectBoardIds(@RequestParam("ids") Set<Long> ids){
        TagsResponseDto responseDto = createTagsBySetTagIdsResponse(ids);

        return responseDto;
    }

    private TageSliceResponse createSliceTags(Slice<Tag> sliceTage) {
        TageSliceResponse tageSliceResponse = new TageSliceResponse();
        tageSliceResponse.setHasNext(sliceTage.hasNext());
        tageSliceResponse.setNowPageNumber(sliceTage.getNumber() + 1);
        List<TagResponseDto> tags = tageSliceResponse.getTags();
        sliceTage.getContent().forEach(tag -> {
            tags.add(modelMapper.map(tag, TagResponseDto.class));
        });
        return tageSliceResponse;
    }

    private TagePageResponse createTagPageResponse(Page<Tag> pageTags) {
        TagePageResponse tagePageResponse = new TagePageResponse();
        tagePageResponse.setPageTotalCount(pageTags.getTotalPages());
        tagePageResponse.setTotalCount(pageTags.getTotalElements());
        List<TagResponseDto> tags = tagePageResponse.getTags();
        pageTags.getContent().forEach(tag -> {
            tags.add(modelMapper.map(tag, TagResponseDto.class));
        });
        return tagePageResponse;
    }

    private TagsResponseDto createTagsResponse(List<Long> ids) {
        TagsResponseDto responseDto = new TagsResponseDto();
        List<TagResponseDto> responseList = responseDto.getTags();
        List<Tag> tags = tagService.getTagsByIds(ids);
        tags.forEach(tag -> {
            responseList.add(modelMapper.map(tag, TagResponseDto.class));
        });
        return responseDto;
    }

    private TagsResponseDto createTagsBySetTagIdsResponse(Set<Long> ids) {
        TagsResponseDto responseDto = new TagsResponseDto();
        List<TagResponseDto> responseList = responseDto.getTags();
        List<Tag> tags = tagService.getTagsByProjectBoardIds(ids);
        tags.forEach(tag -> {
            responseList.add(modelMapper.map(tag, TagResponseDto.class));
        });
        return responseDto;
    }
}
