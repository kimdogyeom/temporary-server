package DHS.temporary.server.controller;

import DHS.temporary.server.dto.FieldDto;
import DHS.temporary.server.dto.FormCreateDto;
import DHS.temporary.server.dto.FormDto;
import DHS.temporary.server.dto.FormIdDto;
import DHS.temporary.server.dto.MemberIdListDto;
import DHS.temporary.server.dto.MemberListDto;
import DHS.temporary.server.dto.ProjectDto;
import DHS.temporary.server.service.FormService;
import DHS.temporary.server.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;
	private final FormService formService;

	@Operation(summary = "프로젝트 생성")
	@PostMapping
	public ResponseEntity<ProjectDto> saveProject(@RequestBody ProjectDto projectDto) {
		ProjectDto responseProjectDto = projectService.save(projectDto);

		return ResponseEntity.status(HttpStatus.OK).body(responseProjectDto);
	}

	@Operation(summary = "프로젝트 삭제")
	@DeleteMapping("/{projectId}")
	public ResponseEntity<String> projectDelete(@PathVariable(value = "projectId") Long projectId) {
		projectService.deleteProjectById(projectId);

		return ResponseEntity.status(HttpStatus.OK).body("삭제완료");
	}

	@Operation(summary = "프로젝트 리스트 모두 불러오기")
	@GetMapping("/findList")
	public ResponseEntity<List<ProjectDto>> projectListAll() {

		List<ProjectDto> projectDtoList = projectService.findProjects();

		return ResponseEntity.status(HttpStatus.OK).body(projectDtoList);
	}

	@Operation(summary = "프로젝트 단일검색")
	@GetMapping("/find/{projectId}")
	public ResponseEntity<ProjectDto> projectFind(@PathVariable(value = "projectId") Long projectId) {

		ProjectDto projectDto = projectService.findOneProject(projectId);

		return ResponseEntity.status(HttpStatus.OK).body(projectDto);
	}

	@Operation(summary = "프로젝트 멤버 추가")
	@PatchMapping("/{projectId}/addMember")
	public ResponseEntity<ProjectDto> projectAddMember(@PathVariable(value = "projectId") Long projectId,
		@RequestBody MemberIdListDto memberIdListDto) {
		ProjectDto projectDto = projectService.projectMemberUpdate(projectId, memberIdListDto);

		return ResponseEntity.status(HttpStatus.OK).body(projectDto);
	}

	@Operation(summary = "프로젝트에 속한 멤버조회")
	@GetMapping("/{projectId}/members")
	public ResponseEntity<MemberListDto> projectMembers(@PathVariable(value = "projectId") Long projectId){
		MemberListDto members = projectService.findMembersByProject(projectId);

		return ResponseEntity.status(HttpStatus.OK).body(members);
	}

	// -----------------------------------------------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------------------------------------------

	@Operation(summary = "폼 생성")
	@PostMapping("/{projectId}/form")

	public ResponseEntity<FormIdDto> saveForm(@PathVariable(value = "projectId") Long projectId,
		@RequestBody FormCreateDto createDto) {

		FormIdDto formIdDto = formService.createForm(projectId, createDto);

		return ResponseEntity.status(HttpStatus.OK).body(formIdDto);
	}

	@Operation(summary = "폼 삭제")
	@DeleteMapping("/{projectId}/{formId}")
	public ResponseEntity<String> saveForm(@PathVariable(value = "projectId") Long projectId,
		@PathVariable(value = "formId") Long formId) {

		formService.deleteForm(projectId, formId);

		return ResponseEntity.status(HttpStatus.OK).body("삭제완료");
	}


	@Operation(summary = "폼 제출 및 수정", description = "필드를 하나만 변경해도 모든 데이터가 변경되어야 합니다.")
	@PostMapping("/{projectId}/{formId}/submit")
	public ResponseEntity<FormDto> formAddField(@PathVariable(value = "projectId") Long projectId, // projectId는 식별을 위해 넣음
		@PathVariable(value = "formId") Long formId,
		@RequestBody List<FieldDto> fields) {

		FormDto formDto = formService.addField(projectId, formId, fields);

		return ResponseEntity.status(HttpStatus.OK).body(formDto);
	}


	@Operation(summary = "프로젝트에 등록된 폼 조회 - 리스트")
	@GetMapping("/{projectId}/forms")
	public ResponseEntity<List<FormDto>> formList(@PathVariable(value = "projectId") Long projectId) {

		List<FormDto> formDtos = formService.findAll(projectId);

		return ResponseEntity.status(HttpStatus.OK).body(formDtos);
	}


	@Operation(summary = "프로젝트에 등록된 폼 조회 - 단건")
	@GetMapping("/forms/{formId}")
	public ResponseEntity<FormDto> selectForm(@PathVariable(value = "formId") Long formId) {

		FormDto formDto = formService.findById(formId);

		return ResponseEntity.status(HttpStatus.OK).body(formDto);
	}


}

