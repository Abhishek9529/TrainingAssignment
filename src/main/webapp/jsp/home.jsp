<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
    <style>
        /* Tab Container */
        .tab-container {
            width: 100%;
            border: 1px solid #ccc;
        }

        /* Tab Buttons */
        .tab-buttons {
            display: flex;
            background-color: #f1f1f1;
            border-bottom: 2px solid #ddd;
        }

        .tab-btn {
            padding: 12px 20px;
            cursor: pointer;
            background-color: #f1f1f1;
            border: none;
            outline: none;
            font-size: 14px;
            border-bottom: 3px solid transparent;
            transition: all 0.3s;
        }

        .tab-btn:hover {
            background-color: #e0e0e0;
        }

        .tab-btn.active {
            border-bottom: 3px solid #0066cc;
            background-color: white;
            color: #0066cc;
            font-weight: bold;
        }

        /* Tab Content */
        .tab-content {
            display: none;
            padding: 20px;
        }

        .tab-content.active {
            display: block;
        }
    </style>


    <title>Training Assignment</title>
</head>

<body>

<!-- Tab Container -->
<div class="tab-container">

    <!-- Tab Buttons -->
    <div class="tab-buttons">
        <button class="tab-btn active" onclick="switchTab(event, 'tab1')">Drop Down</button>
        <button class="tab-btn" onclick="switchTab(event, 'tab2')">Pop-Up</button>
        <button class="tab-btn" onclick="switchTab(event, 'tab3')">List</button>
        <button class="tab-btn" onclick="switchTab(event, 'tab4')">Entry Screen</button>
        <button class="tab-btn" onclick="switchTab(event, 'tab5')">Node Js(Preview and Print)</button>
        <button class="tab-btn" onclick="switchTab(event, 'tab6')">Excel upload and download</button>
        <button class="tab-btn" onclick="switchTab(event, 'tab7')">Tab 7</button>
    </div>

    <!-- Tab 1 Content -->
    <jsp:include page="tab1.jsp"/>

    <!-- Tab 2 Content -->
    <jsp:include page="tab2.jsp"/>

    <!-- Tab 3 -->
    <jsp:include page="tab3.jsp"/>

    <!-- Tab 4 -->
    <jsp:include page="tab4.jsp"/>

    <!-- Tab 5 -->
    <jsp:include page="tab5.jsp"/>


    <!-- ================= TAB 6 ================= -->
    <div id="tab6" class="tab-content">

        <jsp:include page="tab6.jsp"/>

    </div>

    <div id="tab7" class="tab-content">
        <jsp:include page="tab7.jsp"/>
    </div>

    <!-- Popup Modal (Hidden by default) -->
    <div id="popupOverlay" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
        <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: white; padding: 30px; width: 700px; border-radius: 5px; max-height: 90vh; overflow-y: auto;">

            <!-- Popup Header -->
            <h2 style="margin-top: 0; color: #333;">Patient Details</h2>

            <!-- Patient Info (2 columns) -->
            <div style="display: flex; gap: 40px; margin-bottom: 20px;">
                <div>
                    <p><strong>Name</strong> : CT SCAN</p>
                    <p><strong>MRN</strong> : CYTOLOGY</p>
                    <p><strong>Date Of Birth</strong> : DIGITAL X-RAY</p>
                    <p><strong>Age</strong> : FLUID EXAMINATION</p>
                </div>
                <div>
                    <p><strong>Gender</strong> : GASTROENTEROLOGY INVESTIGATION</p>
                    <p><strong>Address</strong> : HAEMATOLOGY</p>
                    <p><strong>Reg Date</strong> : HARMONES</p>
                    <p><strong>Status</strong> : HISTOPATHOLOGY</p>
                </div>
            </div>

            <!-- Text Editor Label -->
            <h3 style="margin-top: 25px;">Text Editor</h3>

            <!-- Text Editor -->
            <textarea id="editorContent" style="width: 100%; height: 200px; padding: 10px; border: 1px solid #ccc; font-family: Arial; font-size: 14px;"></textarea>

            <!-- Close Button -->
            <button onclick="closePopup()" style="margin-top: 15px; padding: 8px 20px; background-color: #999; color: white; border: none; cursor: pointer;">Close</button>
        </div>
    </div>

</div>

