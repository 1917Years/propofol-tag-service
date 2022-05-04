package propofol.tagservice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import propofol.tagservice.api.controller.dto.ResponseDto;
import propofol.tagservice.api.controller.dto.TagRequestDto;
import propofol.tagservice.domain.tag.entity.Tag;
import propofol.tagservice.domain.tag.service.TagService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/tags")
public class TagAdminController {

    private final TagService tagService;

    /**
     * 태그 생성
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto saveTag(@Validated @RequestBody TagRequestDto requestDto){
        Tag tag = Tag.createTag().name(requestDto.getName()).build();
        return new ResponseDto<>(HttpStatus.CREATED.value(), "success",
                "태그 생성 성공!", tagService.saveTag(tag));
    }

    /**
     * 태그 삭제
     */
    @DeleteMapping("/{tagName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto deleteTag(@PathVariable(value = "tagName") String tagName){
        return new ResponseDto<>(HttpStatus.OK.value(), "success",
                "태그 삭제 성공!", tagService.deleteTag(tagName));
    }

    /**
     * 태그 수정
     */
    @PostMapping("/{tagName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto updateTag(@PathVariable(value = "tagName") String tagName,
                            @Validated @RequestBody TagRequestDto requestDto){
        return new ResponseDto<>(HttpStatus.OK.value(), "success",
                "태그 수정 성공!", tagService.updateTag(tagName, requestDto.getName()));
    }

}
