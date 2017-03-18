<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="学生-统计信息" act="2" page="std">
    <stripes:layout-component name="content">
        <div class="row">
            <div class="col-md-12">
                <h3>课程统计</h3>
                <hr>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <p><strong>预订课程数量</strong></p>
                <p><strong>退订课程数量</strong></p>
                <p><strong>退课数量</strong></p>
                <p><strong>完成课程数量</strong></p>
            </div>
            <div class="col-md-8">
                <p class="text-info">${booked}</p>
                <p class="text-warning">${unbooked}</p>
                <p class="text-danger">${quited}</p>
                <p class="text-success">${finished}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h3>学习情况统计 <small>平均成绩:${avg}</small></h3>
                <hr>
                <div id="study-bar" style="width: 600px;height:400px;">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h3>充值情况统计 <small>累计充值:${charge_sum}</small></h3>
                <hr>
                <div id="charge-line" style="width: 600px;height:400px;">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h3>消费情况统计 <small>累计消费:${sum}</small></h3>
                <hr>
                <div id="consume-line" style="width: 600px;height:400px;">
                </div>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="scripts">
        <script src="/js/echarts.min.js"></script>
        <script>
            $(document).ready(function(){
                window.setTimeout(function(){
                    $('[data-dismiss="alert"]').alert('close');
                },2000);
            });
            //学习统计
            var chart1 = echarts.init(document.getElementById('study-bar'));
            var orgs = ${courses};
            var values = ${scores};
            var option1 = {
                color: ['#3398DB'],
                title: {
                    text: '各门课程成绩'
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        data : orgs,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'成绩',
                        type:'bar',
                        barWidth: '60%',
                        data: values
                    }
                ]
            };
            chart1.setOption(option1);
            //消费统计
            var chart2 = echarts.init(document.getElementById('consume-line'));
            var xdata = ${dates};
            var ydata = ${moneys};
            var option2 = {
                title: {
                    text: '累计消费变化'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['消费金额']
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : xdata
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'消费金额',
                        type:'line',
                        stack: '总量',
                        areaStyle: {normal: {}},
                        data: ydata
                    }
                ]
            };
            chart2.setOption(option2);
            //充值统计
            var chart3 = echarts.init(document.getElementById('charge-line'));
            var chargex = ${charge_dates};
            var chargey = ${charge_moneys};
            var option3 = {
                title: {
                    text: '累计充值变化'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['充值金额']
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : chargex
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'充值金额',
                        type:'line',
                        stack: '总量',
                        areaStyle: {normal: {}},
                        data: chargey
                    }
                ]
            };
            chart3.setOption(option3);

        </script>
    </stripes:layout-component>
</stripes:layout-render>
