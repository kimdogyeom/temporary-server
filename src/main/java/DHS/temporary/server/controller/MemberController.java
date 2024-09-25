package DHS.temporary.server.controller;

import DHS.temporary.server.dto.MemberCreateDto;
import DHS.temporary.server.dto.MemberDto;
import DHS.temporary.server.dto.MemberListDto;
import DHS.temporary.server.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "학생 생성")
	@PostMapping
	public ResponseEntity<MemberDto> saveMember(@RequestBody MemberCreateDto memberCreateDto) {

		MemberDto memberDto = memberService.saveMember(memberCreateDto);

		return ResponseEntity.status(HttpStatus.OK).body(memberDto);
	}

	@Operation(summary = "학생 리스트")
	@GetMapping
	public ResponseEntity<MemberListDto> memberList() {

		List<MemberDto> members = memberService.findMembers();
		MemberListDto memberListDto = new MemberListDto(members);

		return ResponseEntity.status(HttpStatus.OK).body(memberListDto);
	}



}
