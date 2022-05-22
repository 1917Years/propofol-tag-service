package propofol.tagservice.api.controller.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagsResponseDto {
    private List<TagResponseDto> tags = new ArrayList<>();
}
