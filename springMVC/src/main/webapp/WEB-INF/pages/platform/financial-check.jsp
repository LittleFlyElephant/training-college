<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" page="${page}" pageTitle="${pageTitle}" act="${type}">
    <stripes:layout-component name="content">
        <c:if test="${msgType != null}">
            <div class="alert alert-${msgType} fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4>${msg}</h4>
            </div>
        </c:if>
        <h3>财务结算: <small>平台余额:${balance}</small></h3>
        <hr>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" <c:if test="${tab==1}"> class="active" </c:if>><a href="#org" aria-controls="org" role="tab" data-toggle="tab">结算机构财务</a></li>
            <li role="presentation" <c:if test="${tab==2}"> class="active" </c:if>><a href="#std" aria-controls="std" role="tab" data-toggle="tab">退课结算</a></li>
        </ul>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="org">
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>机构ID</th>
                        <th>机构名</th>
                        <th>金额</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${orgs}" var="org">
                        <tr>
                            <td>${org.org.id}</td>
                            <td>${org.org.name}</td>
                            <td>${org.money}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${org.state == 1}">
                                        <button type="button" disabled class="btn btn-success">已结算</button>
                                    </c:when>
                                    <c:when test="${org.state == 0}">
                                        <form:form action="/platform/money/1/${org.id}" method="post">
                                            <input hidden name="money" value="0"/>
                                            <button type="submit" class="btn btn-primary">提交</button>
                                        </form:form>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div role="tabpanel" class="tab-pane" id="std">
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>学生ID</th>
                        <th>姓名</th>
                        <th>金额</th>
                        <th>结算金额</th>
                    </tr>
                    <c:forEach var="i" begin="1" end="${stds.size()}" step="1">
                        <tr>
                            <td>${stds.get(i-1).std.id}</td>
                            <td>${stds.get(i-1).std.name}</td>
                            <td>${stds.get(i-1).money}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${stds.get(i-1).state == 1}">
                                        <button type="button" disabled class="btn btn-success">已结算</button>
                                    </c:when>
                                    <c:when test="${stds.get(i-1).state == 0}">
                                        <form:form cssClass="inl form-inline" action="/platform/money/2/${stds.get(i-1).id}" method="post">
                                            <label for="money">退换金额:</label>
                                            <input type="number" class="form-control" id="money" name="money" value="${pay.get(i-1)}"/>
                                            <button type="submit" class="btn btn-primary">提交</button>
                                        </form:form>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="scripts">
        <script>
            $(document).ready(function(){
                window.setTimeout(function(){
                    $('[data-dismiss="alert"]').alert('close');
                },2000);
            });
        </script>
    </stripes:layout-component>
</stripes:layout-render>
