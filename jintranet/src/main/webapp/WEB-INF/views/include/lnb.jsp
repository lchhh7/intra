<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="<c:url value="/common/js/common.js"/>"></script>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>

<div class="lnb floatleft">
    <div class="lnb_header width100">
        <h1><a class="toplogo" href="<c:url value="/main.do"/>"></a></h1>
    </div>
    <ul class="menulist">
        <li <c:if test="${fn:replace(requestScope['javax.servlet.forward.request_uri'],pageContext.request.contextPath,'') eq '/notice.do'}">
      	 class= "active"</c:if>><a class='m_icon1' href='${pageContext.request.contextPath}/notice.do'>공지사항</a></li>
		<li <c:if test="${fn:replace(requestScope['javax.servlet.forward.request_uri'],pageContext.request.contextPath,'') eq '/schedule.do'}">
      	 class= "active"</c:if>><a class='m_icon2' href='${pageContext.request.contextPath}/schedule.do'>일정관리</a></li>
		<li <c:if test="${fn:replace(requestScope['javax.servlet.forward.request_uri'],pageContext.request.contextPath,'') eq '/commuting.do'}">
      	 class= "active"</c:if>><a class='m_icon3' href='${pageContext.request.contextPath}/commuting.do'>근태관리</a></li>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li <c:if test="${fn:replace(requestScope['javax.servlet.forward.request_uri'],pageContext.request.contextPath,'') eq '/admin/member.do'}">
      	 class= "active"</c:if>><a class='m_icon6' href='${pageContext.request.contextPath}/admin/member.do'>사용자관리(관)</a></li>
		<li <c:if test="${fn:replace(requestScope['javax.servlet.forward.request_uri'],pageContext.request.contextPath,'') eq '/admin/schedule.do'}">
      	 class= "active"</c:if>><a class='m_icon7' id='m_icon7' href='${pageContext.request.contextPath}/admin/schedule.do'>일정신청관리(관)</a></li>
		<li <c:if test="${fn:replace(requestScope['javax.servlet.forward.request_uri'],pageContext.request.contextPath,'') eq '/admin/commuting.do'}">
      	 class= "active"</c:if>><a class='m_icon8' id='m_icon8' href='${pageContext.request.contextPath}/admin/commuting.do'>근태수정관리(관)</a></li>
    </sec:authorize>
    </ul>
</div>