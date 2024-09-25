package DHS.temporary.server.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

	private Long id;
	private String name;
	private Date deadDate;
	private Date endDate;
	private String image;

}
