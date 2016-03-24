<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>工资报表详情</title>
    <link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
    <script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">主播管理</a></li>
        <li><a href="#">工资报表详情</a></li>
    </ul>
</div>
<div class="formbody">
    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
        <form action="salaryReport.html?pageIndex=${pageIndex! }&roomId=${roomId!}&mobile=${mobile!}&startTime=${(startTime?date)! }&endTime=${(endTime?date)}&month=${month!}" method="post" name="form1" id="form1">
            <ul class="seachform">
                <li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="返回报表" onclick="form1.submit();"/></li>
                <input name="" type="button" class="scbtn" value="导出" onclick="exportData()"/>

            </ul>
            <table class="tablelist">
                <thead>
                <tr>
                    <th>日期</th>
                    <th>排名</th>
                    <th>积分</th>
                    <th>房间ID</th>
                    <th>手机号</th>
                    <th>有效直播时长</th>
                    <th>薪资阶段</th>
                    <th>应得</th>
                    <th>奖金</th>
                    <th>罚金</th>
                    <th>实得</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list list?if_exists as item>
                <tr title="${item.roomName! }">
                    <td>${(item.day?string("yyyy-MM-dd"))!}</td>
                    <td>${item.ranking!}</td>
                    <td>${item.score! }</td>
                    <td>${item.roomId! }</td>
                    <td>${item.mobile! }</td>
                    <td>${item.playTime! }</td>
                    <td>${item.payStandard! }元/小时</td>
                    <td>${item.salary! }</td>
                    <td>${item.bonus!}</td>
                    <td>${item.forfeit! }</td>
                    <td>${item.lastSalary! }</td>
                    <td>${item.remark! }</td>
                    <td>
                        <a href="salaryReport-detail-edit.html?pageIndex=${pageIndex! }&roomId=${roomId!}&id=${id!}&rankingId=${item.rankingId! }&mobile=${mobile!}&startTime=${(startTime?date)! }&endTime=${(endTime?date)}&month=${month!}" class="tablelink">修改</a>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
    $('.tablelist tbody tr:odd').addClass('odd');

    function exportData(){
        location.href='salaryReport-detailExport.html?id=${id!}&startTime=${(startTime?date)!}&endTime=${(endTime?date)}';
    }
</script>
</body>
</html>