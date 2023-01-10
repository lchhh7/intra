package com.jinjin.jintranet.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinjin.jintranet.member.dto.MemberDefaultDTO;
import com.jinjin.jintranet.model.Member;
import com.jinjin.jintranet.model.RoleType;
import com.jinjin.jintranet.model.Schedule;
import com.jinjin.jintranet.schedule.service.ScheduleService;

@Service
public class MenuUtils {
	@Autowired
	private  ScheduleService scheduleService;
	
	private String getMenuListByMember(Member member, HttpServletRequest request) {
		String contextPath = request.getContextPath();

		StringBuffer sb = new StringBuffer();
		sb.append("<li><a class='m_icon1' href='" + contextPath + "/notice.do'>공지사항</a></li>");
		sb.append("<li><a class='m_icon2' href='" + contextPath + "/schedule.do'>일정관리</a></li>");
		sb.append("<li><a class='m_icon3' href='" + contextPath + "/commuting.do'>근태관리</a></li>");
		
		RoleType auth = member.getRole();
	
		
		switch(auth) {
		case ADMIN:
			sb.append("<li><a class='m_icon6' href='" + contextPath + "/admin/member.do'>사용자관리(관)</a></li>");
			sb.append("<li><a class='m_icon7' id='m_icon7' href='" + contextPath
					+ "/admin/schedule.do'>일정신청관리(관)</a></li>");
			sb.append("<li><a class='m_icon8' id='m_icon8' href='" + contextPath
					+ "/admin/commuting.do'>근태수정관리(관)</a></li>");
			break;
		}
		
		String[] arr = request.getServletPath().split("/");
		String path = arr[1].split(".do")[0];

		path = ("admin".equals(path)) ? (path + "/" + arr[2]).split(".do")[0] : path;

		int n = -1;
		switch (path) {
		case "notice":
			n = 0;
			break;
		case "schedule":
			n = 1;
			break;
		case "commuting":
			n = 2;
			break;
		case "admin/member":
			n = 3;
			break;
		case "admin/schedule":
			n = 4;
			break;
		case "admin/commuting":
			n = 5;
			break;
		}

		if (n == -1) {
			return sb.toString();

		} else {

			arr = sb.toString().split("</li>");
			arr[n] = arr[n].replace("<li>", "<li class=\"active\">");

			StringBuffer result = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				result.append(arr[i]).append("</li>");
			}

			return result.toString();
		}
	}

	
	public Map<String, Object> getDefaultMenu(HttpServletRequest request , Member member) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("todaySchedules" , scheduleService.todaySchedules());
		map.put("member", member);
		map.put("menu", getMenuListByMember(member, request));

		return map;
	}
}
