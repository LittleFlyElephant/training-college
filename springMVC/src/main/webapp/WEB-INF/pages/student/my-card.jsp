<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="学生-我的会员卡" act="3" page="std">
    <stripes:layout-component name="content">
        <div class="row">
            <c:if test="${msgType != null}">
                <div class="alert alert-${msgType} fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4>${msg}</h4>
                </div>
            </c:if>
            <div class="col-md-12">
                <h3>我的会员卡
                    <c:choose>
                        <c:when test="${user.cardState == 0}">
                            <span class="label label-primary">未激活</span>
                        </c:when>
                        <c:when test="${user.cardState == 1}">
                            <span class="label label-success">正常</span>
                        </c:when>
                        <c:when test="${user.cardState == -1}">
                            <span class="label label-warning">余额不足，会员资格暂停(一年内未充值则停止)</span>
                        </c:when>
                        <c:when test="${user.cardState == -2}">
                            <span class="label label-danger">停止，请去激活！</span>
                        </c:when>
                        <c:otherwise>
                            <span class="label label-default">已注销</span>
                        </c:otherwise>
                    </c:choose>
                </h3>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-2">
                <p><strong>会员卡ID</strong></p>
                <p><strong>会员卡余额</strong></p>
                <p><strong>总消费</strong></p>
                <p><strong>会员卡积分</strong></p>
                <p><strong>会员卡等级</strong></p>
            </div>
            <div class="col-md-10">
                <p>${user.cardId}</p>
                <p class="text-danger">${user.cardBalance}</p>
                <p class="text-danger">${user.cardConsume}</p>
                <p class="text-info">${user.cardScore}</p>
                <p class="text-success">${user.cardLevel}
                    <c:if test="${user.cardLevel>0}">
                        <span class="bg-danger">可享受打${10-user.cardLevel*0.5}折优惠</span>
                    </c:if>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-2">
                <p><strong>积分兑换</strong></p>
            </div>
            <div class="col-md-4">
                <form:form action="/student/card/change" method="post">
                    <div class="input-group">
                        <input name="score" type="text" class="form-control" placeholder="输入你想要兑换的积分">
                        <span class="input-group-btn">
                                <button class="btn btn-info" type="submit">兑换</button>
                        </span>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="row" style="margin-top: 20px">
            <div class="col-md-2">
                <c:choose>
                    <c:when test="${user.cardState == 0}">
                        <a href="/student/charge" type="button" class="btn btn-default btn-primary">去激活</a>
                    </c:when>
                    <c:when test="${user.cardState == 1}">
                        <a href="/student/charge" type="button" class="btn btn-default btn-success">去充值</a>
                    </c:when>
                    <c:when test="${user.cardState == -1}">
                        <a href="/student/charge" type="button" class="btn btn-default btn-warning">去充值</a>
                    </c:when>
                    <c:when test="${user.cardState == -2}">
                        <a href="/student/charge" type="button" class="btn btn-default btn-danger">去激活</a>
                    </c:when>
                    <c:otherwise>
                        <button type="button" disabled class="btn btn-default btn-danger">已注销</button>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-8">
                <form:form action="/student/card/invalid" method="post">
                    <c:if test="${user.cardState != -3}">
                        <button type="submit" class="btn btn-default">注销会员卡</button>
                    </c:if>
                    <c:if test="${user.cardState == -3}">
                        <button type="submit" class="btn btn-default btn-success">激活</button>
                    </c:if>
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
