package DHS.temporary.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MemberCreateDto {

	private  String name;
	private  String stuNumber;

}
