<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../include/htmlHeader.jsp" %>

<body>
<div class="wrap clearfix">
    <div class="bodypart width100 clearfix">
        <%@ include file="../include/lnb.jsp" %>

        <div class="container floatleft">
            <%@ include file="../include/header.jsp" %>

            <div class="content width100 clearfix">
                <div class="mainpart floatleft">
                    <div class="subtitle clearfix">
                        <div class="st clearfix">
                            <h3 class="st_title">근태관리</h3>
                            <span class="st_exp">나의 근태현황을 확인할 수 있습니다.</span>
                        </div>
                        <div class="locationbar clearfix">
                            <a href="<c:url value="/main.do"/>" class="home"></a>
                            <span class="local">근태관리</span>
                        </div>
                    </div>
                   
                    <div class="mcpart" id='calendar-container'>
                        <div id='calendar'></div>
                    </div>

                    <c:set var="id" value="${memberId}"></c:set>

                </div>
                <%@ include file="../include/rnb.jsp" %>
            </div>
        </div>
    </div>
    <%@ include file="commuting-edit.jsp" %>
    <%@ include file="commuting-write.jsp" %>

    <%@ include file="../include/datepicker.jsp" %>
    <%@ include file="../include/fullcalendar.jsp" %>
</div>
<script src='<c:url value="/common/js/commuting/commuting-common.js" />'></script>
<script src='<c:url value="/common/js/commuting/commuting-write.js" />'></script>
<script src='<c:url value="/common/js/commuting/commuting-edit.js" />'></script>
</body>

</html>
