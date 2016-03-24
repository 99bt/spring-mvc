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
                    <a class="fl cur" href="#">房间设置</a>
                    <a class="fl" href="manage.html">房间管理员</a>
                    <a class="fl" href="RoomBlackList.html">禁言名单</a>
                </div>
            <form id="frmModRoomInfo" action="${contextPath! }/user/save-room.html">
                <div class="room-box clearfix">
                    <p class="room-left fl">房间ID号：</p>
                    <p class="room-num fl">${roomNo! }</p>
                    <a class="room-a fl" href="${contextPath! }${appsetting.getLivePath(roomNo)! }" target="_blank">进入直播间</a>
                    <input type="hidden" name="roomNo" value="${roomNo! }" />
                </div>
                <div class="room-box clearfix">
                    <p class="room-left fl">房间名称：</p>
                    <input class="room-name fl" id="roomName" name="roomName" type="text" value="${roomName! }" maxlength="30">
                </div>
                <div class="room-box clearfix">
                    <p class="room-left fl">所属游戏：</p><p class="room-num fl">
                        <select name="gameId">
                        	<option value="">--请选择--</option>
                        	<#list listGame?if_exists as item>
                        		<option value="${item.id! }" <#if item.id == gameId>selected="selected"</#if> >${item.name! }</option>
                        	</#list>
                        </select>
                    </p>
                </div>
                <div class="room-box clearfix">
                    <p class="room-left fl">直播公告：</p>
                    <textarea name="notice" class="textarea" id="roomNote" maxlength="200" placeholder="请输入房间公告">${notice! }</textarea>
                </div>
                <a class="room-modify fl" href="javascript:void(0);" id="saveModRoomInfo">保存</a>
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
    $(function(){
	    new LSUser.modRoomInfo().init();
	});
</script>
</body>
</html>