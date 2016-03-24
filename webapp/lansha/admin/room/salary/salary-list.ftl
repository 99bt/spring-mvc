<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>工资报表</title>
    <link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
    <link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
    <script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
    <script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>

<@plugins.msg />
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">主播管理</a></li>
        <li><a href="#">工资报表</a></li>
    </ul>
</div>
<div class="formbody">
    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
        <form action="salaryReport.html" method="post" name="form1" id="form1">
            <ul class="seachform">
                <li>
                    <label>房间ID：</label><input name="roomId" type="text" value="${roomId! }" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  autocomplete="off" />
                </li>

                <li>
                    <label>手机号：</label><input type="text" name="mobile" value="${mobile! }"  class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  autocomplete="off" />
                </li>
                <li>
                    <label>周期：</label>
                    <div class="vocation" style="width:150px;">
                        <select class="select3" name="month" >
                            <option value="1" <#if month==1>selected</#if>>1</option>
                            <option value="2" <#if month==2>selected</#if>>2</option>
                            <option value="3" <#if month==3>selected</#if>>3</option>
                            <option value="4" <#if month==4>selected</#if>>4</option>
                            <option value="5" <#if month==5>selected</#if>>5</option>
                            <option value="6" <#if month==6>selected</#if>>6</option>
                            <option value="7" <#if month==7>selected</#if>>7</option>
                            <option value="8" <#if month==8>selected</#if>>8</option>
                            <option value="9" <#if month==9>selected</#if>>9</option>
                            <option value="10" <#if month==10>selected</#if>>10</option>
                            <option value="11" <#if month==11>selected</#if>>11</option>
                            <option value="12" <#if month==12>selected</#if>>12</option>

                        </select>
                    </div>
                </li>

                <li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
                <input name="" type="button" class="scbtn" value="导出" onclick="exportData()"/>
            </ul>
            <table class="tablelist">
                <thead>
                <tr>
                    <th>日期</th>
                    <th>房间ID</th>
                    <th>手机号</th>
                    <th>有效直播时长</th>
                    <th>应得</th>
                    <th>奖金</th>
                    <th>罚金</th>
                    <th>实得</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list list?if_exists as item>
                <tr title="${item.roomName! }">
                    <td>${timeFrame!}</td>
                    <td>${item.roomId! }</td>
                    <td>${item.mobile! }</td>
                    <td>${item.playTime! }</td>
                    <td>${item.salary! }</td>
                    <td>${item.bonus!}</td>
                    <td>${item.forfeit! }</td>
                    <td>${item.lastSalary! }</td>


                    <td>
                        <a href="salaryReport-detail.html?pageIndex=${pageIndex! }&roomId=${roomId!}&id=${item.roomId! }&mobile=${mobile!}&startTime=${(startTime?date)! }&endTime=${(endTime?date)}&month=${month!}" class="tablelink">详情</a>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </form>
        <div class="pagin">
        <@plugins.page action="salaryReport.html?mobile=${mobile!}&startTime=${(startTime?date)! }&endTime=${(endTime?date)}&month=${month!}&roomId=${roomId! }&pageIndex=" />
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
    $('.tablelist tbody tr:odd').addClass('odd');
    $(document).ready(function() {
        $(".select3").uedSelect({
            width : 150
        });

        $("#up_list_one").click(function(){
            $("#sort").val("one")
            form1.submit();
        });
        $("#up_list_two").click(function(){
            $("#sort").val("two")
            form1.submit();
        });
        $("#up_list_three").click(function(){
            $("#sort").val("three")
            form1.submit();
        });
        $("#up_list_four").click(function(){
            $("#sort").val("four")
            form1.submit();
        });
        $("#up_list_five").click(function(){
            $("#sort").val("five")
            form1.submit();
        });
        $("#up_list_six").click(function(){
            $("#sort").val("six");
            form1.submit();
        });

    });
    function exportData(){
        location.href='salaryReport-listExport.html?roomId=${roomId! }&&mobile=${mobile!}&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&month=${month!}';
    }
</script>
</body>
</html>