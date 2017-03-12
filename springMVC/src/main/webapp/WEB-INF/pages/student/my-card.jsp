<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="学生-我的会员卡" act="3" page="std">
    <stripes:layout-component name="content">
        <h1>我的会员卡: </h1>
        <form:form action="/student/card/invalid" method="post">
            <c:if test="${user.cardState == 1}">
                <button type="submit" class="btn btn-warning">注销会员卡</button>
            </c:if>

            <c:if test="${user.cardState == -3}">
                <button type="submit" class="btn btn-success">激活</button>
            </c:if>
        </form:form>
        <hr>
        <table class="table table-bordered table-striped">
            <tr>
                <th>会员卡 ID</th>
                <td>${user.cardId}</td>
            </tr>
            <tr>
                <th>会员卡余额</th>
                <td>${user.cardBalance}</td>
            </tr>
            <tr>
                <th>会员卡积分</th>
                <td>
                    ${user.cardScore}
                    <form:form action="/student/card/change" method="post">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <button class="btn btn-success" type="submit">兑换</button>
                            </span>
                            <input name="score" type="text" class="form-control" placeholder="enter number:">
                        </div>
                    </form:form>
                </td>
            </tr>
            <tr>
                <th>会员卡状态</th>
                <td>
                    <c:choose>
                        <c:when test="${user.cardState == 0}">
                            未激活
                            <a href="/student/charge" type="button" class="btn btn-sm btn-primary">去激活</a>
                        </c:when>
                        <c:when test="${user.cardState == 1}">
                            正常
                            <a href="/student/charge" type="button" class="btn btn-sm btn-success">去充值</a>
                        </c:when>
                        <c:when test="${user.cardState == -1}">
                            余额不足，会员资格暂停(一年内未充值则停止)
                            <a href="/student/charge" type="button" class="btn btn-sm btn-warning">去充值</a>
                        </c:when>
                        <c:when test="${user.cardState == -2}">
                            停止，请去激活！
                            <a href="/student/charge" type="button" class="btn btn-sm btn-danger">去激活</a>
                        </c:when>
                        <c:otherwise>
                            已注销
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </stripes:layout-component>
</stripes:layout-render>
