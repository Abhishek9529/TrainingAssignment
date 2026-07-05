<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Tab 3 -->
<div id="tab3" class="tab-content">
  <div style="padding: 10px;">
    <div class="list-filters" style="display:flex; gap:8px; align-items:center; margin-bottom:8px;">
      <input id="searchName" type="text" placeholder="Search Name" style="flex:1; padding:8px;"/>
      <input id="searchCode" type="text" placeholder="Search Code" style="width:180px; padding:8px;"/>
      <select id="filterUserType" style="width:140px; padding:8px;">
        <option value="">User Type</option>
        <option value="Staff">Staff</option>
        <option value="Doctor">Doctor</option>
        <option value="Consultant">Consultant</option>
      </select>
      <input id="filterPhone" type="text" placeholder="PHONE" style="width:120px; padding:8px;"/>
      <select id="filterDepartment" style="width:180px; padding:8px;">
        <option value="">Department</option>
      </select>
      <select id="filterStatus" style="width:120px; padding:8px;">
        <option value="">Status</option>
        <option value="Confirmed">Confirmed</option>
        <option value="Trainee">Trainee</option>
        <option value="Regular">Regular</option>
      </select>
    </div>

    <table id="staffTable" style="width:100%; border-collapse:collapse;">
      <thead>
      <tr style="background:#f6f6f6;">
        <th style="text-align:left; padding:10px;">Name</th>
        <th style="padding:10px;">Code</th>
        <th style="padding:10px;">User Type</th>
        <th style="padding:10px;">PHONE</th>
        <th style="padding:10px;">Department</th>
        <th style="padding:10px;">Status</th>
        <th style="padding:10px;">JOINING DATE</th>
      </tr>
      </thead>

        <tbody>
        <c:forEach var="staff" items="${staffList}">

            <tr>

                <td>${staff.name}</td>

                <td>${staff.code}</td>

                <td>${staff.userType}</td>

                <td>${staff.phone}</td>

                <td>${staff.department}</td>

                <td>${staff.status}</td>

                <td>${staff.joiningDate}</td>

            </tr>

        </c:forEach>
        </tbody>

    </table>

    <div class="pagination" style="display:flex; align-items:center; gap:8px; margin-top:10px;">
      <button id="firstPage" title="First">|&lt;</button>
      <button id="prevPage" title="Previous">&lt;</button>
      <span>Page <input id="currentPage" value="1" style="width:40px; text-align:center;"/> / <span id="totalPages">1</span></span>
      <button id="nextPage" title="Next">&gt;</button>
      <button id="lastPage" title="Last">&gt;|</button>
      <span>Page size <input id="pageSize" value="15" style="width:60px; text-align:center;"/></span>
      <span>Total Records <strong id="totalRecords">0</strong></span>
    </div>
  </div>
</div>