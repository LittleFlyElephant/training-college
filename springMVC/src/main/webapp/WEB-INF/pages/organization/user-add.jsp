<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="机构-添加非会员" act="10" page="org">
    <stripes:layout-component name="content">
        <c:if test="${msgType != null}">
            <div class="alert alert-${msgType} fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4>${msg}</h4>
            </div>
        </c:if>
        <h3>添加非会员:
            <a href="/organization/course/${cid}/2" type="button" class="btn btn-default">返回</a>
        </h3>
        <hr>
        <div class="col-md-6">
            <form:form action="/organization/user/add/${cid}" method="post" commandName="user" role="form">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="输入用户名："/>
                </div>
                <div class="form-group">
                    <label for="cid">课程编号</label>
                    <input type="text" class="form-control" disabled id="cid" name="cid" placeholder="${cid}"/>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default btn-success form-control">添加用户</button>
                </div>
            </form:form>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="scripts">
        <script>
            $(document).ready(function () {
                window.setTimeout(function () {
                    $('[data-dismiss="alert"]').alert('close');
                }, 2000);
            });
        </script>
    </stripes:layout-component>
</stripes:layout-render>
