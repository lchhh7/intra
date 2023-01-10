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
                            <h3 class="st_title">사용자관리-관리자용</h3>
                            <span class="st_exp">사용자의 정보 및 권한을 수정할 수 있습니다.</span>
                        </div>
                        <div class="locationbar clearfix">
                            <a href="<c:url value="main.do"/>" class="home"></a>
                            <span class="local">사용자관리(관)</span>
                        </div>
                    </div>
                    <div class="topbox width100 clearfix">
                        <p class="rd2 mr15">이름</p>
                        <input type="text" id="searchName" class="inputbox width15 floatleft">
                        <select id="searchPosition" class="selectbox width15 floatleft ml10">
                            <option value="">선택</option>
                            <option value="사원">사원</option>
                            <option value="대리">대리</option>
                            <option value="과장">과장</option>
                            <option value="차장">차장</option>
                            <option value="부장">부장</option>
                        </select>
                        <select id="searchDepartment" class="selectbox width20 floatleft ml10">
                            <option value="" disabled selected hidden>부서</option>
                            <option value="">선택</option>
                            <option value="부설연구소">부설연구소</option>
                            <option value="시스템개발부">시스템개발부</option>
                            <option value="대외협력부">대외협력부</option>
                        </select>
                        <a role="button" id="search-btn" class="btn jjblue ml10 floatleft">검색</a>
                    </div>
                    <div class="listbox width100">
                        <div class="lbtop mb10 clearfix">
                            <p id="total-cnt" class="total">총 사용자 0명</p>&nbsp;
                        </div>
                        <div class="defaulttb main_table width100">
                            <table class="width100">
                                <colgroup>
                                    <col width="10%"/>
                                    <col width="15%"/>
                                    <col width="8%"/>
                                    <col width="15%"/>
                                    <col width="17%"/>
                                    <col width="11%"/>
                                    <col width="16%"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>이름</th>
                                    <th>아이디</th>
                                    <th>직급</th>
                                    <th>부서</th>
                                    <th>전화번호</th>
                                    <th>내선번호</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody id="members">
                                </tbody>
                            </table>
                        </div>
                        <div id="page" class="pagination">
                        </div>
                    </div>
                </div>
                <%@ include file="../include/rnb.jsp" %>
            </div>
        </div>
    </div>

    <!-- 사용자 추가 모달 -->
    <div class="modal" id="write-modal">
        <div class="modal_wrap">
            <div class="title_bar clearfix">
                <h3>사용자 등록</h3>
            </div>
            <form id="write-form">
                <div class="modal_content">
                    <div class="mline mb10">
                        <p class="mtitle mb5">이름 <span class="required">*</span></p>
                        <input type="text" name="name" class="inputbox width100">
                    </div>
                    <div class="mline mb10">
                        <p class="mtitle mb5">아이디 <span class="required">*</span></p>
                        <input type="text" name="memberId" class="inputbox width100">
                    </div>
                    <div class="mline mb10">
                        <p class="mtitle mb5">비밀번호 <span class="required">*</span></p>
                        <input type="password" name="password" class="inputbox width100">
                    </div>
                    <div class="mline mb10 mhalf_m floatleft mr10">
                        <p class="mtitle mb5">직급 <span class="required">*</span></p>
                        <select name="position" class="selectbox width100">
                            <option value="">선택</option>
                            <option value="사원">사원</option>
                            <option value="대리">대리</option>
                            <option value="과장">과장</option>
                            <option value="차장">차장</option>
                            <option value="부장">부장</option>
                        </select>
                    </div>
                    <div class="mline mb10 mhalf_m floatright">
                        <p class="mtitle mb5">부서 <span class="required">*</span></p>
                        <select name="department" class="selectbox width100">
                            <option value="">선택</option>
                            <option value="부설연구소">부설연구소</option>
                            <option value="시스템개발부">시스템개발부</option>
                            <option value="대외협력부">대외협력부</option>
                        </select>
                    </div>
                    <div class="mline mb10">
                        <p class="mtitle mb5">전화번호 <span class="required">*</span></p>
                        <input type="text" name="mobileNo" class="inputbox width100">
                    </div>
                    <div class="mline mb10 mhalf_m floatleft mr10">
                        <p class="mtitle mb5">내선번호</p>
                        <input type="text" name="phoneNo" class="inputbox width100">
                    </div>
                    <div class="mline mb10 mhalf_m floatright">
                        <p class="mtitle mb5">HEX <span class="required">*</span></p>
                        <input type="text" name="color" class="inputbox width100" data-jscolor="" readonly>
                    </div>
                    <div class="mline mb10">
                        <p class="mtitle mb5">관리자 권한 부여<span class="required">*</span></p>
                        <div class="mchkbox width100">
                            <label class="chklabel m_label">
                                <input type="radio" name="auth" value="USER" checked="checked"><span
                                    class="checkwd">USER</span>
                            </label>
                            <label class="chklabel m_label">
                                <input type="radio" name="auth" value="ADMIN"><span
                                    class="checkwd">ADMIN</span>
                            </label>
                        </div>
                    </div>
                    <div class="mbtnbox">
                        <a role="button" id="write-btn" class="btn jjblue">저장</a>
                        <a role="button" id="write-close-btn" class="btn jjblue">닫기</a>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="modal" id="edit-modal">
        <div class="modal_wrap">
            <div class="title_bar clearfix">
                <h3>사용자정보 수정</h3>
            </div>
            <form id="edit-form">
                <input type="hidden" name="id">
                <div class="modal_content">
                    <div class="mline mb10">
                        <p class="mtitle mb5">이름 <span class="required">*</span></p>
                        <input type="text" name="name" class="inputbox width100">
                    </div>
                    <div class="mline mb10">
                        <p class="mtitle mb5">아이디 <span class="required">*</span></p>
                        <input type="text" name="memberId" class="inputbox width100" readonly>
                    </div>
