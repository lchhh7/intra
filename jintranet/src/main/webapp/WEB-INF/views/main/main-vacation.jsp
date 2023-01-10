<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="vacation-modal" class="modal">
	<div class="modal_wrap mw_big">
		<div class="title_bar clearfix">
			<h3>신청승인상태</h3>
		</div>

		<div class="modal_content">
			<div class="mline mb10">
				<div>
					<div class="floatleft mhalf mr5">
						<div class="floatleft mhalf_m">
							<select class="selectbox width100" id="search-year">
								<option value="">전체</option>
								<c:forEach var="yearList" items="${yearList}">
									<option value="${yearList}">${yearList}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div>
						<div class="floatleft">
							<a role="button" class="btn jjblue" onclick="searching()">검색</a>
						</div>
					</div>
				</div>
				<br>
				<div class="defaulttb md_table">
					<table class="fixedhead">
						<colgroup>
							<col width="17%">
							<col width="33%">
							<col width="12%">
							<col width="23%">
							<col width="13%">
						</colgroup>
						<thead>
							<tr>
								<th>종류</th>
								<th>일정</th>
								<th>결재자</th>
								<th>결재일</th>
								<th>승인상태</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="defaulttb md_table width100 scrollbody">
					<table class="width100">
						<colgroup>
							<col width="17%">
							<col width="33%">
							<col width="12%">
							<col width="23%">
							<col width="13%">
						</colgroup>
						<tbody id="vacation-tbody">
						<%-- <c:forEach var="schedule" items="${member.schedules}">
							<tr class="tbbody" onclick="schedule('+ e.id +')">
								<td>${schedule.type }</td>
								<td>${fn:split(schedule.strDt,'T')[0]} ~ ${fn:split(schedule.endDt,'T')[0]}</td>
								<td>${schedule.approveId }</td>
								<td>${schedule.approveDt }</td>
								<td>${schedule.status }</td>
							</tr>
						</c:forEach> --%> 
						</tbody>
					</table>
				</div>
				<div class="mbtnbox">
					<a role="button" id="vacation-close-btn"
						class="btn jjblue closebtn">닫기</a>
				</div>
			</div>
		</div>
	</div>
</div>