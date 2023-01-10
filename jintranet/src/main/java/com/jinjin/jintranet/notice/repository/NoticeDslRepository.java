package com.jinjin.jintranet.notice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jinjin.jintranet.model.Qfile.QNotice;
import com.jinjin.jintranet.model.Qfile.QNoticeAttach;
import com.jinjin.jintranet.notice.dto.NoticeSearchDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NoticeDslRepository {
	
	private final JPAQueryFactory jPAQueryFactory;
	
	QNotice notice = QNotice.notice;
	
	QNoticeAttach noticeAttach = QNoticeAttach.noticeAttach;
	
	public Page<NoticeSearchDTO> findNotices(Pageable pageable , String keyword , String searchType) {
		//paging + fetchJoin 안하면 N+1 생기고 하면 findAll후 paging을 하기때문에 성능이슈가 생김
		//그래서 페이징 따로만들어서 in하는 방식으로 변경해서 n+1로 해결하고 메모리 문제도 해결함
		
		List<Integer> ids = jPAQueryFactory.select(notice.id).from(notice)
				.where(notice.delDt.isNull() , notice.delId.isNull() ,searchTypeEq(searchType,keyword))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.orderBy(notice.id.desc())
				.fetch();
		
		List<NoticeSearchDTO> list = jPAQueryFactory.selectFrom(notice).distinct()
				.leftJoin(notice.attaches , noticeAttach)
				.fetchJoin()
				.where(notice.delDt.isNull() , notice.delId.isNull() , searchTypeEq(searchType,keyword) , notice.id.in(ids))
				.orderBy(notice.id.desc())
				.fetch()
				.stream().map(n -> new NoticeSearchDTO(n)).toList();
		
		Long count = jPAQueryFactory
				.select(notice.count())
				.from(notice)
				.where(notice.delDt.isNull() , notice.delId.isNull() , searchTypeEq(searchType,keyword))
				.fetchOne();
		
		return new PageImpl<>(list , pageable ,count);
	}
	
	private BooleanExpression searchTypeEq(String searchType , String keyword) { //searchTitle , searchCrtId
		if(searchType == null || "".equals(searchType) || keyword == null || "".equals(keyword)) {
			return null;
		}
		
		if(searchType.equals("searchTitle")) return notice.title.contains(keyword);
		else return notice.member.name.contains(keyword);
	}
}
