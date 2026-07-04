<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:20px;">

    <h2>Prefix Master (Web Service)</h2>

    <table style="margin-bottom:15px;">
        <tr>
            <td>Prefix</td>
            <td>
                <input type="text" id="prefix" style="width:220px;">
            </td>
        </tr>

        <tr>
            <td>Gender</td>
            <td>
                <select id="gender" style="width:225px;">
                    <option value="">Select Gender</option>
                    <option>Male</option>
                    <option>Female</option>
                    <option>Other</option>
                </select>
            </td>
        </tr>

        <tr>
            <td>Prefix Of</td>
            <td>
                <input type="text" id="prefixOf" style="width:220px;">
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <button id="saveBtn">Save</button>
            </td>
        </tr>
    </table>

    <hr>

    <table border="1" width="100%" cellspacing="0" cellpadding="8">

        <thead>

        <tr>

            <th>ID</th>
            <th>Prefix</th>
            <th>Gender</th>
            <th>Prefix Of</th>
            <th>Action</th>

        </tr>

        </thead>

        <tbody id="prefixTableBody">

        <!-- JavaScript will add rows here -->

        </tbody>

    </table>
    <script>var CTX = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/tab6.js"></script>

</div>