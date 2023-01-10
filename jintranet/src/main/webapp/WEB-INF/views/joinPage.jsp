<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/htmlHeader.jsp"%>
<body>
	<div class="wrap loginwrap">
		<div class="login_header clearfix">
<a href="/jintranet/login.do" class="pagelink">로그인 페이지</a>
		</div>
		<div class="login_container">
			<div class="login_main">
				<p class="login_title">인트라넷 회원가입</p>
				<div class="joinpart">
                        <div class="defaulttb sub_table width100">
                            <form method="post" id="join-form">
                                <table class="width100">
                                    <colgroup>
                                        <col width="20%">
                                        <col width="80%">
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th>이름<span class="required">*</span></th>
                                        <td><input type="text" id="name" name="name" class="inputbox width40" value="${member.name}"></td>
                                    </tr>
                                    <tr>
                                        <th>아이디<span class="required">*</span></th>
                                        <td><input type="text" id="username" name="username" class="inputbox width40"></td>
                                    </tr>
                                    <tr>
                                        <th>비밀번호<span class="required">*</span></th>
                                        <td>
                                            <input type="password" id="password" name="password" class="inputbox width40" placeholder="6자리 이상, 1개이상의 문자,특수문자를 이용하세요." onkeyup="validPassword(this);">
                                        	<span class="valid-message" style="color : red;">비밀번호 조건이 일치하지 않습니다.</span>
                                        </td>
                                    </tr>
                                     <tr>
                                        <th>비밀번호 확인<span class="required">*</span></th>
                                        <td>
                                            <input type="password" id="checkpw" name="checkpw" class="inputbox width40" placeholder="비밀번호를 다시 입력해주세요." onkeyup="checkPassword(this);">
                                        	<span class="mismatch-message" style="color : red;">비밀번호가 일치하지 않습니다.</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>직급<span class="required">*</span></th>
                                        <td>
                                            <select id="position" name="position" class="selectbox width40">
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
                                            <select id="department" name="department" class="selectbox width40">
                                                <option value="부설연구소">부설연구소</option>
					                            <option value="시스템개발부">시스템개발부</option>
					                            <option value="대외협력부">대외협력부</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>내선번호</th>
                                        <td><input type="text" id="phoneNo" name="phoneNo" class="inputbox width40" maxlength="3"></td>
                                    </tr>
                                    <tr>
                                        <th>전화번호<span class="required">*</span></th>
                                        <td><input type="text" id="mobileNo" name="mobileNo" class="inputbox width40" maxlength="13"></td>
                                    </tr>
                                    <tr>
                                        <th>HEX<span class="required">*</span></th>
                                        <td><input type="text" id="color" name="color" class="inputbox width40" data-jscolor="" readonly></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                        <div class="btnpart mt10">
                        <button id="btn-save" class="btn jjblue">회원가입</button>
                        </div>
                        </div>
                    </div>
			</div>
		</div>
	</div>
<script src="<c:url value="/common/js/jscolor.js"/>"></script>
<script src="<c:url value="/common/js/main/main.js"/>"></script>
</body>
</html>