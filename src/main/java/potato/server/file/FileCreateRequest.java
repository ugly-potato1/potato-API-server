package potato.server.file;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 정순원
 * @Since 2024-01-15
 */
@Data
@NoArgsConstructor
public class FileCreateRequest {

    private String fileName;
}
