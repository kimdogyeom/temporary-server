package DHS.temporary.server.controller;

import DHS.temporary.server.domain.Field;
import DHS.temporary.server.domain.Form;
import DHS.temporary.server.dto.FieldDto;
import DHS.temporary.server.dto.FormCreateDto;
import DHS.temporary.server.dto.FormDto;
import DHS.temporary.server.dto.FormIdDto;
import DHS.temporary.server.service.FormService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormController {
//
//private final FormService formService;
//
//@Operation(summary = "폼 생성")
//@PostMapping("/{projectId}")
//public ResponseEntity<FormIdDto> saveForm(@PathVariable(value = "projectId") Long projectId, @RequestBody FormCreateDto createDto) {
//
//	FormIdDto formIdDto = formService.createForm(projectId, createDto);
//
//	return ResponseEntity.status(HttpStatus.OK).body(formIdDto);
//}
//
//
//@Operation(summary = "폼 제출 및 수정", description = "필드를 하나만 변경해도 모든 데이터가 변경되어야 합니다.")
//@PostMapping("/field")
//public ResponseEntity<FormDto> formAddField(@RequestParam Long formId, @RequestBody List<FieldDto> fields ) {
//
//	FormDto formDto = formService.addField(formId, fields);
//
//	return ResponseEntity.status(HttpStatus.OK).body(formDto);
//}
//
//
//@Operation(summary = "프로젝트에 등록된 폼 조회 - 리스트")
//@GetMapping("/{projectId}/forms")
//public ResponseEntity<List<FormDto>> formList(@PathVariable(value = "projectId") Long projectId) {
//
//	List<FormDto> formDtos = formService.findAll(projectId);
//
//	return ResponseEntity.status(HttpStatus.OK).body(formDtos);
//}
//
//
//@Operation(summary = "프로젝트에 등록된 폼 조회 - 단건")
//@GetMapping("/forms/{formId}")
//public ResponseEntity<FormDto> selectForm(@PathVariable(value = "formId")Long formId) {
//
//	FormDto formDto = formService.findById(formId);
//
//	return ResponseEntity.status(HttpStatus.OK).body(formDto);
//}





}
