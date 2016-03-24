<#macro chat anchor=false>
<!--右侧悬浮-->
<div class="rightbox">
	<div class="rightBlock">
		<div class="recommend-code">
			<a href="javascript:;" class="r-close"></a>
			<#if (lanshaAdvertisement.id)?default('') != ''> 
				<a href="${lanshaAdvertisement.link!}" title="${lanshaAdvertisement.name! }" target="_blank">
				<img src="${uploadPath! }${(lanshaAdvertisement.img)!}"></a>
			<#else>
				<img src="${staticPath! }/static/lansha/upload/temp05.jpg${staticVersion! }" width="405" height="110" alt="">
			</#if>
		</div>
		<div class="slide-box">
			<div class="day-ticket clearfix">
				<div class="ticket-num fl">
					日票：<span>${currentTicketNum?default("-") }</span>
				</div>
				<div class="ticket-rank fl">
					排名：<span>${ticketOrder?default("-") }</span>
				</div>
				<div class="ticket-minus fl">
					上一名：<span>${ticketWithPrevious?default("-") }</span>
				</div>
			</div>
		</div>
		<!--聊天窗口-->
		<div class="talk-box">
			<ul id="chat_option" class="chat_option clearfix hide">
				<li class="chat-scroll chat-noscroll clearfix" data-scroll="close">
					<i class="chat-sprite"></i> <span class="chat-text">禁止滚屏</span>
				</li>
				<li class="chat-clear clearfix" data-type="clear">
				<i class="chat-sprite"></i> <span class="chat-text">清屏</span></li>
			</ul>
			<#if (room.online!)?default(0) == 1 && !anchor>
			<!-- 鲜花-->
			<a class="gift_btn" id="giveFlower">
				<i class="gift_flower"></i>
				<span class="f_plus-minus" style="opacity: 0; display: block; top: -8px;"><b>+</b><i>1</i></span>
				<#-- 鲜花数为0且未登录则显示为N,反之显示为0 -->
				<i class="gift_flower_num" id="gift_flower_num" data-num="<#if (flowerStock.stock)?default(0) == 0><#if userIsLogin>0<#else>N</#if><#else>${(flowerStock.stock)! }</#if>">
				<#if (flowerStock.stock)?default(0) == 0>
					<#if userIsLogin>0<#else>N</#if><#else>${(flowerStock.stock)! }</#if></i>
			</a>
			<a class="gift_btn2 <#if (ticketStock.stock)?default(0) == 0>disabled</#if>" id="todayTickets">
				<i class="todayTickets"></i>
				<i class="todayTickets_num" id="todayTickets_num" data-num="<#if (ticketStock.stock)?default(0) == 0><#if userIsLogin>0<#else>N</#if><#else>${(ticketStock.stock)! }</#if>">
				<#if (ticketStock.stock)?default(0) == 0>
					<#if userIsLogin>0<#else>N</#if><#else>${(ticketStock.stock)! }</#if></i>
			</a> </#if>
			<ul class="talk-list" id="chat_list">

			</ul>
		</div>
		<div class="talk-send clearfix">
			<input type="hidden" id="blackRoomsList" value="" /> 
			<#--未登录 textarea增加class:disabled  input未登录增加样式:nologin 登录后移除 增加属性 disabled="disabled"  -->
			<#if userIsLogin>
				<textarea class="send-int01 talk_input fl" id="talk_input" maxlength="50" placeholder="这里输入聊天内容(最长50个字，回车键发送)"></textarea>
				<input class="send-int02 btn_talk_send fl" type="button" id="btn_talk_send" onclick="sendMsg()" />
			<#else>
				<textarea class="send-int01 talk_input fl disabled" id="talk_input" maxlength="50" placeholder="这里输入聊天内容(最长50个字，回车键发送)" data-login="0">未登录，点击登录</textarea>
				<input class="send-int02 btn_talk_send fl nologin" disabled="disabled" type="button" id="btn_talk_send" onclick="sendMsg()" />
			</#if>
			<!--新手指引第一步-->
			<div class="guide-popup popup-first">
				<div class="guide-close"></div>
				<div class="popup-triangle"></div>
				<div class="popup-guide clearfix">
					<span class="guide-num guide-num01 fl"></span>
					<div class="guide-text">
						<h2>登录</h2>
						<p>发弹幕送礼物刷表情参与主播互动</p>
					</div>
				</div>
			</div>
		</div>
		<!--聊天窗口 end-->
	</div>
