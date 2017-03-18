<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="${pageTitle}" act="10" page="${page}">
    <stripes:layout-component name="content">
        <c:if test="${msgType != null}">
            <div class="alert alert-${msgType} fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4>${msg}</h4>
            </div>
        </c:if>
        <h3>个人信息 </h3>
        <c:choose>
            <c:when test="${page == 'std'}">
                <c:set var="url_root" value="/student"/>
            </c:when>
            <c:when test="${page == 'org'}">
                <c:set var="url_root" value="/organization"/>
            </c:when>
        </c:choose>
        <hr>
        <form:form cssClass="form-horizontal" action="${url_root}/info" method="post" commandName="userCharge" role="form">
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">用户名:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="username" name="username" placeholder="输入用户名:"
                       value= <c:if test="${page==\"org\"}">${user.orgName}</c:if>
                              <c:if test="${page==\"std\"}">${user.userName}</c:if>
                    />
                </div>
            </div>
            <div class="form-group">
                <label for="cid" class="col-sm-2 control-label">ID:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="cid" name="cid" placeholder="输入电话:"
                           value="${user.cardId}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">姓名:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="name" name="name" placeholder="输入姓名:"
                       value="${user.name}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="phone" class="col-sm-2 control-label">电话:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="phone" name="phone" placeholder="输入电话:"
                       value="${user.phone}"/>
                </div>
            </div>
            <c:if test="${page == \"std\"}">
                <div class="form-group">
                    <label for="account" class="col-sm-2 control-label">银行账户:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="account" name="account" placeholder="输入银行账户:"
                           value="${user.email}"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${page == \"org\"}">
                <div class="form-group">
                    <label for="money" class="col-sm-2 control-label">账户余额:</label>
                    <div class="col-sm-10">
                        <input type="number" class="form-control" disabled id="money" name="money" placeholder="${user.cardBalance}"/>
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default btn-success">修改</button>
                </div>
            </div>
        </form:form>
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
