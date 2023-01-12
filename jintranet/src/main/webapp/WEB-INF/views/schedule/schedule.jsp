<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/htmlHeader.jsp" %>

<body>
    <div class="wrap clearfix">
        <div class="bodypart width100 clearfix">
            <%@ include file="/WEB-INF/views/include/lnb.jsp" %>

            <div class="container floatleft">
                <%@ include file="/WEB-INF/views/include/header.jsp" %>

                <div class="content width100 clearfix">
                    <div class="mainpart floatleft">
                        <!-- 페이지 컨텐츠 -->
                        <div class="subtitle clearfix">
                            <div class="st clearfix">
                                <h3 class="st_title">일정관리</h3>
                                <span class="st_exp">일정 및 휴가를 조회하고 등록, 수정할 수 있습니다.</span>
                            </div>
                            <div class="locationbar clearfix">
                                <a href="<c:url value = "/main.do"/>" class="home"></a>
                                <span class="local">일정관리</span>
                            </div>
                        </div>
                        <div class="topcal width100 clearfix">
                            <div class="restv floatleft">
                                <div class="midrv">
                                    <p class="restday rd1">나의 남은 연차 일수는</p>
                                    <p class="restday rd2"><span class="rd">${vacationDays.total - vacationDays.use}일</span> 입니다.</p>
                                </div>
                                <div class="botrv">
                                    <div class="btrest clearfix">
                                        <span class="brtitle">총 휴가</span>
                                        <span class="brdays"><span class="brcalc">
                                          
                                        </span>${vacationDays.total}일</span>
                                    </div>
                                    <div class="btrest clearfix">
                                        <span class="brtitle">사용한 휴가</span>
                                        <span class="brdays">${vacationDays.use}일</span>
                                    </div>
                                </div>
                            </div>
                            <div class="defaulttb caltable floatleft">
                                <table class="width100">
                                    <colgroup>
                                        <col width="23%" />
                                        <col width="77%" />
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>일정 종류</th>
                                            <td class="clearfix">
                                                <label class="chklabel">
                                                    <input class="check" id="search-typeSC" type="checkbox" value="SC" checked><span class="checkwd">일정</span>
                                                </label>
                                                <label class="chklabel">
                                                    <input class="check" id="search-typeFV" type="checkbox" value="FV" checked><span class="checkwd">휴가</span>
                                                </label>
                                                <label class="chklabel">
                                                    <input class="check" id="search-typeHV" type="checkbox" value="HV" checked><span class="checkwd">반차</span>
                                                </label>
                                                <label class="chklabel">
                                                    <input class="check" id="search-typeOW" type="checkbox" value="OW" checked><span class="checkwd">외근</span>
                                                </label>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>캘린더 종류</th>
                                            <td>
                                                <label class="chklabel">
                                                    <input class="check" name="calendar-type" type="radio" value="" checked><span
                                                        class="checkwd">회사 캘린더</span>
                                                </label>
                                                <label class="chklabel">
                                                    <input class="check" name="calendar-type" type="radio" value="m" /><span class="checkwd">내
                                                        캘린더</span>
                                                </label>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="mcpart" id='calendar-container'>
                            <div id='calendar'></div>
                        </div>
                    </div>
                    <%@ include file="/WEB-INF/views/include/rnb.jsp" %>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/views/schedule/write-modal.jsp"%>
        <%@ include file="/WEB-INF/views/schedule/edit-modal.jsp"%>
        <%@ include file="/WEB-INF/views/include/datepicker.jsp" %>
        <%@ include file="/WEB-INF/views/include/fullcalendar.jsp" %>
    </div>
    <script>
        const _id = '${principal.member.id}';
        const _name = '${principal.member.name}';
        const _color = '${principal.member.useColor}';
    </script>
    <script src="<c:url value="/common/js/jscolor.js"/>"></script>
    <script src='<c:url value="/common/js/schedule/schedule-common.js" />'></script>
    <script src='<c:url value="/common/js/schedule/schedule-write.js" />'></script>
    <script src='<c:url value="/common/js/schedule/schedule-edit.js" />'></script>

</body>

</html>