</div>
<!--房间管理-->
<div class="user_manager" id="itemopen" style="display: none;">
	<div class="user_wrap">
		<input id="operatedUser_name" type="hidden" value=""/ > 
		<input id="operatedUser_id" type="hidden" value=""/ >
		<a id="black_talk" class="jy">禁言</a>
		<a id="user_report"	class="report hide">举报该弹幕</a>
		<a rel="0" id="user_black" class="pb hide">屏蔽该用户</a>
		<#if (role)?default('other') == 'master'>
			<a id="adminsetup" class="rm">管理员任命</a>
		</#if> 
		<a id="manage-cancel" class="manage-cancel">取消</a>
	</div>
</div>
<#-- 页尾js -->
<@lansha.footjs />

<script type="text/javascript">
	var usernickname = "${menberName! }";
	var meRole = '${role! }';
	var meId = '${userId! }';
	function getCatSetting() {
		<#-- 定义参数配置对象 -->
		var settings_object = {
		 	<#-- 正式 -->
			server : "${room.openfirePath! }",
			port : ${room.openfirePort! },
			roomName : "${room.openfireRoom! }",
			conferenceServer : "${room.openfireConference! }",
			username : "",
			password : "",
			<#-- 先拿昵称，没有设置则用用户名，没有则是用匿名 -->
			nickname : usernickname,
			baseNumber : ${room.baseNumber! },
			multipleNumber : ${room.multipleNumber! },
			resource : "xiff2"
    	};
	    return settings_object;
	}
	
	function thisCat() {
		return document["catobj"];
	}
	
    <#--发送聊天CD-->
    function talk_CD(talk_CD_time) {
    	if(typeof(talk_CD_time) == "undefined"){
    		return;
    	}
        if (talk_CD_time <= 0) {
            $("#btn_talk_send").val('').removeClass("nosend").attr("disabled",false);
            $("#talk_input").attr("disabled",false);
            $("#talk_input").focus();
        } else {
            $("#btn_talk_send").val("" + talk_CD_time + "").addClass("nosend").attr("disabled",true);
            $("#talk_input").attr("disabled",true);
            talk_CD_time--;
            setTimeout(function () {
                talk_CD(talk_CD_time);
            }, 1000);
        }
    }
    
    <#--禁言窗口-->
    function popManageMenu(el,username ,uid){
		<#if (role)?default('other') != 'other'>
	        $('#operatedUser_name').val(username);
	        $('#operatedUser_id').val(uid);
	        var X = $(el).offset().top - 30, Y = $(el).offset().left;
	        $(".user_manager").css({"top": X, "left": Y}).show();
	        $(".manage-cancel").on("click", function () {
	            $(".user_manager").hide();
	        });
	   </#if>       
    }
    //关闭管理窗口
    $(".manage-cancel").on("click", function () {
        $(".user_manager").hide();
    });

    <#--禁言-->
    $("#black_talk").on("click",function(){
        $(".user_manager").hide();
        $.xbox.msgBox("确定要禁言用户[" +  $('#operatedUser_name').val() + "]","禁言",null,null,true,function(){
           $.ajax({
            type: 'POST',
            url: '${contextPath! }/live-banned-msg.html?id=${room.id! }&bid=' +  $('#operatedUser_id').val().trim(),
            dataType: 'json',
            success: function (data) {
                $.xbox.closeMsgBox();
                if (data.status == '1') {
                    $.xbox.tips('禁言成功!');
                }else{
                    $.xbox.tips(data.failed);
                }
            }
        });               
        });
    });
    <#--设置管理员-->
    $("#adminsetup").on("click",function(){
        $(".user_manager").hide();
        $.xbox.msgBox("确定要设置用户[" +  $('#operatedUser_name').val() + "]为管理员","设置管理员",null,null,true,function(){
        	$.ajax({
                type: 'POST',
                url: '${contextPath! }/live-add-manager.html?id=${room.id! }&uid=' +  $('#operatedUser_id').val().trim(),
                dataType: 'json',
                success: function (data) {
                    $.xbox.closeMsgBox();
                    if (data.status == '1') {
                        $.xbox.tips('设置成功!');
                    }else{
                        $.xbox.tips(data.failed);
                    }
                }
            });               
        });
    });
    
	<#-- 发送消息 -->
	function sendMsg(){
		if(isLogin == 0){
			return;
		}
		var msg = $("#talk_input").val();
		msg = $.trim(msg);
		if(msg == ""){
            $.xbox.tips('请输入聊天内容');
			return;
		}

		if("black" == meRole){
			$("#chat_list").append("<li><span class=\"col-main red\" >系统：你已经被禁言!</span></li>");
	    	return;
		}

		<#-- 发送消息 -->
		talk_CD(${appsetting.getOption("LANSHA.ROOM.CD")?default("3")! });
		$('#talk_input').val("");
		<#-- 直接显示在聊天框 -->
		putMsg(msg);
		var userType = 0;
		if("master" == meRole){
			userType = 1;
		}else if("admin" == meRole){
			userType = 2;
		}else if("official" ==  meRole){
			userType = 4;
		}else if("superManager" == meRole){
			userType = 3;
		}
		var msgHeader = getTitle(userType);
        var msg_ = "";
		if("" != msgHeader){
			msg_ = "<li>"+msgHeader+"<span class=\"col-main\" >" + usernickname.split('|')[0] + "：</span>" + msg + "</li>";
		}else{
			msg_ = "<li><span class=\"col-main\" onclick=\"popManageMenu(this,'" + usernickname.split('|')[0] + "','" + usernickname.split('|')[1] + "')\">" + usernickname.split('|')[0] + "：</span>" + msg + "</li>";
		}
		showMessage(msg_, false);
		<#-- 发送im -->
		thisCat().sendMessage(msg);
	}
	
	<#assign admin = "admin0" />
	<#-- 接受消息 -->
	//var putMsg = false;
	function groupMessage(user, uid, data, time, nickname, userType){
		if(nickname == usernickname){
			return;
		}
		<#-- 显示到聊天记录 -->
		var msg = data;
		if(msg == ""){
			return;
		}
		if(user+uid == "${admin! }"){
			var jsonData = eval("("+data+")");
			
			if(jsonData.type == 4){
				<#-- 礼物 -->
				if("${stack.findValue('@com.yaowang.lansha.common.constant.LanshaConstant@GIFT_ID')}" == jsonData.giftId){
					msg = "<li class='gift_xiami clearfix'><div class='fl'><span class='red'>"
						+ jsonData.sendName+"</span>赠送给主播<em class='red'>"
						+ jsonData.giftNum+ "个虾米"
						+ "</em></div><div class='xiami'></div></li>";
				
					<#--虾米增加展示-->
					<#if anchor>
					var giftNum = $("#myMoney").text();
					$("#myMoney").text(Number(giftNum) + jsonData.giftNum);
					</#if>
				}else if("${stack.findValue('@com.yaowang.lansha.common.constant.LanshaConstant@GIFT_ID_TWO')}" == jsonData.giftId){
					msg = "<li class='talk_gift clearfix'><div class='fl'><span class='red' >"
						+ jsonData.sendName+"</span>赠送给主播<em class='red'>"
						+ jsonData.giftNum+ "朵鲜花"
						+ "</em></div><div class='icon_flower'></div></li>";
					<#--鲜花增加展示-->
					var giftNum = $("#flowerSumNum").val();
					var allNum = Number(giftNum) + jsonData.giftNum;
					var showNum = allNum;
					if(showNum >= 10000){
						showNum = (allNum/10000).toFixed(1) + "万";
					}
					$("#flowerSum").text(showNum);
					$("#flowerSumNum").val(allNum);
				}else if("${stack.findValue('@com.yaowang.lansha.common.constant.LanshaConstant@GIFT_ID_FOUR')}" == jsonData.giftId){
					msg = "<li class='talk_gift clearfix'><div class='fl'><span class='red' >"
						+ jsonData.sendName+"</span>赠送给主播<em class='red'>"
						+ jsonData.giftNum+ "张日票"
						+ "</em></div><img src='${staticPath! }/static/lansha/static/images/todayTickets.png' alt=''></div></li>";
					reloadTicketInfo();
				}
			}else if(jsonData.type == 1){
				<#-- 开播(去除遮罩层) -->
				//console.log("开播");
				$(".noLive").addClass("hide");
				thisMovie().openVideo();
				
				<#-- 显示领虾米 -->
				<#if !anchor>
					$(".chest").removeClass("hide");
					time = getGiftTime;
					$(".chest #onlineTime").html("--:--");
					settime();
				</#if>
				<#-- 防止弹幕 -->
				return;
			}else if(jsonData.type == 2){
				<#-- 停播(打开遮罩层) -->
				//console.log("停播");
				$(".noLive").removeClass("hide");
				//thisMovie().closeVideo();
				<#-- 隐藏领虾米 -->
				<#if !anchor>
					$(".chest").addClass("hide");
					time = -1;
				</#if>
				<#-- 防止弹幕 -->
				return;
			}else if(jsonData.type == 3){
				<#-- 禁播(打开遮罩层) -->
				//console.log("禁播");
				//$(".noLive").removeClass("hide");
				//thisMovie().closeVideo();
				location.href=location.href;
				<#-- 防止弹幕 -->
				return;
			}else if(jsonData.type == 5){
				msg = "<li  class='clearfix jinyan'><div class='fl'><span class='col-main red'>" + jsonData.uName + "被管理员禁言!</span></div></li>";
                if(jsonData.uId == meId){
                	role = "black";
                }
			}else if(jsonData.type == 6){
				msg = "<li><span class='col-main red'>" + jsonData.uName + "被管理员解除禁言!</span></li>";
				if(jsonData.uId == meId){
                	role = "other";
                }
			}else if(jsonData.type == 7){
				msg = "<li class='gift_xiami clearfix message'><div class='fl'><span class='col-main red'>恭喜 " + jsonData.uName + jsonData.reason + " 获得" + jsonData.giftDes + "</span></div><div class='xiami'>虾米</div></li>";
				
				if(jsonData.uId == '${(userLogin.id)?default('')}'){
					$("#myMoney").html(jsonData.uInventory + "");
				}
			}
		}else{
			putMsg(msg);
			var msgHeader = getTitle(userType);
			if("" != msgHeader){
				msg = "<li>"+msgHeader+"<span class=\"col-main\">" +  user + "：</span>" + msg + "</li>";
			}else{
				msg = "<li><span class=\"col-main\" onclick=\"popManageMenu(this,'" + user + "','" + uid + "')\">" +user + "：</span>" + msg + "</li>";
			}
			
			showMessage(msg, false);
			return;
		}
		showMessage(msg, true);
	}
	
	function getTitle(userType){
		if(userType == "1"){
			return "<span class='level boger'>主播</span>";
		}else if(userType == "2"){
			return "<span class='level master'>房管</span>";
		}else if(userType == "3"){
			return "<span class='level official'>超管</span>";
		}else if(userType == "4"){
			return "<span class='level official'>官方</span>";
		}else {
			return "";
		}
	}
	
	function showMessage(msg, show){
		<#-- 表情转码 -->		
		msg = ImgIputHandler.Code2Img(msg);
		document.getElementById("chat_list").innerHTML = document.getElementById("chat_list").innerHTML + msg;
		<#-- 是否滚屏 -->
        var chatScroll = $('.chat-scroll');
        var isScroll = chatScroll.attr("data-chatscroll");
        if (isScroll != 'open') {
			$('#chat_list').scrollTop($('#chat_list')[0].scrollHeight);
		}
		if(show){
			putMsg(msg);
		}
	}
	
	function putMsg(msg){
		<#-- 表情转码 -->
		msg = ImgIputHandler.Code2Img(msg);
		<#-- 弹幕 -->
		//if(putMsg){
			var str = removeHTMLTag(msg)+"";
			thisMovie().putMsg(str);
		//}
	}

	<#-- 刷新日票 -->
	function reloadTicketInfo(){
		$.ajax({
            type: 'POST',
            url: '${contextPath! }/ticket-sort.html?id=${room.uid! }',
            dataType: 'json',
            success: function (data) {
                if (data.status == "1") {
                	$('#ticket-num').html(data.ticketSum);
                    $('.ticket-num').html('日票：<span>' + data.ticketNum + '</span>');
                    $('.ticket-rank').html('排名：<span>' + data.ticketOrder + '</span>');
                    $('.ticket-minus').html('上一名：<span>' + data.ticketWithPrevious + '</span>');
                }
            }
        });
	}

	<#-- 用户进入房间 -->
	var useUserJion = false;
	function onUserJoin(nickName, userId, time){
		//$("#userlist").append("<li id='" + userId + "'>" + nickName + "</li>");
		
		//if(putMsg){
		//	document.getElementById("mesbox").innerHTML = document.getElementById("mesbox").innerHTML 
		//		+ "&nbsp;&nbsp;" + nickName + "进入房间<br />";
		//	$('#mesbox').scrollTop($('#mesbox')[0].scrollHeight );
		//}
		if(nickName+userId == '${admin! }'){
			<#-- admin发送的消息忽略 -->
			return;
		}
		<#-- 加入人为当前人则开始加人 -->
		if((userId == '0' && nickName==getCatSetting().nickname.split('|')[0]) || (userId != '0' && userId=='${(userLogin.id)?default('')}')){
			useUserJion = true;
		}
		if(useUserJion){
			$('#onlineNum em').text(parseInt($('#onlineNum em').text())+1);
		}
	}
	
	<#-- 用户离开房间 -->
	function onUserLeave(nickName, userId, time){
		//$("#" + userId).remove();
		
		//if(putMsg){
		//	document.getElementById("chat_list").innerHTML = document.getElementById("chat_list").innerHTML 
		//		+ "&nbsp;&nbsp;" + nickName + "离开房间<br />";
		//	$('#chat_list').scrollTop($('#chat_list')[0].scrollHeight);
		//}
		if(nickName+userId == '${admin! }'){
			return;
		}
		var count = parseInt($('#onlineNum em').text())-1;
		if(count > 0){
			$('#onlineNum em').text(count);
		}
	}
	
	<#-- 连接成功 -->
	function onLogin(){
		<#--未登录 textarea增加class:disabled 增加属性 disabled="disabled"  input未登录增加样式:nologin 登录后移除 增加属性 disabled="disabled"  -->
		if(isLogin){
			<#-- 发送按钮 -->
			$('#btn_talk_send').removeClass("nologin");
            $("#btn_talk_send").attr("disabled",false);
			<#-- 聊天消息 -->
			//$('#chat_list').text("");
			if($('#talk_input').val() == "正在连接..." || $('#talk_input').val() == "未登录，点击登录"){
				$('#talk_input').val("");
			}
			$("#talk_input").removeClass("disabled");
			$('#talk_input').attr("data-login", "1");
			<#-- 表情 -->
			$(".face-box").removeClass("disabled");
	        meId = usernickname.split('|')[1];
	        <#-- 日票 -->
	        $('#todayTickets').removeClass('disabled');
		}else{
			<#-- 发送按钮 -->
			$('#btn_talk_send').addClass("nologin");
            $("#btn_talk_send").attr("disabled",true);
			<#-- 聊天消息 -->
			$('#talk_input').val("未登录，点击登录");
			$("#talk_input").addClass("disabled");
			$('#talk_input').attr("data-login", "0");
		}
		<#-- 1秒钟后可以开始弹幕 -->
		//putMsg = false;
		//setTimeout(function(){putMsg = true; }, 1 * 1000);
	}
	
	<#-- 连接失败 -->
	function onError(){
		//$("#userlist").empty();
		//document.getElementById("mesbox").innerHTML += "&nbsp;&nbsp;连接断开，正在重新连接...<br />";
		<#-- 发送按钮 -->
		//$("#btn_talk_send").attr("disabled",true);
		//$('#btn_talk_send').addClass("nosend");
		<#-- 文本域 -->
		//$("#talk_input").addClass("disabled");
		//$("#talk_input").attr("readonly",true);
		//$('#talk_input').val("正在连接...");
		//$('#talk_input').attr("data-login", "0");
	}
	
	function removeHTMLTag(str) {
		str = str.replace(/<[^>]+>/g, '');
		str = str.replace(/&nbsp;/g, '');
		str = str.replace(/[\r\n]/g, ' ')
        return str;
    }
    
    <#-- 视频连接失败 -->
    function  showConnectError(){
    	//alert("视频连接失败！");
    	$(".error-live").removeClass("hide");
    }
    
    <#-- 视频连接成功 -->
    function  hideConnectError(){
    	$(".error-live").addClass("hide");
    }
	
	<#-- 无直播提示信息 -->
	function  showNoLiveTips(){
    	//alert("无直播！");
    	$(".noLive").removeClass("hide");
    }
    
    <#-- 刷新视频 -->
    function  refreshVedio(){
    	$(".error-live").addClass("hide");
    	thisMovie().refreshVedio();
    }
    
    <#-- 定时2分钟获取在线人数 -->
    function loadOnline(){
    	$.ajax({
            type: 'POST',
            url: '${contextPath! }/live-online.html?id=${room.id! }',
            dataType: 'json',
            success: function (data) {
        		$('#onlineNum em').text(data.num);
            	setTimeout(function(){
            		loadOnline();
            	}, 2 * 60 * 1000);
            }
        })
    }
    
    var getFlowerTime = ${giveFlowerTime?default(180) }; //鲜花增加时间间隔.单位秒
    var MaxFlowerNum = ${maxSum?default(10) };  //鲜花最大数
    var FF_Timer = null;
    var num = ${(flowerStock.stock)?default(0)};
	//领取鲜花
	function getFlower(){
        if(num >= MaxFlowerNum){
        	clearInterval(FF_Timer);
        }else {
        <#if !anchor>
        	<#-- 玩家 -->
            $.ajax({
                type: 'POST',
                url: '${contextPath! }/live-flower.html?id=${room.id! }',
                dataType: 'json',
                success: function (data) {
                    if (data.status == '1') {
                        $("#gift_flower_num").html(data.biSum);
                        $('#gift_flower_num').data('num',data.biSum);
                        //增加效果
                        $(".f_plus-minus i").html(data.bi);
                        $(".f_plus-minus b").html("+");
                        $(".f_plus-minus").css({"opacity":"1","display":"block"}).animate({"top": "-35px"}, 200).animate({"opacity": "0"}, 100).animate({"top":"-8px"},1);
                    }else{
                    	$("#gift_flower_num").html(data.biSum);
                    	$('#gift_flower_num').data('num',data.biSum);
                    }
                    num = data.biSum;
                }
            });
        </#if>
        }
	}
	
	<#-- 获取免费鲜花礼物 -->
    function getFreeFlower(getFlowerTime){
        FF_Timer = setInterval("getFlower()", parseInt(getFlowerTime)*1000);
    }
    $(document).ready(function() {
        //领鲜花
    	if(isLogin){
	    	//进房间已送鲜花故首次不用发请求领取
	    	//getFlower();
	    	getFreeFlower(getFlowerTime);
		}
		<#-- 定时获取在线人数 -->
		loadOnline();
	});
   
    <#--赠送日票-->
    $("#todayTickets").on('click',function(){
    
	    if (!isLogin) {
        loginShow();
        return false;
        }
        var buttonT = $(this);
        var todayTickets_num = parseInt($('#todayTickets_num').data('num'));

        if (buttonT.hasClass("disabled") || todayTickets_num<=0) {
            return false;
        }
        buttonT.addClass("disabled");
        $.ajax({
            type: 'POST',
            url: '${contextPath! }/live-giveTicket.html?id=${room.id! }',
            dataType: 'json',
            success: function (data) {
                if (data.status == "1") {
                    $.xbox.tips(data.msg);
                    $('#todayTickets_num').data('num',data.biSum);
                    $('#todayTickets_num').html(data.biSum);
                } else {
                    $.xbox.tips(data.msg);
                    buttonT.removeClass("disabled");
                }
            }
        });
    });
        
    <#-- 赠送鲜花 -->
    $("#giveFlower").on('click',function(){
        var buttonT = $(this);
        var gift_flower_num = parseInt($('#gift_flower_num').text());
		//未登录弹出登录框
		if (!isLogin) {
            loginShow();
            return false;
        }
		
        if (buttonT.hasClass("disabled") || gift_flower_num<=0) {
            return false;
        }
        buttonT.addClass("disabled");
        $.ajax({
            type: 'POST',
            url: "${contextPath! }/live-giveFlower.html?id=${room.id! }" ,
            dataType: 'json',
            success: function (data) {
                var flowerStock = $('#gift_flower_num').text();
                if (data.status == "1") {
                    $.xbox.tips(data.msg);
                    //thisCat().sendMessage(entity.msg);
                    $('.gift_flower_num').hide();
                    $('#gift_flower_num').html(data.biSum);
                    $('#gift_flower_num').data('num', data.biSum);
                    //减少效果
                    $(".f_plus-minus i").html("1");
                    $(".f_plus-minus b").html("-");
                    $(".f_plus-minus").css({"opacity":"1","display":"block"}).animate({"top": "-35px"}, 200).animate({"opacity": "0"}, 100).animate({"top":"-8px"},1);
                    $('.gift_flower_num').show();
                    if(FF_Timer==null){
                        getFreeFlower(getFlowerTime);
                    }
                } else {
                    $.xbox.tips(data.msg);
                    $('#gift_flower_num').html(data.biSum);
                    $('#gift_flower_num').data('num', data.biSum);
                }
                num = data.biSum;
                //防止鲜花库存达到上限停止定时领取鲜花，点击赠送再次调用
                if(flowerStock >= MaxFlowerNum){
               		getFreeFlower(getFlowerTime);
                }
                buttonT.removeClass("disabled");
            }
        });
    });
	
	<#--
	jQuery("body").ready(function(){
		$("#catMsg").keydown(function(k){
			if(k.keyCode == 13){
				$("#catBtn").click();
				return false;
			}
		});
	});
	-->
