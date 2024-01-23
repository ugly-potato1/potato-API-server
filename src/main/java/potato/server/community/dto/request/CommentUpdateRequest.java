package potato.server.community.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateRequest {
    @NotBlank
    private String content;
}
