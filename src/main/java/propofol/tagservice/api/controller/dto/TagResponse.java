package propofol.tagservice.api.controller.dto;

import lombok.Data;

@Data
public class TagResponse {
    private String name;

    public TagResponse(String name) {
        this.name = name;
    }
}
