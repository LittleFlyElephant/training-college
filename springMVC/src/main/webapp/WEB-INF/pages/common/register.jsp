<%--
  Created by IntelliJ IDEA.
  User: raychen
  Date: 2017/2/27
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="注册" page="common">
    <stripes:layout-component name="content">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <c:if test="${msgType != null}">
                    <div class="alert alert-${msgType} fade in" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4>${msg}</h4>
                    </div>
                </c:if>
                <h1 style="text-align: center;margin-top: 50px">注册</h1>
                <form:form action="/register" method="post" commandName="user" role="form">
                    <div class="form-group">
                        <label for="userName">用户名:</label>
                        <input type="text" class="form-control" id="userName" name="username" placeholder="输入用户名："/>
                    </div>
                    <div class="form-group">
                        <label for="password">密码:</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="输入密码："/>
                    </div>
                    <div class="form-group">
                        <label for="re-password">再次密码:</label>
                        <input type="password" class="form-control" id="re-password" name="re-password" placeholder="再次输入密码："/>
                    </div>
                    <div class="form-group">
                        <label for="phone">手机:</label>
                        <input type="text" class="form-control" id="phone" name="phone" placeholder="输入手机："/>
                    </div>
                    <div class="form-group">
                        <label for="account">银行账户:</label>
                        <input type="text" class="form-control" id="account" name="account" placeholder="输入银行账户："/>
                    </div>
                    <div class="form-group">
                        <label for="type">注册为</label>
                        <select id="type" class="form-control" name="type">
                            <option value="1">学员</option>
                            <option value="2">机构</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn form-control btn-success">注册</button>
                    </div>
                </form:form>
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