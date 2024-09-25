package DHS.temporary.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MemberDto {

	private Long id;
	private String name;
	private String stuNumber;

}