</script>
</#macro>

<#macro left type="">
<!--左侧悬浮导航-->
<div class="leftbar">
	<div class="face-box">
		<img src="${(user.headpic)! }" onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'" alt="主播头像" class="face">
		<#--普通用户&游客的按钮-->
		<#if (role)?default('other') != 'master'>
			<#if (relation.id)?default("") == "">
				<a href="javascript:;" class="follow-icon" id="follow">订阅</a>
			<#else>
				<a href="javascript:;" class="follow-icon on" id="follow">已订阅</a>
			</#if>
		</#if>
	</div>
	<p class="anchor-name">${room.nickname! }</p>
	<div class="anchor-game-num">
		<p class="game clearfix fl">
			<span class="fr">${room.gameName! }</span> 
			<i class="icon-sprite icon-gamename fr"></i>
		</p>
		<p class="num clearfix fl">
			<i class="icon-sprite icon-people fl"></i>
			<span class="fl" id="onlineNum"><em>${room.onLineNumber! }</em></span>
		</p>
	</div>
	<div class="show-num">
		<ul class="show-num-ul clearfix">
			<li class="fl"><span class="icon follow-icon"></span>
				<p id="favNum">${favNum! }</p></li>
			<li class="fl"><span class="icon today-icon"></span>
				<p id="ticket-num">${ticketSum! }</p></li>
			<li class="fl"><span class="icon flower-icon"></span>
				<p id="flowerSum">${flowerSum! }</p>
				<input type="hidden" id="flowerSumNum" value="${flowerSumNum! 0}" />
			</li>
		</ul>
	</div>
	<div class="msg-box">
		<a href="javascript:;" data-sel="1" class="msg-box-sel on fl">团队</a> <a
			href="javascript:;" data-sel="2" class="msg-box-sel fl">公告</a>
		<div class="slide-notice"></div>
		<div class="slide-notice slide-notice2 ">
			<ul id="adminListUl">
				<#list adminList?if_exists as manager>
					<li><span>房管</span>${manager.nickname! }</li>
				</#list>
			</ul>
		</div>
	</div>
	<div class="room-sharebox mgt15 clearfix">
		<div class="room-share">
			<div class="hand clearfix fl">分享:</div>
			<div class="fl">
				<div class="bdsharebuttonbox">
					<a href="#" class="bds_more" data-cmd="more"></a> <a
						title="分享到新浪微博" href="#" class="bds_tsina" data-cmd="tsina"></a> <a
						title="分享到微信" href="#" class="bds_weixin" data-cmd="weixin"></a> <a
						title="分享到QQ好友" href="#" class="bds_sqq" data-cmd="sqq"></a>
				</div>
			</div>
		</div>
	</div>
	<div class="qr-con">
		<p class="qr-title">扫一扫下载APP，更多惊喜</p>
		<img src="${uploadPath! }/app/${lanshaLogo! }-type1.png${staticVersion! }" class="qr-img" alt="" width="145" height="145">
		<a href="${contextPath! }/help-center.html" class="qr-btn fl">直播指导</a> 
		<a href="${contextPath! }/about-contactus.html" class="qr-btn fl">客服支持</a>
	</div>
</div>
</#macro>