<!--                     <div class="mline mb10">
                        <p class="mtitle mb5">비밀번호 <span class="required">*</span></p>
                        <input type="password" name="password" class="inputbox width100">
                    </div>
 -->                    <div class="mline mb10 mhalf_m floatleft mr10">
                        <p class="mtitle mb5">직급 <span class="required">*</span></p>
                        <select name="position" class="selectbox width100">
                            <option value="">선택</option>
                            <option value="사원">사원</option>
                            <option value="대리">대리</option>
                            <option value="과장">과장</option>
                            <option value="차장">차장</option>
                            <option value="부장">부장</option>
                        </select>
                    </div>
                    <div class="mline mb10 mhalf_m floatright">
                        <p class="mtitle mb5">부서 <span class="required">*</span></p>
                        <select name="department" class="selectbox width100">
                            <option value="">선택</option>
                            <option value="부설연구소">부설연구소</option>
                            <option value="시스템개발부">시스템개발부</option>
                            <option value="대외협력부">대외협력부</option>
                        </select>
                    </div>
                    <div class="mline mb10">
                        <p class="mtitle mb5">전화번호 <span class="required">*</span></p>
                        <input type="text" name="mobileNo" class="inputbox width100">
                    </div>
                    <div class="mline mb10 mhalf_m floatleft mr10">
                        <p class="mtitle mb5">내선번호 </p>
                        <input type="text" name="phoneNo" class="inputbox width100">
                    </div>
                    <div class="mline mb10 mhalf_m floatright">
                        <p class="mtitle mb5">HEX <span class="required">*</span></p>
                        <input type="text" name="color" class="inputbox width100" data-jscolor="" readonly>
                    </div>
                   <div class="mline mb10">
                        <p class="mtitle mb5">관리자 권한 부여<span class="required">*</span></p>
                        <div class="mchkbox width100">
                            <label class="chklabel m_label">
                                <input type="radio" name="auth" value="USER"><span
                                    class="checkwd">USER</span>
                            </label>
                            <label class="chklabel m_label">
                                <input type="radio" name="auth" value="ADMIN"><span
                                    class="checkwd">ADMIN</span>
                            </label>
                        </div>
                    </div>
                    <div class="mbtnbox">
                        <a role="button" id="edit-btn" class="btn jjblue">저장</a>
                        <a role="button" id="edit-close-btn" class="btn jjblue">닫기</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<c:url value="/common/js/jscolor.js"/>"></script>
<script src="<c:url value="/common/js/admin/member/admin-member.js"/>"></script>
</body>

</html>