<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>精彩活动</title>
    <link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
    <script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
    <script type="text/javascript">
        <#-- 全选 -->
        function checkAll() {
            var b = $("input[name='allIds']").attr('checked');
            $("input[name='ids']").attr('checked', b == "checked");
        }

        <#-- 删除 -->
        function doDelete(id) {
            if (confirm("确定需要删除？")) {
                location.href = 'activity-push-delete.html?pageIndex=${pageIndex! }&title=${(title)! }&ids=' + id;
            }
        }

        function deleteAll() {
            var i = $("input[type='checkbox'][name='ids']:checked").length;
            if (i <= 0) {
                addFieldError("请先选择需要删除的数据");
            } else {
                if (confirm("确定需要删除？")) {
                    var form1 = document.getElementById("form1");
                    form1.action = "activity-push-delete.html?pageIndex=${pageIndex! }";
                    form1.submit();
                }
            }
        }
    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a></li>
        <li><a href="#">活动</a></li>
        <li><a href="#">活动管理</a></li>
    </ul>
</div>
<div class="formbody">
    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
        <form action="activity-push.html" method="post" name="form1" id="form1">
            <ul class="seachform">
                <li onclick="location.href='activity-push-detail.html?pageIndex=${pageIndex!}'" class=".toolbar li">
                    <span><img src="${staticPath! }/static/images/t01.png${staticVersion! }"/></span>新增
                </li>

                <li>
                    <label>活动名称：</label><input name="title" type="text" value="${title! }" class="scinput-150"/>
                </li>
                <li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询"
                                                onclick="form1.submit();"/></li>
            </ul>
            <table class="tablelist">
                <thead>
                <tr>
                    <th>活动标题</th>
                    <th>图片</th>
                    <th>链接</th>
                    <th>排序号</th>
                    <th>创建时间</th>
                    <th>remark</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list list?if_exists as item>
                <tr title="${(item.title)! }">
                    <td>${(item.title)! }</td>
                    <td>
                        <#if item.indexImg?default("") != "">
                            <a href="#" onclick="javascript:window.open('${uploadPath! }${item.indexImg!}');">
                                <img width="30px" height="30px" src="${uploadPath! }${item.indexImg! }"/>
                            </a>
                        </#if>
                    </td>
                    <td><a href="${(item.activityUrl)! }" target="_blank ">${(item.activityUrl)! }</td>
                    <td>${(item.orderId)! }</td>
                    <td>${(item.createTime?datetime)! }</td>
                    <td>${(item.remark)! }</td>


                    <td>
                        <a href="activity-push-detail.html?pageIndex=${pageIndex! }&id=${(item.id)! }"
                           class="tablelink">修改</a> |
                        <a href="javascript:doDelete('${(item.id)! }')" class="tablelink">删除</a>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </form>
        <div class="pagin">
        <@plugins.page action="activity-push.html?title=${title! }&pageIndex=" />
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
    $('.tablelist tbody tr:odd').addClass('odd');
</script>
</body>
</html>