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
		if($("#name").val() == ""){
			addFieldError("房间名称不能为空");
			return false;
		}
		if($("#gameId").val() == ""){
			addFieldError("游戏不能为空");
			return false;
		}
		var intro = $("#intro").val();
		if(intro != '' && intro.length > 200){
			addFieldError("房间介绍内容最多只能输入200字");
			return false;
		}
		var notice = $("#notice").val();
		if(notice != '' && notice.length > 200){
			addFieldError("房间公告内容最多只能输入200字");
			return false;
		}
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
		    <li><a href="#">房间管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
		    <form action="room-save.html" id="form1" method="post" enctype="multipart/form-data">
		    	<input type="hidden" id="id" name="entity.id" value="${entity.id!}" />
		    	<input type="hidden" name="roomName" value="${roomName!}" />
		    	<input type="hidden" name="sign" value="${sign!}" />
		    	<input type="hidden" name="idInt" value="${idInt!}" />
		    	<input type="hidden" name="username" value="${username!}" />
		    	<input type="hidden" name="gameName" value="${gameName!}" />
		    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
			    <div class="formtitle"><span class="sp">房间信息</span></div>
				    <ul class="forminfo">
				    	<li><label><font color="red">*</font>主播：</label>
				    		<@plugins.search text="userName" value="uid" url="room-search.html" />
			        		<input type="text" id="userName" value="${entity.nickname!}" style="width:130px;" class="scinput-150" autocomplete="off">
					    	<input type="hidden" id="uid" name="entity.uid" value="${entity.uid!}" />
					    </li>
					    <li><label><font color="red">*</font>房间id：</label>
					    	<input value="${entity.idInt! }" id="idInt" name="entity.idInt" type="text" class="dfinput-345" readonly="" />
							<font style="color:red;">系统维护，不可修改</font>
						</li>
					    <li><label><font color="red">*</font>房间名称：</label>
					    	<input value="${entity.name! }" id="name" name="entity.name" type="text" class="dfinput-345" />
					    </li>
					    <li><label><font color="red">*</font>是否签约：</label>
                         <#--   <input type="hidden" name="entity.sign" value="${entity.sign?default('0')}" disabled/>-->
					    	<label><input type="radio"  name="entity.sign" value="0" <#if entity.sign?default('0') == '0'>checked</#if>  />未签约</label>
					    	<label><input type="radio"  name="entity.sign" value="1" <#if entity.sign?default('0') == '1'>checked</#if>  />已签约</label>
					    </li>
					    <li><label><font color="red">*</font>游戏名称：</label>
					    	<div class="public_input_msg_w">
				                <input id="gameName" value="${entity.gameName! }" class="dfinput-345" type="text" readonly="">
				                <input id="gameId" name="entity.gameId" value="${entity.gameId! }" type="hidden">
				                <div id="pickgame" class="button fl ml10" data-t="选择"></div>
				            </div>
						</li>
					    <li><label>开播时间：</label>
					    	<input value="${entity.startTime! }" id="startTime" name="entity.startTime" type="text" class="dfinput-345" />
						</li>
					    <li><label><font color="red">*</font>基数：</label>
					    	<input value="${entity.baseNumber! }" id="baseNumber" name="entity.baseNumber" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=7/>
							<font style="color:red;">在线观众人数加上基数</font>
						</li>
					    <li><label><font color="red">*</font>倍数：</label>
					    	<input value="${entity.multipleNumber! }" id="multipleNumber" name="entity.multipleNumber" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=7/>
							<font style="color:red;">在线观众人数乘以倍数</font>
						</li>
					    <li><label><font color="red">*</font>状态：</label>
					    	<label><input type="radio" name="entity.online" value="0" checked />下线</label>
					    	<label><input type="radio" name="entity.online" value="1" <#if entity.online?default(0) == 1>checked</#if> />上线</label>
					    </li>
					    <li><label>排序号：</label>
					    	<input value="${entity.orderId! }" id="orderId" name="entity.orderId" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=7/>
						</li>
						<li><label>介绍：</label>
							<textarea id="intro" name="entity.intro" class="textinput" style="width:320px;height:62px;">${entity.intro! }</textarea>
						</li>
						<li><label>公告：</label>
							<textarea id="notice" name="entity.notice" class="textinput" style="width:320px;height:62px;">${entity.notice! }</textarea>
						</li>
						<li><label><font color="red">*</font>房间号：</label>
					    	<input value="${entity.roomId! }" id="roomId" name="entity.roomId" type="text" class="dfinput-345" readonly="" />
					    	<font style="color:red;">系统维护，不可修改</font>
						</li>
						<li><label><font color="red">*</font>流媒体地址：</label>
					    	<input value="${entity.rtmp! }" id="rtmp" name="entity.rtmp" type="text" class="dfinput-345" readonly="" />
					    	<font style="color:red;">系统维护，不可修改</font>
						</li>
						<li><label><font color="red">*</font>上行流域名：</label>
					    	<input value="${entity.liveHost! }" id="liveHost" name="entity.liveHost" type="text" class="dfinput-345" readonly="" />
							<font style="color:red;">系统维护，不可修改</font>
						</li>
						<li><label><font color="red">*</font>下行流域名：</label>
					    	<input value="${entity.wsHost! }" id="wsHost" name="entity.wsHost" type="text" class="dfinput-345" readonly="" />
							<font style="color:red;">系统维护，不可修改</font>
						</li>
						<li><label><font color="red">*</font>聊天房间号：</label>
					    	<input value="${entity.openfireRoom! }" id="openfireRoom" name="entity.openfireRoom" type="text" class="dfinput-345" readonly="" />
							<font style="color:red;">系统维护，不可修改</font>
						</li>
						<li><label><font color="red">*</font>聊天室地址：</label>
					    	<input value="${entity.openfirePath! }" id="openfirePath" name="entity.openfirePath" type="text" class="dfinput-345" readonly="" />
							<font style="color:red;">系统维护，不可修改</font>
						</li>
						<li><label><font color="red">*</font>聊天室端口：</label>
					    	<input value="${entity.openfirePort! }" id="openfirePort" name="entity.openfirePort" type="text" class="dfinput-345" readonly="" />
							<font style="color:red;">系统维护，不可修改</font>
						</li>
						<li><label><font color="red">*</font>聊天室服务：</label>
					    	<input value="${entity.openfireConference! }" id="openfirePort" name="entity.openfireConference" type="text" class="dfinput-345" readonly="" />
							<font style="color:red;">系统维护，不可修改</font>
						</li>
					    <li>
					    	<label>&nbsp;</label>
					    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
					    	<input name="" type="button" class="btn" value="返回" onclick="location.href='room.html?roomName=${roomName! }&idInt=${idInt! }&username=${username! }&sign=${sign! }&gameName=${gameName! }&pageIndex=${pageIndex! }'"/>
					    </li>
				    </ul>
			    </div>
		    </form>
		    <div class="confirmationBg"></div>
		    <div class="layer">
		        <div>
		            <div>
		                <div class="confirmationWrapper confirmMidWrapper" style="width: 650px">
		                    <div class="confirmCloseBtn cmd_closePopup"></div>
		                    <div class="title">选择游戏</div>
		                    <div class="content bigContent" style="margin-top:0px;">
		                        <div class="right_content">
		                            <input id="ppg_txt_keyword" class="public_input" type="text" value="" maxlength="32" style="border: 1px solid #999;width:200px; height: 35PX;">
		                            <div id="ppg_btn_search" class="button fl ml10" data-t="搜索"></div>
		                        </div>
		                        <div class="mainGameBox">
			                        <div class="right_content">
			                            <div class="table_nav">
			                                <div class="table_title tcol_psg1">&nbsp;</div>
			                                <div class="table_title tcol_psg4">游戏名称</div>
			                            </div>
			                            <div id="ppg_table_games" class="scrollbar">
			                                <div class="viewport">
			                                    <div id="ppg_list_games" class="overview tac" style="top: 0px;">
			                                        <!--game list-->
			
			                                        <!-- end -->
			                                    </div>
			                                </div>
			                            </div>
			                        </div>
			                     </div>
		                    </div>
		                    <div class="btns tar">
		                        <div class="button btok" data-t="确　定"></div>
		                        <div class="button btno alt cmd_closePopup" data-t="取　消"></div>
		                    </div>
		                </div>
		            </div>
		        </div>
			</div>
		</div>
	</div>
