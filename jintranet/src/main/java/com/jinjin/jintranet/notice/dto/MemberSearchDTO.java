package com.jinjin.jintranet.notice.dto;

import java.time.LocalDate;

import com.jinjin.jintranet.model.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchDTO {

	private String name;
	
	private LocalDate edf;

	public MemberSearchDTO(Member m) {
		this.name = m.getName();
		this.edf = m.getEdf();
	}
	
	
}
