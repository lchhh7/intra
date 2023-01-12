<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="aside floatleft">
    <div class="profile width100">
        <div class="pf_wrap">
            <div class="pf_part">
                <div class="clearfix">
                    <div class="pficon"></div>
                    <div class="pfname clearfix">
                        <p class="position">${principal.member.position}</p>
                        <p class="name">${principal.member.name}</p>
                        <p class="mailadd">${principal.member.memberId}@jinjin.co.kr</p>
                    </div>
                </div>
                <div class="bottom_pf clearfix">
                    <p class="dept">${principal.member.department}</p>
                    <p class="num">내선번호 : ${principal.member.phoneNo}</p>
                </div>
            </div>
            <a class="btn jjblue" href="<c:url value='/member/edit.do'/>">정보수정</a>
            <a class="btn jjblue" href="<c:url value='/member/p/edit.do'/>">비밀번호변경</a>
        </div>
    </div>
    <div class="today_sch">
        <div class="title_bar clearfix">
            <h3>오늘의 일정</h3>
            <a class="more floatright" href="<c:url value="/schedule.do"/>">더보기</a>
        </div>
        <ul class="sch_list">
            <c:forEach var="s" items="${todaySchedules}">
                <c:if test='${s.type eq "SC" or s.type eq "OW"}'>
                    <li class='sch'>
                        <a role="button" onclick="">
                            <p class="sname">${s.title}</p>
                            <p class="stime">${fn:split(s.strDt,'T')[1]} - ${fn:split(s.endDt,'T')[1]}</p>
                        </a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
    <div class="today_rest">
        <div class="title_bar clearfix">
            <h3>오늘의 휴가</h3>
            <a class="more floatright" href="<c:url value="/schedule.do"/>">더보기</a>
        </div>
        <ul class="sch_list">
            <c:forEach var="s" items="${todaySchedules}">
                <c:if test='${(s.type eq "FV" or s.type eq "HV") and s.status eq "Y"}'>
                    <c:choose>
                        <c:when test='${s.type eq "FV"}'>
                            <c:set var="className" value="r_full"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="className" value="r_half"/>
                        </c:otherwise>
                    </c:choose>
                    <li class='rest ${className}'>
                        <a role="button" onclick="">
                             <p class="rtime">${fn:split(s.strDt,'T')[1]} - ${fn:split(s.endDt,'T')[1]}</p> 
                            <p class="rname">${s.title}</p>
                        </a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>