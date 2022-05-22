package propofol.tagservice.api.controller.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagePageResponse {

    private Integer pageTotalCount;
    private Long totalCount;
    private List<TagResponseDto> tags = new ArrayList<>();
}