<!-- JavaScript for Tab Switching -->
<script>
    function switchTab(event, tabId) {
        // Remove active class from all buttons
        var buttons = document.querySelectorAll('.tab-btn');
        buttons.forEach(function (btn) {
            btn.classList.remove('active');
        });

        // Remove active class from all content
        var contents = document.querySelectorAll('.tab-content');
        contents.forEach(function (content) {
            content.classList.remove('active');
        });

        // Add active class to clicked button
        event.target.classList.add('active');

        // Show the tab content
        document.getElementById(tabId).classList.add('active');
    }
    
    // Download Excel file
    function downloadExcel() {
        // Trigger download by making a GET request to the Excel endpoint
        window.location.href = '${pageContext.request.contextPath}/excel/download';
    }

    // Download Excel template file
    function downloadTemplate() {
        // Trigger template download by making a GET request to the template endpoint
        window.location.href = '${pageContext.request.contextPath}/excel/template';
    }
    
    // for popup btn
    function openPopup() {
        document.getElementById('popupOverlay').style.display = 'block';
    }

    function closePopup() {
        document.getElementById('popupOverlay').style.display = 'none';
    }

    // Client-side filtering (combined: name + code + other filters)
    document.addEventListener('DOMContentLoaded', function() {
        var tbody = document.querySelector('#staffTable tbody');
        var totalSpan = document.getElementById('totalRecords');
        var currentPageInput = document.getElementById('currentPage');
        var totalPagesSpan = document.getElementById('totalPages');

        if (tbody && totalSpan) {
            var initialCount = tbody.querySelectorAll('tr').length;
            totalSpan.textContent = initialCount;
            if (totalPagesSpan) totalPagesSpan.textContent = 1;
            if (currentPageInput) currentPageInput.value = 1;
        }

        var inputs = {
            name: document.getElementById('searchName'),
            code: document.getElementById('searchCode'),
            userType: document.getElementById('filterUserType'),
            phone: document.getElementById('filterPhone'),
            department: document.getElementById('filterDepartment'),
            status: document.getElementById('filterStatus')
        };

        function applyFilters() {
            if (!tbody) return;
            var nameFilter = inputs.name ? inputs.name.value.trim().toLowerCase() : '';
            var codeFilter = inputs.code ? inputs.code.value.trim().toLowerCase() : '';
            var userTypeFilter = inputs.userType ? inputs.userType.value.trim().toLowerCase() : '';
            var phoneFilter = inputs.phone ? inputs.phone.value.trim().toLowerCase() : '';
            var departmentFilter = inputs.department ? inputs.department.value.trim().toLowerCase() : '';
            var statusFilter = inputs.status ? inputs.status.value.trim().toLowerCase() : '';

            var rows = tbody.querySelectorAll('tr');
            var visible = 0;
            rows.forEach(function(r){
                var cells = r.cells;
                var nameCell = cells[0] ? cells[0].textContent.trim().toLowerCase() : '';
                var codeCell = cells[1] ? cells[1].textContent.trim().toLowerCase() : '';
                var userTypeCell = cells[2] ? cells[2].textContent.trim().toLowerCase() : '';
                var phoneCell = cells[3] ? cells[3].textContent.trim().toLowerCase() : '';
                var departmentCell = cells[4] ? cells[4].textContent.trim().toLowerCase() : '';
                var statusCell = cells[5] ? cells[5].textContent.trim().toLowerCase() : '';

                var match = true;
                if (nameFilter && nameCell.indexOf(nameFilter) === -1) match = false;
                if (codeFilter && codeCell.indexOf(codeFilter) === -1) match = false;
                if (userTypeFilter && userTypeCell !== userTypeFilter) match = false;
                if (phoneFilter && phoneCell.indexOf(phoneFilter) === -1) match = false;
                if (departmentFilter && departmentCell.indexOf(departmentFilter) === -1) match = false;
                if (statusFilter && statusCell.indexOf(statusFilter) === -1) match = false;

                if (match) {
                    r.style.display = '';
                    visible++;
                } else {
                    r.style.display = 'none';
                }
            });

            totalSpan.textContent = visible;
            if (currentPageInput) currentPageInput.value = 1;
        }

        // Attach listeners to relevant inputs
        if (inputs.name) inputs.name.addEventListener('input', applyFilters);
        if (inputs.code) inputs.code.addEventListener('input', applyFilters);
        if (inputs.userType) inputs.userType.addEventListener('change', applyFilters);
        if (inputs.phone) inputs.phone.addEventListener('input', applyFilters);
        if (inputs.department) inputs.department.addEventListener('change', applyFilters);
        if (inputs.status) inputs.status.addEventListener('change', applyFilters);
    });

</script>

</body>

</html>