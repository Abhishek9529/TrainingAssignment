<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ================= TAB 5 ================= -->

<div id="tab5" class="tab-content">

    <div style="padding:20px;">

        <h2>Excel Upload & Download</h2>

        <br>

        <!-- Action Buttons -->

        <div style="margin-bottom:20px;">

            <button id="downloadExcelBtn"
                    onclick="downloadExcel()"
                    style="padding:8px 18px;
                       background:#0b72d9;
                       color:white;
                       border:none;
                       cursor:pointer;
                       margin-right:10px;">

                Download Excel

            </button>

            <button id="downloadTemplateBtn"
                    onclick="downloadTemplate()"
                    style="padding:8px 18px;
                       background:#198754;
                       color:white;
                       border:none;
                       cursor:pointer;
                       margin-right:20px;">

                Download Template

            </button>

        </div>

        <!-- Success and Error Message Display -->
        <c:if test="${not empty successMessage}">
            <div style="margin-bottom:15px; padding:12px; background-color:#d4edda; color:#155724; border:1px solid #c3e6cb; border-radius:4px;">
                ${successMessage}
            </div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div style="margin-bottom:15px; padding:12px; background-color:#f8d7da; color:#721c24; border:1px solid #f5c6cb; border-radius:4px;">
                ${errorMessage}
            </div>
        </c:if>

        <!-- Upload Form -->
        <!-- This form:
             - Uses multipart/form-data encoding for file upload
             - Submits POST request to /excel/upload endpoint
             - Contains file input and submit button
             - Success/error messages are displayed above via redirectAttributes
        -->
        <form id="uploadForm"
              action="${pageContext.request.contextPath}/excel/upload"
              method="post"
              enctype="multipart/form-data"
              style="margin-bottom:25px;">

            <!-- File Input: accepts only .xlsx files
                 name="excelFile" matches the @RequestParam in controller
            -->
            <input type="file"
                   id="excelFile"
                   name="excelFile"
                   accept=".xlsx,.xls"
                   style="padding:6px;"/>

            <!-- Submit Button: sends form to /excel/upload endpoint -->
            <button id="uploadExcelBtn"
                    type="submit"
                    style="padding:8px 18px;
                       margin-left:10px;
                       background:#fd7e14;
                       color:white;
                       border:none;
                       cursor:pointer;">

                Upload Excel

            </button>

        </form>

        <hr>

        <br>

        <!-- Prefix Table -->

        <table border="1"
               cellspacing="0"
               cellpadding="8"
               style="width:100%;
                  border-collapse:collapse;">

            <thead style="background:#efefef;">

            <tr>

                <th>ID</th>
                <th>Prefix</th>
                <th>Gender</th>
                <th>Prefix Of</th>

            </tr>

            </thead>

            <tbody>

            <c:forEach items="${prefixList}" var="p">

                <tr>

                    <td>${p.id}</td>
                    <td>${p.prefix}</td>
                    <td>${p.gender}</td>
                    <td>${p.prefixOf}</td>

                </tr>

            </c:forEach>

            </tbody>

        </table>

    </div>

</div>