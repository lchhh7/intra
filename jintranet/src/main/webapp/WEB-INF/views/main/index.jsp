<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../include/htmlHeader.jsp"%>

<body>
	<div class="wrap clearfix">
		<div class="bodypart width100 clearfix">
			<%@ include file="../include/lnb.jsp"%>

			<div class="container floatleft">
				<%@ include file="../include/header.jsp"%>

				<div class="content width100 clearfix">
					<div class="mainpart floatleft">
						<div class="topcontents width100 clearfix">
							<div class="worktime tc floatleft">
								<div class="moretop">
									<a class="more floatright"
										href="<c:url value='/commuting.do'/>">더보기</a>
								</div>
								<div class="tcontent">
									<div class="timecheck midtc">
										<div class="working clearfix">
											<p class="wtime" id="workingTime"></p>
											<p>
												<span class="wk"> ${workingStatus} </span>
											</p>
										</div>
										<div class="wstart clearfix">
											<p class="ws">출근시간</p>
											<p class="wstime" id="goWorkTime"></p>
										</div>
									</div>
									<div class="checkbtn bottc clearfix">
	                                <a role="button" id="commuteOn" class="go">출근하기</a>
	                                <a role="button" id="commuteOff" class="go">퇴근하기</a>
                                </div>
								</div>
							</div>

							<div class="approve tc floatleft">
								<div class="moretop">
									<a role="button" id="vacation-btn" class="more floatright">더보기</a>
								</div>
								<div class="tcontent">
									<div class="apptop midtc clearfix">
										<div class="statetitle">
											<p id="vacation-info">
											<c:choose>
												<c:when test="${empty vacation }">
													신청한 휴가가 없습니다.
												</c:when>
												<c:otherwise>
													${vacation[0].strDt}  
													<c:if test="${vacation[0].type eq 'FV'}">연차</c:if>
													<c:if test="${vacation[0].type ne 'FV'}">반차</c:if>
												</c:otherwise>
											</c:choose>
											</p>
										</div>
										<div class="statement">
											<div class="s_title">
												<p>승인상태</p>
											</div>
											<div class="state">
												<c:if test="${!empty vacation}">
													<p id="vacation-status">
													<c:choose>
														<c:when test="${vacation[0].status eq 'N'}">비승인</c:when>
														<c:when test="${vacation[0].status eq 'Y'}">승인</c:when>
														<c:otherwise>대기</c:otherwise>
													</c:choose>
													</p>
												</c:if>
											</div>
										</div>
									</div>
									<div class="bottc clearfix">
										<p class="app_t">승인대기</p>
										<p id="vacation-cnt" class="app_c">${cnt }건</p>
									</div>
								</div>
							</div>

							<div class="docnum tc floatleft">
								<div class="moretop">
								</div>
								<div class="tcontent">
								</div>
							</div>
						</div>

						<div class="project width100">
							<div class="title_bar clearfix">
								<h3>공지사항</h3>
								<a class="more floatright" href="<c:url value="/notice.do"/>">더보기</a>
							</div>
							<div class="pcontent">
								<ul class="tapmenu clearfix">
								</ul>
								<div class="defaulttb main_table mtbox width100">
									<table class="width100">
										<colgroup>
											<col width="7%" />
											<col width="48%" />
											<col width="20%" />
											<col width="25%" />
										</colgroup>
										<thead>
											<tr>
												<th>번호</th>
												<th>제목</th>
												<th>작성자</th>
												<th>작성일</th>
											</tr>
										</thead>
										<tbody id="projects">
											 <c:forEach var="n" items="${noticeList}" varStatus="status">
												<tr class="tbbody"
													onclick="location.href='<c:url value='/notice/view.do?id=${n.id}'/>'">
													<td>${n.id}</td>
													<td>${n.title}</td>
													<td>${n.member.name}</td>
													<td>${n.crtDt}</td>
												</tr>
											</c:forEach> 
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<%@ include file="../include/rnb.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="main-vacation.jsp"%>
	<%@ include file="../schedule/edit-modal.jsp"%> <!-- 더보기 수정용 모달(나중에 삭제해야됨) -->
	<%@ include file="../include/datepicker.jsp"%>
	<%@include file="noticePopup.jsp" %>
	
	<script src="<c:url value="/common/js/jscolor.js"/>"></script>
	<script src='<c:url value="/common/js/main/main.js" />'></script>
	<script src='<c:url value="/common/js/main/main-vacation.js" />'></script>
	<script src='<c:url value="/common/js/main/main-popup.js" />'></script>
	<script>
	const goTime = function () {
        const workingStatus = "${workingStatus}";
        const goWorkTime = document.getElementById("goWorkTime");
        const goToWorkTime = "${goToWorkTime}";
        
        if (workingStatus == '근무중') {
            goWorkTime.innerHTML = goToWorkTime.substr(11, 2) + "시" + goToWorkTime.substr(14, 2) + "분";
            workTime();
        } else if (workingStatus != '근무중' && "${goToWorkTime}" != '') {
            const workingTime = document.getElementById("workingTime");
            const goToWorkTime = "${goToWorkTime}";
            goToWorkTime.innerHTML = goToWorkTime.substr(11, 2) + "시" + goToWorkTime.substr(14, 2) + "분";
            goWorkTime.innerHTML = goToWorkTime.substr(11, 2) + "시" + goToWorkTime.substr(14, 2) + "분";
            const goTime = new Date(goToWorkTime.substr(0, 4), goToWorkTime.substr(5, 2) - 1, goToWorkTime.substr(8, 2), goToWorkTime.substr(11, 2), goToWorkTime.substr(14, 2));
            const offTime = "${offToWorkTime}";
            const now = new Date(offTime.substr(0, 4), offTime.substr(5, 2) - 1, offTime.substr(8, 2), offTime.substr(11, 2), offTime.substr(14, 2));
            const subTime = now.getTime() - goTime.getTime();
            const time = parseInt(subTime / 1000 / 60 / 60);
            const minute = parseInt((subTime - (time * 1000 * 60 * 60)) / 1000 / 60);
            workingTime.innerHTML = time + "시간 " + minute + "분";
        }
    }

    const workTime = function () {
        const workingTime = document.getElementById("workingTime");
        const goWorkTime = "${goToWorkTime}";
        const goTime = new Date(goWorkTime.substr(0, 4), goWorkTime.substr(5, 2) - 1, goWorkTime.substr(8, 2), goWorkTime.substr(11, 2), goWorkTime.substr(14, 2));
        const now = new Date();
        const subTime = now.getTime() - goTime.getTime();
        const time = parseInt(subTime / 1000 / 60 / 60);
        const minute = parseInt((subTime - (time * 1000 * 60 * 60)) / 1000 / 60);
        workingTime.innerHTML = time + "시간 " + minute + "분";
        setTimeout(workTime, 60000);
    }
	goTime();
	
    document.getElementById('commuteOn').addEventListener('click', function () {
    	commuteInsert('Y');
    }, true);
    
    document.getElementById('commuteOff').addEventListener('click', function () {
    	commuteInsert('N');
    }, true);
</script>
</body>
</html>
