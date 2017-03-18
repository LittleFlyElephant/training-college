<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="学生-充值会员卡" act="3" page="std">
    <stripes:layout-component name="content">
        <h3>会员卡充值
            <a href="/student/card" type="button" class="btn btn-default">返回</a>
        </h3>
        <hr>
        <form:form cssClass="form-horizontal" action="/student/handle_charge" method="post" commandName="userCharge" role="form">
            <div class="form-group">
                <label for="balance" class="col-md-2 control-label">卡余额:</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="balance" disabled value="${user.cardBalance}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="user" class="col-md-2 control-label">持有人:</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="user" disabled value="${user.userName}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="money" class="col-md-2 control-label">充值金额:</label>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="money" name="money" placeholder="输入充值金额:"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default btn-success">充值</button>
                </div>
            </div>
        </form:form>
    </stripes:layout-component>
</stripes:layout-render>
