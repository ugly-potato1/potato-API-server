package potato.server.community.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
