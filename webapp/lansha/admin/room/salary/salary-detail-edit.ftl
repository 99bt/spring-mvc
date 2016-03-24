<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>房间管理</title>
    <link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
    <link href="${staticPath! }/static/css/baidu.css" rel="stylesheet">
    <script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
    <script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
    <script>
        <#-- 保存 -->
        function save(){

            //表单提交
            $("#form1").submit();
        }
    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">主播管理</a></li>
        <li><a href="#">工资管理</a></li>
    </ul>
</div>
<div class="formbody">
    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
        <form action="salaryReport-detail-update.html" id="form1" method="post">
            <input type="hidden" id="id" name="id" value="${id!}" />
            <input type="hidden" name="pageIndex" value="${pageIndex!}" />
            <input type="hidden" name="ywUserRoomDayData.rankingId" value="${ywUserRoomDayData.rankingId!}" />
            <input type="hidden" name="roomId" value="${roomId!}" />
            <input type="hidden" name="mobile" value="${mobile!}" />
            <input type="hidden" name="startTime" value="${(startTime?date)! }" />
            <input type="hidden" name="endTime" value="${(endTime?date)! }" />
            <div class="formtitle"><span class="sp">工资信息</span></div>
            <ul class="forminfo">
                <li><label>日期:</label>
                    <input value="${(ywUserRoomDayData.day?string("yyyy-MM-dd"))!}" id="orderId"  type="text" class="dfinput-345" readonly/>
                </li>
                <li><label>房间：</label>
                    <input value="${ywUserRoomDayData.roomId! }"  type="text" class="dfinput-345" readonly/>
                </li>
                <li><label>积分：</label>
                    <input value="${ywUserRoomDayData.score! }"   type="text" class="dfinput-345" readonly/>
                </li>

                <li><label>罚金：</label>
                    <input value="${ywUserRoomDayData.forfeit! }"  name="ywUserRoomDayData.forfeit" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=7/>
                </li>
                <li><label>奖金：</label>
                    <input value="${ywUserRoomDayData.bonus! }"  name="ywUserRoomDayData.bonus" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=7/>
                </li>
                <li><label>备注：</label>
                    <textarea id="intro" name="ywUserRoomDayData.remark" class="textinput" style="width:320px;height:62px;">${ywUserRoomDayData.remark! }</textarea>
                </li>
                <li>
                    <label>&nbsp;</label>
                    <input name="" type="button" class="btn" value="保存" onclick="save()"/>
                    <input name="" type="button" class="btn" value="返回" onclick="location.href='salaryReport-detail.html?pageIndex=${pageIndex! }&roomId=${roomId!}&id=${id! }&mobile=${mobile!}&startTime=${(startTime?date)! }&endTime=${(endTime?date)}&month=${month!}'"/>
                </li>
            </ul>
    </div>
    </form>
</div>
</div>
</body>
<script>
    jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
</script>
</html>
