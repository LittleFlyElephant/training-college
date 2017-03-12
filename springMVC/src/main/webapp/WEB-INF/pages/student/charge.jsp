<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="学生-充值会员卡" act="3" page="std">
    <stripes:layout-component name="content">
        <h1>我的会员卡: </h1>
        <hr>
        <form:form action="/student/handle_charge" method="post" commandName="userCharge" role="form">
            <div class="form-group">
                <label for="firstName">卡余额:</label>
                <input type="text" class="form-control" id="nickname" name="nickname" placeholder="Enter Nickname:"
                       value="${user.cardBalance}"/>
            </div>
            <div class="form-group">
                <label for="firstName">持有人:</label>
                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Enter FirstName:"
                       value="${user.userName}"/>
            </div>
            <div class="form-group">
                <label for="money">充值金额:</label>
                <input type="text" class="form-control" id="money" name="money" placeholder="Enter Price:"/>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-sm btn-success">充值</button>
            </div>
        </form:form>
    </stripes:layout-component>
</stripes:layout-render>
