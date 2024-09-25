package DHS.temporary.server.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Builder
public class FormDto {

	private Long formId;
	private String title;
	private String description;

	private List<FieldDto> fieldDtoList;
}
