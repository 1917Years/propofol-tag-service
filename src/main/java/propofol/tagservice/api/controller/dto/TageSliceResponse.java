package propofol.tagservice.api.controller.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TageSliceResponse {
    private Boolean hasNext;
    private Integer nowPageNumber;
    private List<TagResponse> tags = new ArrayList<>();
}
