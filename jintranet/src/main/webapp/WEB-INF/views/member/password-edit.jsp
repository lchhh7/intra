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
                            <h3 class="st_title">비밀번호 수정</h3>
                            <span class="st_exp">비밀번호를 수정할 수 있습니다.</span>
                        </div>
                        <div class="locationbar clearfix">
                            <a href="<c:url value="/main.do"/>" class="home"></a>
                            <span class="local">비밀번호 수정</span>
                        </div>
                    </div>
                    <div class="mcpart">
                        <div class="defaulttb sub_table width100">
                            <form id="password-edit">
                                <table class="width100">
                                    <colgroup>
                                        <col width="20%">
                                        <col width="80%">
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th>현재 비밀번호<span class="required">*</span></th>
                                        <td><input type="password" name="password" class="inputbox width40" value="${memberInfo.name}"></td>
                                    </tr>
                                    <tr>
                                        <th>새로운 비밀번호<span class="required">*</span></th>
                                        <td>
                                            <input type="password" id="newPassword" name="newPassword" class="inputbox width40" placeholder="6자리 이상, 1개이상의 문자,특수문자를 이용하세요." onkeyup="validPassword(this);">
                                        	<span class="valid-message" style="color : red;">비밀번호 조건이 일치하지 않습니다.</span>
                                        </td>
                                    </tr>
                                     <tr>
                                        <th>비밀번호 확인<span class="required">*</span></th>
                                        <td>
                                            <input type="password" id="newPassword2" name="newPassword2" class="inputbox width40" placeholder="비밀번호를 다시 입력해주세요." onkeyup="checkPassword(this);">
                                        	<span class="mismatch-message" style="color : red;">비밀번호가 일치하지 않습니다.</span>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                        <div class="btnpart mt10">
                            <a role="button" id="p-edit-btn" class="btn jjblue">저장</a>
                        </div>
                    </div>
                </div>
                <%@ include file="../include/rnb.jsp" %>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/common/js/jscolor.js"/>"></script>
<script src='<c:url value="/common/js/password.js" />'></script>
</body>

</html>