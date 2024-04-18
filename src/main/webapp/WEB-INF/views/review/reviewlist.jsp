<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>후기 목록</title>
    <jsp:include page="../commonheader.jsp"></jsp:include>
    <style type="text/css">
      div.grid {
        display: grid;
        grid-template-columns: 1fr;
        grid-template-rows: 1fr 28px 1fr;
        row-gap: 10px;
      }
      #search-form {
        position: fixed; /* 화면 스크롤에 고정 */
        bottom: 20px; /* 하단 여백 설정 */
        left: 50%; 
        transform: translateX(-50%); 
        background-color: #f9f9f9;
        padding: 10px;
        border-radius: 5px;
        box-shadow: 0px 2px 5px #000000;
        z-index: 999; 
      }
      
      
    </style>
    
    <!-- <script type="text/javascript">
      $("#list-size").on("change", function () {
        search(0);
      });
    </script> -->
    <script type="text/javascript" src="/js/review/revewlist.js"></script>

    
  </head>
  <body>
    <div>
      <table class="table">
        <colgroup>
          <col width="*" />
          <col width="*" />
          <col width="*" />
          <col width="*" />
          <col width="*" />
          <col width="*" />
        </colgroup>
        <thead>
          <tr>
            <th>프로젝트 명</th>
            <th>고객사</th>
            <th>수행부서</th>
            <th>상태</th>
            <th>시작일</th>
            <th>종료일</th>
            <th>후기결과보기</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${not empty reviewlist.reviewList}">
              <c:forEach items="${reviewlist.reviewList}" var="review">
                <tr>
                  <td>
                    <a href="/review/prjId/${review.projectVO.prjId}/write"
                      >${review.projectVO.prjName}</a
                    >
                  </td>
                  <td>${review.projectVO.clntInfo}</td>
                  <td>${review.departmentVO.deptName}</td>
                  <td>${review.projectVO.prjSts}</td>
                  <td>${review.projectVO.strtDt}</td>
                  <td>${review.projectVO.endDt}</td>
                  <td
                    onclick="location.href='/review/viewresult?prjId=${review.prjId}'"
                  >
                    후기결과보기
                  </td>
                </tr>
              </c:forEach>
            </c:when>
          </c:choose>
        </tbody>
      </table>
    </div>
    
  </body>
</html>





     <!----------------------------------------------------  Paginator 시작 -------------------------------------------------------->
     <div>
      <form id="search-form">
        <!-- value=0 은 아무것도 안치고 엔터눌렀을때 에러나는것을 방지하는 하는 역할-->
        <input type="hidden" id="page-no" name="pageNo" value="0"/>
        <select id="list-size" name="listSize">
          <option value="10" ${searchReviewVO.listSize eq 10 ? 'selected' : ''}>10개</option>
          <option value="20" ${searchReviewVO.listSize eq 20 ? 'selected' : ''}>20개</option>
          <option value="30" ${searchReviewVO.listSize eq 30 ? 'selected' : ''}>30개</option>

        </select>

        <!--검색하는 타입을 만들어줌-->
        <select id="search-type" name="searchType">
          <option value="prjName" ${searchReviewVO.searchType eq 'prjName' ? 'selected' : ''}>프로젝트 명</option>
          <option value="content" ${searchReviewVO.searchType eq 'content' ? 'selected' : ''}>내용</option>
          <!-- <option value="title_content" ${searchReviewVO.searchType eq 'title_content' ? 'selected' : ''}>제목 + 내용</option> -->
          <option value="clntInfo" ${searchReviewVO.searchType eq 'clntInfo' ? 'selected' : ''}>고객사</option>
        </select>

        <input type="text" name="searchKeyword" value="${searchReviewVO.searchKeyword}"/>
        <button type="button" id="search-btn">검색</button>
        <button type="button" id="cancel-search-btn">초기화</button>


        <ul class="page-nav">
          <c:if test="${searchReviewVO.hasPrevGroup}">
            <!-- '처음'을 클릭하면, javascript에 있는 search함수에 0을 파라미터로 전달해서 form을 전송시킴 -->
            <li><a href="javascript:search(0);">처음</a></li>
            <li>
              <a
                href="javascript:search(${searchReviewVO.prevGroupStartPageNo});"
                >이전</a
              >
            </li>
          </c:if>

          <!--page번호를 반복하여 노출한다.-->
          <c:forEach
            begin="${searchReviewVO.groupStartPageNo}"
            end="${searchReviewVO.groupEndPageNo}"
            step="1"
            var="p"
          >
            <!--페이지번호(pageNo) 는 항상 0부터 시작 -->
            <li class="${searchReviewVO.pageNo eq p ? 'active' : ''}">
              <!--p와 누른페이지번호가 같다면 active줘라. 노출되는것은 1부터 시작하므로 +1 해줌-->
              <!-- p를 넣어서 search함수를 호출한다 -->
              <a href="javascript:search(${p});">${p+1}</a>
            </li>
          </c:forEach>

          <c:if test="${searchReviewVO.hasNextGroup}">
            <li>
              <a
                href="javascript:search(${searchReviewVO.nextGroupStartPageNo});"
                >다음</a
              >
            </li>
            <li>
              <a href="javascript:search(${searchReviewVO.pageCount - 1});"
                >마지막</a
              >
            </li>
          </c:if>
        </ul>
      </form>
    </div>
    <!----------------------------------------------------  Paginator 끝 -------------------------------------------------------->