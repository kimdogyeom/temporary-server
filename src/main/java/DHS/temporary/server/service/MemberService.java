package DHS.temporary.server.service;

import DHS.temporary.server.domain.Member;
import DHS.temporary.server.dto.MemberCreateDto;
import DHS.temporary.server.dto.MemberDto;
import DHS.temporary.server.repository.MemberRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;


	public MemberDto saveMember(MemberCreateDto createDto) {
		Member member = new Member();
		member.setName(createDto.getName());
		member.setStuNumber(createDto.getStuNumber());

		MemberDto memberDto = convertToDto(memberRepository.save(member));
		return memberDto;
	}

	public Member findOneMember(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(NoSuchElementException::new);
	}

	public List<MemberDto> findMembers(){

		return convertToDtoList(memberRepository.findAll());
	}

	// 단일 객체 변환
	public MemberDto convertToDto(Member member) {
		return modelMapper.map(member, MemberDto.class);
	}

	public Member convertToEntity(MemberDto memberDto) {
		return modelMapper.map(memberDto, Member.class);
	}

	// 리스트 변환
	public List<MemberDto> convertToDtoList(List<Member> members) {
		return members.stream()
			.map(member -> modelMapper.map(member, MemberDto.class))
			.collect(Collectors.toList());
	}

	public List<Member> convertToEntityList(List<MemberDto> memberDtos) {
		return memberDtos.stream()
			.map(memberDto -> modelMapper.map(memberDto, Member.class))
			.collect(Collectors.toList());
	}
}
