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
                    <!-- 페이지 컨텐츠 -->
                    <div class="subtitle clearfix">
                        <div class="st clearfix">
                            <h3 class="st_title">정보수정</h3>
                            <span class="st_exp">정보를 수정할 수 있습니다.</span>
                        </div>
                        <div class="locationbar clearfix">
                            <a href="<c:url value="/main.do"/>" class="home"></a>
                            <span class="local">정보수정</span>
                        </div>
                    </div>
                    <div class="mcpart">
                        <div class="defaulttb sub_table width100">
                            <form name="edit" id="member-edit">
                                <table class="width100">
                                    <colgroup>
                                        <col width="20%">
                                        <col width="80%">
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th>이름<span class="required">*</span></th>
                                        <td><input type="text" name="name" class="inputbox width40" value="${memberInfo.name}"></td>
                                    </tr>
                                    <tr>
                                        <th>아이디</th>
                                        <td><input type="text" name="memberId" class="inputbox width40" value="${memberInfo.memberId}" readonly></td>
                                    </tr>
                                    <tr>
                                        <th>직급<span class="required">*</span></th>
                                        <td>
                                            <select name="position" class="selectbox width40">
                                                <option value="사원">사원</option>
					                            <option value="대리">대리</option>
					                            <option value="과장">과장</option>
					                            <option value="차장">차장</option>
					                            <option value="부장">부장</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>부서</th>
                                        <td>
                                            <select name="department" class="selectbox width40">
                                                <option value="부설연구소">부설연구소</option>
					                            <option value="시스템개발부">시스템개발부</option>
					                            <option value="대외협력부">대외협력부</option>

                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>내선번호</th>
                                        <td><input type="text" name="phoneNo" class="inputbox width40" value="${memberInfo.phoneNo}" maxlength="3"></td>
                                    </tr>
                                    <tr>
                                        <th>전화번호<span class="required">*</span></th>
                                        <td><input type="text" name="mobileNo" class="inputbox width40" value="${memberInfo.mobileNo}" maxlength="13"></td>
                                    </tr>
                                    <tr>
                                        <th>HEX<span class="required">*</span></th>
                                        <td><input type="text" name="color" class="inputbox width40" value="${memberInfo.useColor}" data-jscolor="" readonly></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                        <div class="btnpart mt10">
                            <a role="button" id="m-edit-btn" class="btn jjblue">저장</a>
                        </div>
                    </div>
                </div>
                <%@ include file="../include/rnb.jsp" %>
            </div>
        </div>
    </div>

</div>
<script src="<c:url value="/common/js/jscolor.js"/>"></script>
<script src='<c:url value="/common/js/member.js" />'></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        editForm.position.value = '${principal.member.position}';
        editForm.department.value = '${principal.member.department}';
    });
</script>
</body>

</html>