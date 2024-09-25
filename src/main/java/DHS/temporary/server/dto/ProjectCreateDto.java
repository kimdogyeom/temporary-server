package DHS.temporary.server.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ProjectCreateDto {

	private String projectName;
	private Date deadDate;
	private Date endDate;
	private String img;

}
