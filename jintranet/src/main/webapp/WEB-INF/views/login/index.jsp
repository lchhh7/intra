<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/htmlHeader.jsp" %>
<body>
<div class="wrap loginwrap">
    <div class="login_header clearfix">
        <!-- <img class="logo" src="" alt="로고"> -->
        <a href="/jintranet/join.do" class="pagelink">회원가입</a>
    </div>
    <div class="login_container">
        <div class="login_main">
            <p class="login_title">인트라넷 로그인</p>
            <div class="login_box">
                <h3>로그인</h3>
                <form action="/jintranet/auth/loginProc" method="post">
	                <input type="text" id="username" name="username" class="inputbox" placeholder="아이디를 입력해주세요.">
	                <input type="password" id="password" name="password" class="inputbox" placeholder="비밀번호를 입력해주세요.">
	                <label class="idcheck">
	                    <input type="checkbox" id="saveId" class="check">
	                    <span>아이디 저장</span>
	                </label>
	                <button type="submit" class="loginbtn">로그인</button>
            	</form>
	            <%--     <a href="https://kauth.kakao.com/oauth/authorize?client_id=817064497bd340a285e9af9786027c10&redirect_uri=http://localhost:8080<%= contextPath%>auth/kakao/callback&response_type=code">
	                <img src="<%= contextPath%>common/img/kakao_login_medium_narrow.png"></a> --%>
            </div>

            <div class="login_footer">
                <address>
                    <br>Copyright© 2022 chlee.
                </address>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const cookieId = getCookie("username");

        if (cookieId !== null) {
            document.getElementById("username").value = cookieId;
            document.getElementById("saveId").checked = true;
        }
    });

    <%-- 쿠키 설정 --%>
    function setCookie(name, value, exp) {
        var date = new Date();
        date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
        document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
    };

    <%-- 쿠키값 조회 --%>
    function getCookie(name) {
        var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
        return value ? value[2] : null;
    };

    <%-- 쿠키값 삭제 --%>
    function deleteCookie(name) {
        document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    }
</script>
</body>
</html>