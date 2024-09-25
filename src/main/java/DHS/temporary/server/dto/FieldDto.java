package DHS.temporary.server.dto;

import DHS.temporary.server.domain.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FieldDto {

	private String description;
	private ContentType contentType;

	// 초기생성 시 Null
	private String content;

}
