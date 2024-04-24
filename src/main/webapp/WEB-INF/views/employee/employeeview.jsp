<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 상세정보 </title>
    <jsp:include page="../commonheader.jsp"></jsp:include>
    <script type="text/javascript" src="/js/employee/employeeview.js"></script>
     <style type="text/css">
        div.grid {
          display: grid;
          grid-template-columns: 80px 1fr;
          grid-template-rows: repeat(6, 28px) auto auto 1fr;
          row-gap: 10px;
        }
        .change-table{
          text-align: center;
        }
        .photo{
          width: 10rem;
          height: 10rem;
        }
        label{
            font-weight: bold;
        }
      </style>
</head>
<body>
    <h3 style="margin: 1rem auto; text-align: center;">${employeeVO.empName} ${employeeVO.commonCodeVO.cmcdName} 정보란</h3>
    <div>
      <c:choose>
        <c:when test="${not empty employeeVO.prfl}">
          <img src="/employee/file/download/${employeeVO.prfl}" alt="프로필 사진" class="photo">
        </c:when>
        <c:otherwise>
          <img src="/images/login.png" alt="프로필 사진" class="photo">
        </c:otherwise>
        
      </c:choose>
    </div>

    <div class="grid" data-id="${employeeVO.empId}">
        <label for="empName">사원 이름</label>
        <div>${employeeVO.empName}</div>

        <label for="empId">사원 ID</label>
        <div id="empId" data-id="${employeeVO.empId}">${employeeVO.empId}</div>

        <label for="workSts">재직 상태</label>
        <div> 
          ${employeeVO.workSts eq '201' ? '재직' : 
            employeeVO.workSts eq '202' ? '휴직' : 
            employeeVO.workSts eq '203' ? '퇴직예정' : 
            employeeVO.workSts eq '204' ? '퇴직' : ''}
        </div>

        <label for="hireYear">입사연차</label>
        <div>${employeeVO.hireYear}</div>

        <label for="hireDt">입사일</label>
        <div>${employeeVO.hireDt}</div>

        <label for="deptName">부서</label>
        <div>${employeeVO.departmentVO.deptName}</div>

        <c:if test="${empty employeeVO.teamList}">
          <label for="noneTmName">팀</label>
          <div id="noneTmName">소속된 팀이 존재하지 않습니다.</div>
        </c:if>
        <c:forEach items="${employeeVO.teamList}" var="teamList">
          <label for="tmName">팀</label>
          <div id="tmName">${teamList.tmName}</div>
        </c:forEach>

        <label for="jobName">직무</label>
        <div>${employeeVO.jobVO.jobName}</div>

        <label for="cntct">연락처</label>
        <div>${employeeVO.cntct}</div>

        <label for="addr">주소</label>
        <div>${employeeVO.addr}</div>

        <label for="brth">생년월일</label>
        <div>${employeeVO.brth}</div>

        <label for="email">이메일</label>
        <div>${employeeVO.email}</div>
      </div>
        <c:if test="${sessionScope._LOGIN_USER_.empId eq employeeVO.empId || sessionScope._LOGIN_USER_.admnCode eq '301'}">
          <div class="btn-group">
            <button class="backto-list">
              <a href="/employee/search">목록</a>
            </button>
            <button> 
              <a href="/employee/modify/${employeeVO.empId}">수정</a>
            </button>
          </div>
          <div>
            <h5>직무 변경 사항</h5>
            <table class="job-change change-table">
              <thead>
                <tr>

                  <th>순서</th>
                  <th>이전 직무</th>
                  <th>근무 시작일</th>
                  <th>근무 종료일</th>
                  <th>변경 사유</th>
                </tr>
              </thead>
              <tbody>
                <c:if test="${fn:length(jobHistList) == 0}">
                  <tr>
                    <td colspan="5">
                      내역이 존재하지 않습니다.

                    </td>
                  </tr>
                </c:if>
                <c:forEach items="${jobHistList}" var="jobHist" varStatus="item">
                  <tr>
                    <td>${item.count}</td>
                    <td>${jobHist.jobVO.jobName}</td>
                    <td>${jobHist.jobStrtDt}</td>
                    <td>${jobHist.jobEndDt}</td>
                    <td>${jobHist.cnNote}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
          <div>
            <h5>직급 변경 사항</h5>
            <table class="position-change change-table">
              <thead>
                <tr>

                  <th>순서</th>
                  <th>이전 직급</th>
                  <th>변경된 직급</th>
                  <th>근무 시작일</th>
                  <th>근무 종료일</th>
                  <th>변경 사유</th>
                </tr>
              </thead>
              <tbody>
                <c:if test="${fn:length(positionHistList) == 0}">
                  <tr>
                    <td colspan="5">
                      내역이 존재하지 않습니다.

                    </td>
                  </tr>
                </c:if>
                <c:forEach items="${positionHistList}" var="posotionHist" varStatus="item">
                  <tr>
                    <td>${item.count}</td>
                    <td>${posotionHist.pastCommonVO.cmcdName}</td>
                    <td>${posotionHist.commonVO.cmcdName}</td>
                    <td>${posotionHist.pstnStrtDt}</td>
                    <td>${posotionHist.pstnEndDt}</td>
                    <td>${posotionHist.cnNote}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
          <div>
            <h5>부서 변경 사항</h5>
            <table class="dept-change change-table">
              <thead>
                <tr>

                  <th>순서</th>
                  <th>이전 부서</th>
                  <th>근무 시작일</th>
                  <th>근무 종료일</th>
                  <th>변경 사유</th>
                </tr>
              </thead>
              <tbody>
                <c:if test="${fn:length(departmentHistList) == 0}">
                  <tr>
                    <td colspan="5">
                      내역이 존재하지 않습니다.

                    </td>
                  </tr>
                </c:if>
                <c:forEach items="${departmentHistList}" var="deptHist" varStatus="item">
                  <tr>
                    <td>${item.count}</td>
                    <td>${deptHist.departmentVO.deptName}</td>
                    <td>${deptHist.deptStrtDt}</td>
                    <td>${deptHist.deptEndDt}</td>
                    <td>${deptHist.cnNote}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:if>
</body>
</html>