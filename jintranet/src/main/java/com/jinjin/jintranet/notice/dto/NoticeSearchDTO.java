package com.jinjin.jintranet.notice.dto;

import java.time.LocalDate;

import com.jinjin.jintranet.model.Member;
import com.jinjin.jintranet.model.Notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSearchDTO {
	
	private Integer id;
	
	private String title;
	
	private Member member;
	
	private LocalDate crtDt;
	
	private Integer attachesCnt;
	
	public NoticeSearchDTO(Notice notice) {
		this.id = notice.getId();
		this.title = notice.getTitle();
		this.member = notice.getMember();
		this.crtDt = notice.getCrtDt();
		this.attachesCnt = (int) notice.getAttaches().stream().filter(a -> a.getDelId() ==null).count() ;
	}
}