</body>
<script>
jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
$(function() {
	var page = 1;
    var kwd = '';
    var gameID = '';
    var gameName = '';
	        
	$('.viewport').bind('slimscroll', function (e, pos) {
    });

    $('.btok').click(function() {
        if (gameID==''){
            alert('请选择游戏');
        }else{
            $('#gameName').val(gameName);
            $('#gameId').val(gameID);
            $('#discountCoin').val(discountCoin);
            $('#discountSettled').val(discountSettled);
            $('.confirmationBg, .layer').hide();
        }
    });
    //显示选择游戏
    $('#pickgame').click(function() {
        //请求数据
        _getData(page,kwd);
        //显示游戏窗
        $('.confirmationWrapper').css('left', parseInt($(window).width())/2 - parseInt($('.confirmationWrapper').width())/2);
        $('.confirmationBg, .layer').toggle();
    });

    function _getData(page,kwd) {
        //请求数据
        $.ajax({
            url: 'room-game.html',
            type: 'get',
            async: false,
            dataType: 'html',
            data: {pageIndex: page, name: kwd},
            success: function(data){
                $('#ppg_list_games').html(data);
                //加载数据后绑定一次
                $('.cell-inner input').unbind('click');
                $('.cell-inner input').bind('click', function() {
                   gameID = $(this).val();
                   gameName = $(this).data("gamename");
                   discountCoin = $(this).parent().find("[name='discountCoin']").val();
   				   discountSettled = $(this).parent().find("[name='discountSettled']").val();
                });
                //翻页
                $('.page_num').unbind('click');
                $('.page_num').bind('click',function() {
                    page = parseInt($(this).data('page'));
                    $('.page_num').removeClass('cur_page_num');
                    $(this).addClass('cur_page_num');
                    _getData(page,kwd);
                });
                //绑定结束
            }
        })
    }
    
    //搜索
    $('#ppg_btn_search').click(function() {
        kwd = $('#ppg_txt_keyword').val();
        _getData(page,kwd);
    });
    //关闭
    $('.cmd_closePopup').click(function() {
    	gameID = 0;
        $('.confirmationBg, .layer').hide();
    });
});
</script>
</html>
