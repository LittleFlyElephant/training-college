<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<stripes:layout-render name="/WEB-INF/pages/layout/layout.jsp" pageTitle="平台-统计信息" act="4" page="plat">
    <stripes:layout-component name="content">
        <div class="row">
            <div class="col-md-12">
                <h3>机构招生情况统计 <small>平台机构数量:${num}</small></h3>
                <hr>
                <div id="org-bar" style="width: 600px;height:400px;">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h3>学员学习情况统计 <small>完成课程率:${per}%</small></h3>
                <hr>
                <div id="study-bar" style="width: 600px;height:400px;">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h3>平台收入情况统计 <small>累计收入:${sum}</small></h3>
                <hr>
                <div id="charge-line" style="width: 600px;height:400px;">
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
            //机构统计
            var chart2 = echarts.init(document.getElementById('org-bar'));
            var orgs = ${orgs};
            var nums = ${nums};
            var option2 = {
                color: ['#3398DB'],
                title: {
                    text: '各个机构招生人数'
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
                        name:'人数',
                        type:'bar',
                        barWidth: '60%',
                        data: nums
                    }
                ]
            };
            chart2.setOption(option2);
            //学习统计
            var chart1 = echarts.init(document.getElementById('study-bar'));
            var courses = ${courses};
            var values = ${scores};
            var option1 = {
                color: ['#3398DB'],
                title: {
                    text: '课程平均分'
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
                        data : courses,
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
                        name:'平均分',
                        type:'bar',
                        barWidth: '60%',
                        data: values
                    }
                ]
            };
            chart1.setOption(option1);
            //收入统计
            var chart3 = echarts.init(document.getElementById('charge-line'));
            var chargex = ${charge_dates};
            var chargey = ${charge_moneys};
            var option3 = {
                title: {
                    text: '累计收入变化'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['收入金额']
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
                        name:'收入金额',
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
