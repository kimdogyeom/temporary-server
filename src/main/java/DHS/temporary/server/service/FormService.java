package DHS.temporary.server.service;

import DHS.temporary.server.domain.ContentType;
import DHS.temporary.server.domain.Project;
import DHS.temporary.server.dto.FieldDto;
import DHS.temporary.server.dto.FormCreateDto;
import DHS.temporary.server.domain.Field;
import DHS.temporary.server.domain.Form;
import DHS.temporary.server.dto.FormDto;
import DHS.temporary.server.dto.FormIdDto;
import DHS.temporary.server.dto.ProjectDto;
import DHS.temporary.server.repository.FieldRepository;
import DHS.temporary.server.repository.FormRepository;
import DHS.temporary.server.repository.ProjectRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FormService {

	private final FormRepository formRepository;
	private final ProjectRepository projectRepository;
	private final ModelMapper modelMapper;

	public FormIdDto createForm(Long projectId, FormCreateDto createDto) {
		Project project = projectRepository.findById(projectId).orElseThrow();

		Form form = new Form();
		List<Field> fields = new ArrayList<>();

		form.setTitle(createDto.getTitle());
		form.setDescription(createDto.getDescription());
		form.setProject(project);

		for (FieldDto field : createDto.getFields()) {
			String description = field.getDescription();
			ContentType contentType = field.getContentType();
			String content = field.getContent();

			fields.add(Field.builder()
				.description(description)
				.contentType(contentType)
				.content(content)
				.form(form)
				.build());
		}

		form.setFields(fields);
		formRepository.save(form);

		return new FormIdDto(form.getId());
	}


	public FormDto addField(Long formId, List<FieldDto> fields) {

		Form form = formRepository.findById(formId).orElseThrow();
		List<Field> formFields = form.getFields();

		try {
			if (formFields.size() == fields.size()) {
				IntStream.range(0, fields.size()).forEach(i -> {
					Field existingField = formFields.get(i);
					FieldDto fieldDto = fields.get(i);

					// 기존 필드의 정보 업데이트
					existingField.setDescription(fieldDto.getDescription());
					existingField.setContentType(fieldDto.getContentType());
					existingField.setContent(fieldDto.getContent());
				});
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new RuntimeException("필드항목 수 불일치");
		}

		return convertToDto(form);
	}

	public List<FormDto> findAll(Long projectId) {
		List<Form> formList = formRepository.findByProjectId(projectId);
		return convertToDtoList(formList);
	}

	public FormDto findById(Long formId) {
		Form form = formRepository.findById(formId).orElseThrow();

		return convertToDto(form);
	}

	// 단일 객체 변환
	public FormDto convertToDto(Form form) {
		List<FieldDto> fieldDtos = form.getFields().stream()
			.map(field -> modelMapper.map(field, FieldDto.class))
			.collect(Collectors.toList());

		return FormDto.builder()
			.formId(form.getId())
			.title(form.getTitle())
			.description(form.getDescription())
			.fieldDtoList(fieldDtos)
			.build();
	}

	public Form convertToEntity(FormDto formDto) {
		return modelMapper.map(formDto, Form.class);
	}

	// 리스트 변환
	public List<FormDto> convertToDtoList(List<Form> forms) {
		return forms.stream()
			.map(form -> {
				List<FieldDto> fieldDtos = form.getFields().stream()
					.map(field -> modelMapper.map(field, FieldDto.class))
					.collect(Collectors.toList());

				return FormDto.builder()
					.formId(form.getId())
					.title(form.getTitle())
					.description(form.getDescription())
					.fieldDtoList(fieldDtos)
					.build();
			})
			.collect(Collectors.toList());
	}


	public List<Form> convertToEntityList(List<FormDto> formDtos) {
		return formDtos.stream()
			.map(formDto -> {
				List<Field> fields = formDto.getFieldDtoList().stream()
					.map(fieldDto -> modelMapper.map(fieldDto, Field.class))
					.collect(Collectors.toList());

				Form form = new Form();
				form.setId(formDto.getFormId());
				form.setTitle(formDto.getTitle());
				form.setDescription(formDto.getDescription());
				form.setFields(fields);

				return form;
			})
			.collect(Collectors.toList());
	}


	public void deleteForm(Long projectId, Long formId) {
		Form form = formRepository.findById(formId).orElseThrow(() -> new NoSuchElementException("폼을 찾을 수 없습니다."));
		if (!form.getProject().getId().equals(projectId)) {
			throw new IllegalArgumentException("폼에 속한 프로젝트 아이디 불일치");
		}
		formRepository.delete(form);
	}
}
