<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- Tab 4 -->
<div id="tab4" class="tab-content">

    <div style="padding:20px;">

        <h3>Prefix Master</h3>

        <%--@elvariable id="prefix" type=""--%>
        <form:form action="${pageContext.request.contextPath}/prefix/save"
                   method="post"
                   modelAttribute="prefix">

            <table style="width:100%;">

                <tr>

                    <td style="width:120px;">
                        <label>Prefix</label>
                    </td>

                    <td>
                        <form:input path="prefix"
                                    style="width:220px;padding:6px;"/>
                    </td>

                </tr>

                <tr>

                    <td>
                        <label>Gender</label>
                    </td>

                    <td>

                        <form:select path="gender"
                                     style="width:235px;padding:6px;">

                            <form:option value="">--Select Gender--</form:option>

                            <form:option value="Male">Male</form:option>

                            <form:option value="Female">Female</form:option>

                            <form:option value="Other">Other</form:option>

                        </form:select>

                    </td>

                </tr>

                <tr>

                    <td>
                        <label>Prefix Of</label>
                    </td>

                    <td>

                        <form:input path="prefixOf"
                                    style="width:220px;padding:6px;"/>

                    </td>

                </tr>

                <tr>

                    <td></td>

                    <td>

                        <button type="submit"
                                style="padding:8px 20px;
                                   background:#0b72d9;
                                   color:white;
                                   border:none;
                                   cursor:pointer;">

                            Save

                        </button>

                    </td>

                </tr>

            </table>

        </form:form>

        <br><br>

        <table border="1"
               cellpadding="8"
               cellspacing="0"
               style="width:100%;border-collapse:collapse;">

            <thead>

            <tr style="background:#efefef;">

                <th>ID</th>

                <th>Prefix</th>

                <th>Gender</th>

                <th>Prefix Of</th>

                <th>Action</th>

            </tr>

            </thead>

            <tbody>

            <c:forEach items="${prefixList}" var="p">

                <tr>

                    <td>${p.id}</td>

                    <td>${p.prefix}</td>

                    <td>${p.gender}</td>

                    <td>${p.prefixOf}</td>

                    <td>

                        <a href="${pageContext.request.contextPath}/prefix/delete/${p.id}"
                           onclick="return confirm('Delete this record?')">

                            Delete

                        </a>

                    </td>

                </tr>

            </c:forEach>

            </tbody>

        </table>

    </div>

</div>