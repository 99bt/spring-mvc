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
		if($("#roomId").val() == ""){
			addFieldError("房间Id不能为空");
			return false;
		}
		if($("#timeTarget").val() == ""){
			addFieldError("时长为空");
			return false;
		}
        if($("#salary").val() == ""){
            addFieldError("初始薪资为空");
            return false;
        }
//        if($("#ticketTarget").val() == ""){
//            addFieldError("日票指标为空");
//            return false;
//        }
        if($("#manager").val() == ""){
            addFieldError("负责人不能为空");
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
		    <li><a href="#">签约主播管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
		    <form action="contract-save.html" id="form1" method="post">
		    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
                <input type="hidden" id="userId" name="entity.userId" value="${entity.userId!}" />
			    <div class="formtitle"><span class="sp">房间信息</span></div>
				    <ul class="forminfo">

                        <li><label><font color="red">*</font>房间ID：</label>
                            <div class="public_input_msg_w">
                                <input id="room" value="${entity.roomId! }" class="dfinput-345" type="text" readonly="">
                                <input id="roomId" name="entity.roomId" value="${entity.roomId! }" type="hidden">
                                <#if entity.roomId??>
                                <#else>
                                    <div id="pickgame" class="button fl ml10" data-t="选择"></div>
                                </#if>
                            </div>
                        </li>

					    <li><label><font color="red">*</font>手机号：</label>
                            <input  value="${entity.mobile! }"  id="mobile" class="dfinput-345" type="text" readonly="true">
					    </li>
                        <li><label><font color="red">*</font>真实姓名：</label>
                            <input  value="${entity.userName! }"  id="userName" class="dfinput-345" type="text" readonly="true">
                        </li>
                        <li><label><font color="red">*</font>游戏名称：</label>
                            <input id="gameName" value="${entity.gameName! }" class="dfinput-345" type="text" readonly="true">
                        </li>
                        <li><label><font color="red">*</font>签约时间：</label>

                            <input type="text" id="startTime" value="${entity.startTime!}" name="entity.startTime" onclick="WdatePicker();" class="scinput-150" readonly>
                            至
                            <input type="text" id="endTime" value="${entity.endTime!}" name="entity.endTime" onclick="WdatePicker();" class="scinput-150" readonly>
                        </li>
                        <li><label><font color="red">*</font>时长指标：</label>
                            <input value="${entity.timeTarget! }" id="timeTarget" name="entity.timeTarget" type="text" class="dfinput-345"   onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />小时
                        </li>
                        <li><label><font color="red">*</font>初始薪资：</label>
                            <input value="${entity.salary! }" id="salary" name="entity.salary" type="text" class="dfinput-345"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
                        </li>
                        <li><label><font color="red"></font>日票指标：</label>
                            <input value="${entity.ticketTarget! }" id="ticketTarget" name="entity.ticketTarget" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
                        </li>
                        <li><label><font color="red">*</font>负责人：</label>
                            <input value="${entity.manager! }" id="manager" name="entity.manager" type="text" class="dfinput-345" />
                        </li>
                        <li><label><font color="red">*</font>是否考核：</label>
                            <label><input type="radio" name="entity.examine" value="1" <#if examine==1>checked</#if> />参加考核</label>
					    	<label><input type="radio" name="entity.examine" value="0" <#if examine==0>checked</#if> />不参加考核</label>

					    </li>


						<li><label>备注：</label>
							<textarea id="intro" name="entity.remark" class="textinput" style="width:320px;height:62px;">${entity.remark!}</textarea>
						</li>

					    <li>
					    	<label>&nbsp;</label>
					    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
					    	<input name="" type="button" class="btn" value="返回" onclick="location.href='contract.html?pageIndex=${pageIndex! }'"/>
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
		                    <div class="title">选择房间</div>
		                    <div class="content bigContent" style="margin-top:0px;">
		                        <div class="right_content">
		                            <input id="ppg_txt_keyword" class="public_input" type="text" value="" maxlength="32" style="border: 1px solid #999;width:200px; height: 35PX;">
		                            <div id="ppg_btn_search" class="button fl ml10" data-t="搜索"></div>
		                        </div>
		                        <div class="mainGameBox">
			                        <div class="right_content">
			                            <div class="table_nav">
			                                <div class="table_title tcol_psg1">&nbsp;</div>

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
    var roomId = '';
    var gameId = '';
    var mobile='';
    var userName='';
    var gameName = '';
    var userId='';
	        
	$('.viewport').bind('slimscroll', function (e, pos) {
    });

    $('.btok').click(function() {
        if (roomId==''){
            alert('请选择房间号');
        }else{
            $('#room').val(roomId);
            $('#roomId').val(roomId);
            $('#gameName').val(gameName);
            $('#mobile').val(mobile);
            $('#userName').val(userName);
            $('#userId').val(userId);
            $('#gameName').val(gameName);
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
            url: 'contract-search.html',
            type: 'get',
            async: false,
            dataType: 'html',
            data: {pageIndex: page, roomId: kwd},
            success: function(data){
                $('#ppg_list_games').html(data);

                //加载数据后绑定一次
                $('.cell-inner input').unbind('click');
                $('.cell-inner input').bind('click', function() {
                  var value = $(this).val();
                    //gameId = $(this).data("gameId");
                    var strs= new Array(); //定义一数组

                    strs=value.split(","); //字符分割
                    roomId=strs[0];
                    userId=strs[1] ;
                    mobile=strs[2];
                    userName=strs[3];
                    gameName=strs[4];

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
