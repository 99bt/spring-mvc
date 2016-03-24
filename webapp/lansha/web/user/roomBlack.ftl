<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/style.css${staticVersion! }">
</head>
<body>
<@lansha.head index="1" showdiv="1"/>

<div class="container">
    <div class="layout  help-box user-center clearfix">
    	<@lansha.userLeft index="5"/>

    	<div class="help-right fr">
		 <h3 class="history-title">
                <span>我的直播间</span>
            </h3>
			<div class="user-con myroom-con clearfix">
                <div class="user-conTitle clearfix">
                    <a class="fl" href="myroom.html">房间设置</a>
                    <a class="fl" href="manage.html">房间管理员</a>
                    <a class="fl cur" href="#">禁言名单</a>
                </div>
           
                <form id="frmModRoomInfo" action="api/api.php?act=modifyroominfo">
                    <div class="clearfix myroom-box">
                        <p class="p-big">添加用户禁言</p>
                        <div class="clearfix">
                            <p class="p-1 fl">用户昵称：</p>
                            <input type="text" class="fl input-2" placeholder="请输入用户名称">
                            <a href="#" class="fl add-btn-2">添加</a>
                        </div>
                        <#if blackList??>
                        <ul class="list-2 clearfix">
                        <#list blackList as user>
                             <li>
                                <span class="delManager remove-btn" data-uid="${user.userId! }">x</span>
                                <input type="hidden" id="nickname${user.userId! }"  value="${user.userNickname! }" />
                                <img src="${(user.userHeadpic)!}" onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'"  alt="${user.userNickname! }" data-uid="1">
                                <p title="${user.userNickname!user.userId}">${user.userNickname!user.userId}</p>
                            </li>
                         </#list>
                        </ul> 
                        </#if>
                    </div>
                </form>
        </div>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script>
var BLACK_SAVE_API = '${contextPath! }/user/black-save.html';
var BLACK_DEL_API = '${contextPath! }/user/black-del.html';
$(function(){
    new LSUser.modRoomInfo().init();
    new LSUser.RoomBlack(BLACK_SAVE_API,BLACK_DEL_API).init();
});
</script>
</body>
</html>