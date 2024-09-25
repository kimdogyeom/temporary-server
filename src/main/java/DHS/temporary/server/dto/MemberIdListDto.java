package DHS.temporary.server.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MemberIdListDto {

	private  List<MemberIdDto> memberIdList;

}
