<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Tab 1 Content -->
<div id="tab1" class="tab-content active">
    <div style="padding: 20px;">
        <!-- Department Dropdown -->
        <div style="margin-bottom: 20px;">
            <label style="display: block; margin-bottom: 8px; font-weight: bold;">DEPARTMENT</label>
            <select id="departmentSelect" style="width: 300px; padding: 8px; font-size: 14px;">
                <option value="">-- Select Department --</option>
                <option value="1">Finance</option>
                <option value="2">IT</option>
                <option value="3">HR</option>
                <option value="4">Operations</option>
            </select>
        </div>

        <!-- Static Value Dropdown -->
        <div style="margin-bottom: 20px;">
            <label style="display: block; margin-bottom: 8px; font-weight: bold;">STATIC VALUE</label>
            <select id="staticValueSelect" style="width: 300px; padding: 8px; font-size: 14px;">
                <option value="">-- Select Value --</option>
                <option value="A">Value A</option>
                <option value="B">Value B</option>
                <option value="C">Value C</option>
                <option value="D">Value D</option>
            </select>
        </div>
    </div>
</div>
