package propofol.tagservice.api.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TagRequestDto {
    @NotBlank
    private String name;
}
