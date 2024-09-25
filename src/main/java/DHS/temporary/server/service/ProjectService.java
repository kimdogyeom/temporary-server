package DHS.temporary.server.service;

import DHS.temporary.server.domain.Form;
import DHS.temporary.server.domain.Member;
import DHS.temporary.server.domain.Project;
import DHS.temporary.server.dto.FormIdListDto;
import DHS.temporary.server.dto.MemberDto;
import DHS.temporary.server.dto.MemberIdListDto;
import DHS.temporary.server.dto.MemberListDto;
import DHS.temporary.server.dto.ProjectDto;
import DHS.temporary.server.repository.FormRepository;
import DHS.temporary.server.repository.MemberRepository;
import DHS.temporary.server.repository.ProjectRepository;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectService {

	private final ModelMapper modelMapper;
	private final ProjectRepository projectRepository;
	private final MemberRepository memberRepository;
	private final FormRepository formRepository;

	public ProjectDto save(ProjectDto createDto) {
		Project project = new Project();
		project.setName(createDto.getName());
		project.setDeadDate(createDto.getDeadDate());
		project.setEndDate(createDto.getEndDate());
//		log.info("생성 이미지 확인={}", createDto.getImg());
		project.setImage(Base64.getDecoder().decode(createDto.getImage()));

		return convertToDto(projectRepository.save(project));
	}

	public MemberListDto findMembersByProject(Long projectId) {
		Project project = projectRepository.findById(projectId).orElseThrow();
		List<Member> members = project.getMembers();

		return new MemberListDto(members.stream()
			.map(member -> modelMapper.map(member, MemberDto.class))
			.collect(Collectors.toList()));
	}

	public ProjectDto projectMemberUpdate(Long projectId, MemberIdListDto idListDto) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(NoSuchElementException::new);

		List<Member> members = idListDto.getMemberIdList().stream()
			.map(memberId -> memberRepository.findById(memberId.getMemberId())
				.orElseThrow(NoSuchElementException::new))
			.collect(Collectors.toList());

		project.setMembers(members);
		projectRepository.save(project);

		return convertToDto(projectRepository.save(project));
	}

	public ProjectDto projectFormUpdate(Long projectId, FormIdListDto idListDto) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(NoSuchElementException::new);

		List<Form> forms = idListDto.getFormIdList().stream()
			.map(formId -> formRepository.findById(formId.getFormId())
				.orElseThrow(NoSuchElementException::new))
			.collect(Collectors.toList());

		project.setForms(forms);
		projectRepository.save(project);

		return convertToDto(projectRepository.save(project));
	}

	public void deleteProjectById(Long id) {
		projectRepository.deleteById(id);
	}

	public ProjectDto findOneProject(Long id) {
		Project project = projectRepository.findById(id)
			.orElseThrow(NoSuchElementException::new);

		return convertToDto(projectRepository.save(project));
	}

	public List<ProjectDto> findProjects() {
		List<Project> projects = projectRepository.findAll();
		return convertToDtoList(projects);
	}


	// 단일 객체 변환
	public ProjectDto convertToDto(Project project) {
		configureModelMapper(); // ModelMapper 초기화 호출
		return modelMapper.map(project, ProjectDto.class);
	}

	public Project convertToEntity(ProjectDto projectDto) {
		return modelMapper.map(projectDto, Project.class);
	}

	// 리스트 변환
	public List<ProjectDto> convertToDtoList(List<Project> projects) {
		configureModelMapper(); // ModelMapper 초기화 호출
		return projects.stream()
			.map(project -> modelMapper.map(project, ProjectDto.class))
			.collect(Collectors.toList());
	}

	public List<Project> convertToEntityList(List<ProjectDto> projectDtos) {
		return projectDtos.stream()
			.map(projectDto -> modelMapper.map(projectDto, Project.class))
			.collect(Collectors.toList());
	}

	// Project -> ProjectDto 변경시 image가 서로 사용하는 자료형이 달라 ModelMapper에서 인식하지 못하였고 byte[] -> String 변경이 일어나야 했음
	private void configureModelMapper() {
		// byte[] -> String (Base64) 변환 컨버터 설정
		Converter<byte[], String> toBase64String = new Converter<byte[], String>() {
			@Override
			public String convert(MappingContext<byte[], String> context) {
				return context.getSource() != null ? Base64.getEncoder().encodeToString(context.getSource()) : null;
			}
		};

		// Project -> ProjectDto 매핑 설정
		modelMapper.typeMap(Project.class, ProjectDto.class)
			.addMappings(mapper -> {
				mapper.using(toBase64String).map(Project::getImage, ProjectDto::setImage);
				// 이름, 마감일, 종료일은 자동 매핑을 사용하므로 생략 가능
			});
	}

}
