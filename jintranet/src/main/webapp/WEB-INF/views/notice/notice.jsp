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
                                <h3 class="st_title">공지사항</h3>
                                <span class="st_exp">글쓰기는 공지권한이 있는 관리자만 작성 가능합니다.</span>
                            </div>
                            <div class="locationbar clearfix">
                                <a href="<c:url value="/main.do"/>" class="home"></a>
                                <span class="local">공지사항</span>
                            </div>
                        </div>
                        <div class="topbox infotb width100">
                        <form action="<%=contextPath %>notice.do" method="GET">
                        	<select id="search-type" name="searchType" class="selectbox width130px">
                                <option value="searchTitle" <c:if test="${searchType eq 'searchTitle'}">selected</c:if>>제목</option>
                                <option value="searchCrtId" <c:if test="${searchType eq 'searchCrtId'}">selected</c:if>>작성자</option>
                            </select>
                        
                            <input type="text" name="keyword" class="inputbox width400px ml5" value="${keyword }">
                            <input type="submit" style="background : #1256BB" class="btn jjblue ml10" value="검색">
                        </form>
                        </div>
                        <div class="listbox width100">
                            <div class="lbtop mb10 clearfix">
                                <p id="total-cnt" class="total">총 게시물 ${noticeList.totalElements} 건</p>
                                <a href="<c:url value="/notice/write.do"/>" class="btn jjblue" id="writeAuthority">글쓰기</a>
                            </div>
                            <div class="defaulttb main_table width100">
                                <table class="width100">
                                    <colgroup>
                                        <col width="6%" />
                                        <col width="45%" />
                                        <col width="13%" />
                                        <col width="8%" />
                                        <col width="13%" />
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th>번호</th>
                                            <th>제목</th>
                                            <th>작성자</th>
                                            <th>첨부파일</th>
                                            <th>등록일</th>
                                        </tr>
                                    </thead>
                                   <!--  <tbody id="notice">
                                    </tbody> -->
                                    <c:choose>
                                    	<c:when test="${noticeList.content.size() > 0}">
		                                    <c:forEach var="e" items="${noticeList.content }">
		                                    	<tr class="tbbody" onclick="location.href='<%=contextPath%>notice/view.do?id=${e.id}'">
							                    <td>${e.id}</td>
							                    <td>${e.title}</td>
							                    <td>${e.member.name}</td>
							                    <td>
							                   <c:if test ="${e.attachesCnt > 0 }">
							                        <img src="<%= contextPath%>common/img/file.png" alt="첨부파일">
							                   </c:if>
							                    </td>
							                    <td>${e.crtDt}</td>
							                    </tr>
		                                    
		                                    </c:forEach>
	                                	</c:when>
	                                	<c:otherwise>
	                                		<tr class="tbbody">
	                                			<td colspan="5">게시글이 존재하지 않습니다.</td>
	                                		</tr>
	                                	</c:otherwise>
                                    </c:choose>
                                </table>
                            </div>
                            <!-- <div id="page" class="pagination">
                            </div> -->
                            
                            <c:choose>
                            <c:when test="${noticeList.totalPages > 0 }">
	                            <div class="pagination">
	                            	<a role='button' class='pfirst' href="?page=0&searchType=${searchType}&keyword=${keyword}"><img src='<%= contextPath%>common/img/pfirst.png' alt='첫페이지'></a>
	                            	<a role='button' class='pprev' href="?page=${noticeList.number >=1 ? noticeList.number -1 : noticeList.number  }&searchType=${searchType}&keyword=${keyword}"><img src='<%= contextPath%>common/img/pprev.png' alt='앞페이지'></a>
	                            	 <c:forEach var="i" begin="0" end="${noticeList.totalPages-1}">
			                        	<a role='button' class='pnum 
			                        	<c:if test ="${noticeList.number == i}"> active</c:if>
			                        	' href="?page=${i}&searchType=${searchType}&keyword=${keyword}">${i+1} </a>
			                        </c:forEach>
	                            	<a role='button' class='pnext' href="?page=${noticeList.number < noticeList.totalPages -1? noticeList.number +1 : noticeList.number}&searchType=${searchType}&keyword=${keyword}"><img src='<%= contextPath%>common/img/pnext.png' alt='뒤페이지'></a>
	                            	<a role='button' class='plast' href="?page=${noticeList.totalPages -1 }&searchType=${searchType}&keyword=${keyword}"><img src='<%= contextPath%>common/img/plast.png' alt='마지막페이지'></a>
	                            </div> 
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <%@ include file="../include/rnb.jsp" %>
                </div>
            </div>
        </div>
    </div>

</body>

</html>