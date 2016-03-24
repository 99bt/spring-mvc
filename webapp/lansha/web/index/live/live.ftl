<#import "/lansha/web/common/lansha.ftl" as lansha>
<#import "/lansha/web/index/live/liveCommon.ftl" as live>
<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${room.name! }-${platFormName!}</title>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/style.css${staticVersion! }">
</head>
<script>
    var Android = navigator.userAgent.match(/Android/i);
    var iPhone = navigator.userAgent.match(/iPhone/i);
    var iPad = navigator.userAgent.match(/iPad/i);
    if (Android || iPhone|| iPad) {
        window.location="${contextPath! }/wap/live.html?id=${id! }";
    }
</script>
<body>
<@lansha.head index="" isBoxLogin="true" style="live-details-header"/>
<#assign liveUrl = "${hostPath! }${contextPath! }${appsetting.getLivePath(room.idInt)! }" />
<#--
pn=${(loginUser.id)! }
-->
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="1px" height="1px" id="catobj"
        codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab" style="position: absolute;top: 0px; z-index: -1000;";>
    <param name="movie" value="${staticPath! }/static/lansha/static/flash/cat.swf${staticVersion! }" />
    <param name="quality" value="high" />
    <param name="wmode" value="Opaque" />
    <param name="allowFullScreen" value="true" />
    <param name="bgcolor" value="#865ca7" />
    <param name="allowScriptAccess" value="always" />
    <embed src="${staticPath! }/static/lansha/static/flash/cat.swf${staticVersion! }" quality="high" bgcolor="#865ca7"
        width="1" height="1" name="catobj" align="middle"
        play="true" loop="false" quality="high" wmode="Opaque" allowFullScreen="true" allowScriptAccess="always"
        type="application/x-shockwave-flash"
        pluginspage="http://www.macromedia.com/go/getflashplayer">
    </embed>
</object>

<div class="container">
    <@live.left type="0"/>
    
    <div class="liveBlock">
        <!--播放器-->
        <div class="live-main">
        	<a href="${store! }/activity/anchor-recruit.html" target="_blank">
                <div class="live-main-recruit" style="height: 172px;background: url('${uploadPath! }/activity/rootbanner.jpg${staticVersion! }') center top no-repeat;">
                </div>
            </a>
            <div class="room-intro clearfix">
                <span class="room-pic fl"><img src="${staticPath! }/static/lansha/static/images/dianshiji.png"  onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'"  width="80" height="80" /></span>
                <div class="room-text fl">
                    <h3><@plugins.cutOff cutStr="${room.name! }" cutLen="16" /></h3>
                </div>
                <!--新手指引第三步-->
                <div class="guide-popup popup-third">
                    <div class="guide-close"></div>
                    <div class="popup-triangle"></div>
                    <div class="popup-guide clearfix">
                        <span class="guide-num guide-num03 fl"></span>
                        <div class="guide-text">
                            <h2>关注他</h2>
                            <p>成为忠实粉丝</p>
                        </div>
                    </div>
                </div>
            </div>
            <!--播放器-->
            <div id="playerArea">
                <div class="noLive <#if (room.online)?default(0) == 1>hide</#if>">
                    <a href="${contextPath! }/liveList.html" class="noLive-btn">返回直播大厅</a>
                    <p class="noLive-tips">本房间暂无直播，更多精彩直播推荐</p>
                    <div class="noLive-rec">
                        <ul class="live-list clearfix">
                         <#list roomList?if_exists as item>
			             <#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
                         <#assign userIcon = "${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }" />
			                <li class="game-one fl">
			                    <a href="${url! }" class="game-pic">
			                        <img src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';" alt="" />
			                        <div class="live-icon"><img src="${item.userIcon! userIcon}" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png';" alt="" /></div>
			                        <div class="live-bg"></div>
			                        <div class="live-begin"></div>
			                    </a>
			                    <h3 class="room-name mgt10"><a href="${url! }">${item.name! }</a></h3>
	                    		<div class="room-topic clearfix">
	                        		<div class="fl"><i class="icon-sprite icon-people fl"></i>${item.nickname! }</div>
	                        		<div class="fr"><i class="icon-sprite icon-gamename fl"></i>${item.gameName! }</div>
	                        		<div class="fl"><i class="icon-sprite icon-eye fl"></i>${item.onLineNumber! }</div>
	                    		</div>
	                    		<i class="show-line-b"></i>
			                </li>
			            </#list>
                        </ul>
                    </div>
                </div>
                <!--在需要刷新时，显示erroe-live 去掉hide-->
                <div class="error-live hide">
                    <a href="${contextPath! }/liveList.html" class="noLive-btn">返回直播大厅</a>
                    <div class="refresh clearfix">
                        <h3>${platFormName!}</h3>
                        <img src="${staticPath! }/static/lansha/static/images/erroe-icon.png" alt="感叹号">
                        <p class="tips">获取节目信息失败，请刷新页面</p>
                        <a href="javascript:refreshVedio()" class="fl">点击刷新</a>
                        <a href="${contextPath! }/suggestion.html" class="fl">反馈问题</a>
                    </div>
                </div>
                <!--在需要刷新时，显示erroe-live 去掉hide-->
        		<div id="liveplayer"></div>
            </div>
            <!--播放器 end-->
            <div class="room-present clearfix <#if !userIsLogin?default(false)>hide</#if>"  id="userArea">
               
                <!--baoxiang-->
                <div class="getXiami fl <#if (itemId)?default("") != "">hide</#if>">
                   <div class="chest chest-active"> <!-- chest-active chest-countdown chest-visitor-->
                        <a class="chest-image" href="javascript:;" onclick="return false;"></a>
                        <a class="chest-btn" href="javascript:;" onclick="return false;" id="onlineTime">00:00</a>
                    </div>
                </div>
                <!--Q币礼包-->
                <div class="getQBBox fl <#if (itemId)?default("") == "">hide</#if>">
                    <div class="getQB getQB-countdown"> <!-- getQB-active getQB-countdown getQB-visitor-->
                        <a class="getQB-image" href="javascript:;" onclick="return false;"></a>
                        <a class="getQB-btn" href="javascript:;" onclick="return false;" id="QBonlineTime">00:00</a>
                    </div>
                    <div class="getqb-tips"></div>
                </div>
                <!--Q币礼包 end-->
                <div class="room-coin fr">
                	<div class="fl">
                		我的虾米：<span class="col-main" id="myMoney">${stock! }</span>
                	</div>
                    <a href="javascript:;" class="gift-xiami" id="giveGift">
                    	<span class="plus-minus"><b>+</b><i>1</i></span>
	                    <div class="room-tips">
							<div class="popup-triangle"></div>
							虾米<br />
							点击送给主播
						</div>
                    </a>
                </div>
                 <!--新手指引第二步-->
                <div class="guide-popup popup-second">
                    <div class="guide-close"></div>
                    <div class="popup-triangle"></div>
                    <div class="popup-guide clearfix">
                        <span class="guide-num guide-num02 fl"></span>
                        <div class="guide-text">
                            <h2>领取虾米礼物</h2>
                            <p>送给主播表达心意</p>
                        </div>
                    </div>
                </div>
            </div>
            <!--未登录按钮--> 
            <div class="room-present room-nologin <#if userIsLogin?default(false)>hide</#if>" id="userArea_nologin">
               
                <div class="getXiami fl <#if (itemId)?default("") != "">hide</#if>">
                 <div class="chest chest-active">
                    <div class="chest-tips"></div>
                    <a class="chest-image" href="javascript:;" onclick="return false;"></a>
                    <a class="chest-btn" href="javascript:;" onclick="return false;">-:-</a>
                 </div>
                </div>
                <!--Q币礼包-->
                <div class="getQBBox fl <#if (itemId)?default("") == "">hide</#if>">
                    <div class="getQB"> <!-- getQB-active getQB-countdown getQB-visitor-->
                        <a class="getQB-image" href="javascript:;" onclick="return false;"></a>
                        <a class="getQB-btn" href="javascript:;" onclick="return false;">00:00</a>
                    </div>
                    <div class="getqb-tips"></div>
                </div>
                <!--Q币礼包 end-->

                
                <div class="room-coin fr">
                    <div class="fl">
                        	我的虾米：<span class="col-main">0</span>
                    </div>
                    <a href="javascript:;" class="gift-xiami" onclick="loginShow();return false;">
                        <span class="plus-minus"><b>+</b><i>1</i></span>
                        <div class="room-tips">
                            <div class="popup-triangle"></div>
					                            虾米<br />
					                            点击送给主播
                        </div>
                    </a>
                </div>
            </div>
            
            <div class="game-box only-livelist">
                <div class="live-title-box">
                    <div class="live-title">
                        <div class="titleDiv">
                            <hr class="left-line" />
                            <hr class="right-line" />
                            <span>热门推荐</span>
                        </div>
                    </div>
                </div>
                <div class="live-auto">
	                <ul class="live-list clearfix">
	            	<#list list?if_exists as item>
		                <#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
                        <#assign userIcon = "${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }" />
		                <li class="game-one fl<#if (item_index + 1) % 4 ==0> last</#if>" >
                           <a href="${url! }" class="game-pic"  target="_blank">
                              <img class="lazy" src="${uploadPath! }${item.liveImg! }"  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }';" alt="${item.name! }" width="280" height="156" />
                              <div class="live-icon"><img src="${item.userIcon! userIcon}" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="${item.name! }" /></div>
                              <div class="live-bg"></div>
                              <div class="live-begin"></div>
                            </a>
                            <h3 class="room-name">
                            <a href="${url! }" class="room-name-title">${item.name! }</a>
                            <a href="${contextPath! }/gameLive.html?id=${item.gameId! }" class="fr room-game-name">${item.gameName! }</a>
                            </h3>
                        <div class="room-topic clearfix">
                        <div class="fl">${item.nickname! }</div>
                        <div class="fr"><i class="icon-sprite icon-people fl"></i>${item.onLineNumber! }</div>
                          </div>
                       <i class="show-line-b"></i>
                       </li>
	            	</#list>
	                </ul>
                </div>
            </div>
            <div class="details-footer">
            	<@lansha.footTitle />
            </div>
        </div>
        <!--playend-->
        <@live.chat anchor=false/>
    </div>
</div>
<div class="popup-center popup-shrimp">
    <div class="give-shrimp">
        <h3 class="give-static">恭喜您！</h3>
        <p class="give-text">获得虾米×<span>5</span></p>
    </div>
</div>
<!--登录注册弹窗 begin-->
<div class="popup-bg"></div>
<div class="popup-box">
    <h2 class="popup-slide">
        <span class="cur" data-slide="login">登录</span>
        <span data-slide="reg">注册</span>
    </h2>
    <div class="popup-close"></div>
    <div class="popup-cbox clearfix">
        <div class="popup-login clearfix">
            <ul class="login-list fl">
                <form id="frmUserLoginMini" action="${contextPath! }/dologin.html?liveType=live&id=${id! }">
                    <li class="login-one mgt30 clearfix">
                        <p><span class="infoArea loginiMPTEL"></span></p>
                        <input class="login-int01" type="text" id="loginMinTel" name="ywUser.username" maxlength="11" placeholder="请输入手机号" data-tips="请输入您的手机号" onkeydown="if(event.keyCode==13) {document.getElementById('loginMinpassword').focus();return false;}"/>
                    </li>
                    <li class="login-one mgt30 clearfix">
                        <p><span class="infoArea loginiPassword"></span></p>
                        <input class="login-int01" type="password"  id="loginMinpassword" placeholder="请输入密码" data-tips="请输入6-16位密码" onkeydown="if(event.keyCode==13) {document.getElementById('frmSubmitMini').click();return false;}"/>
                    </li>
                     <input type="hidden" name="roomId" value="${room.id}">
                     <input type="hidden" name="ywUser.password" id="pwdhidelogin" value="">
                    <li class="login-one mgt10 clearfix">
                        <a class="find-pwd fr" href="${contextPath! }/findPwd.html" target="_blank">忘记密码？</a>
                    </li>
                    <li class="login-one mgt30 clearfix">
                        <a class="login-btn fl" id="frmSubmitMini" href="javascript:;">登录</a>
                    </li>
                </form>
            </ul>
            <div class="login-right mgt20 fr">
            	<@lansha.othreLogin />
            </div>
        </div>
        <div class="popup-reg clearfix" style="display: none;">
            <ul class="login-list fl">
                <form id="frmUserRegMini" action="${contextPath! }/doregister.html?liveType=live&id=${id! }">
                    <li class="login-one mgt30 clearfix">
                        <p><span class="infoArea fr miniiMPTEL"></span></p>
                        <input class="login-int01 login-int02 fl" id="miniTel" type="text" placeholder="请输入手机号" maxlength="11" data-tips="请输入您的手机号码"  onkeydown="if(event.keyCode==13) {document.getElementById('SendCode').click();return false;}">
                        <input type="hidden" name="ywUser.username" value="" id="miniTel1">
                        <a class="login-code fr" id="SendCode" type="button" data-rcode="1" data-apiurl="${contextPath! }/rcode.html">发送验证码</a>
                    </li>
                    <li class="login-one mgt30 clearfix">
                        <p><span class="infoArea fr iRcodeMini"></span></p>
                        <input class="login-int01" type="text" name="rcode" id="rcode" placeholder="请输入验证码" maxlength="6" data-tips="请输入验证码"  onkeydown="if(event.keyCode==13) {document.getElementById('regNickname').focus();return false;}">
                    </li>
                    <li class="login-one mgt30 clearfix">
                        <p><span class="infoArea fr iNickname"></span></p>
                        <input class="login-int01" type="text" name="ywUser.nickname" id="regNickname" placeholder="请输入昵称" maxlength="16" data-tips="请输入昵称"  onkeydown="if(event.keyCode==13) {document.getElementById('miniPassword').focus();return false;}">
                    </li>
                    <li class="login-one mgt30 clearfix">
                        <p class="login-sex fl">性别</p>
                        <label class="checkbox checked" data-value="1"><span class="fl checkbox-icon"></span><span class="fl">男</span></label>
                        <label class="checkbox" data-value="2"><span class="fl checkbox-icon"></span><span class="fl">女</span></label>
                        <input type="hidden" name="ywUser.sex" id="genderMini" value="1">
                    </li>
                    <li class="login-one mgt30 clearfix">
                        <p><span class="infoArea fr iPasswordM">请输入密码</span></p>
                        <input class="login-int01" type="password" id="miniPassword" placeholder="请输入密码" data-tips="请输入6-16位密码"  onkeydown="if(event.keyCode==13) {document.getElementById('miniRePassword').focus();return false;}">
                    </li>
                    <li class="login-one mgt30 clearfix">
                        <p><span class="infoArea fr iPasswordM2"></span></p>
                        <input class="login-int01" type="password" id="miniRePassword" placeholder="请确认密码" data-tips="请再次输入密码"  onkeydown="if(event.keyCode==13) {document.getElementById('regsubmitMini').click();return false;}">
                    </li>
                    <input type="hidden" name="ywUser.password"  id="pwdrehide" value="">
                    <li class="login-one mgt30 clearfix">
                        <a class="login-btn" id="regsubmitMini" href="javascript:;">注册</a>

                        <p class="login-agreement mgt10">注册即默认同意<a href="${contextPath! }/pc-agreement.html" target="_blank">${platFormName!}《用户注册协议》</a></p>
                    </li>
                </form>
            </ul>
            <div class="login-right mgt20 fr">
            	<@lansha.othreLogin />
            </div>
        </div>
    </div>
</div>
<!--发送站内验证码-->
<div class="RcodeBox">
    <span>请输入验证码完成本次发送</span>
    <span class="wrongText" id="wrongText"></span>
    <span><input id="captchaInput" type="text" class="picCode" maxlength="6"></span>
    <div>
        <img id="captchaImage" data-apiurl="${contextPath! }/web/validate.html" class="codePic">
        <a class="changePic" id="changeCaptchaImage">看不清,换一个</a>
    </div>
    <a class="allBtn confirmBtn" id="checkRcode">确认</a>
    <a class="allBtn cancelBtn" id="cancelBtn">取消</a>
    <div class="clear"></div>
</div>

<!--领QBbox-->
<div class="QBbox" id="QBbox">
    <div class="box-header">
        <span class="fl">提示</span>
        <span class="fr closebox" id="closebox">X</span>
    </div>
    <div class="content">
        <p>填写QQ号就能成功领取Q币啦！</p>
        <p><input type="text" value="" id="qqnum" name="qqnum" maxlength="11"></p>
        <p class="btns">
            <a href="javascript:;" class="btn" id="submitGetQB">确定</a>
        </p>
    </div>
</div>
<!--领QBbox-->
<#-- 页尾js -->
<@lansha.footjs />

<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/swfobject.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.nicescroll.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.md5.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/emoji.min.js${staticVersion! }"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/room.js${staticVersion! }"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/mini-user.js${staticVersion! }"></script>
<script type="text/javascript">
    var roomID = '${room.idInt! }';
    var FAV_APIURL = '${contextPath! }/live-relation.html?id=${room.id! }'; //收藏接口
    var getOnlineAPIURL = '';
    var firstCom = ${firstCom?default(0)};  //是否显示新手引导 1显示
    
    var getGiftCount = <#if firstGetGift>1<#else>0</#if>;   //当日是否已领过虾米
    
    <#-- 领蓝鲨币倒计时 -->
    var getGiftTime = ${time! };//1800;
    var time = getGiftTime;
    var getQBtime = 10;  //获取QB时长
    var qb_time = getQBtime;
	//INIT
    new LSLiveRoom(null,null, FAV_APIURL).init();
	
	
    var MIN_WIDTH = 600;
    var mW = 0, mH = 0;
    
    function thisMovie() {
        return document["media_player_box"];
    }
    function getSetting() {
        var settings_object = {
            rtmp : "${(room.liveHost)! }",
            videoURL : "${(room.roomId)! }",
            width : mW,
            height :  mH,
            bufferTime : ${playCache?default(3) },
            isIndex: false,
            online:${room.online!}
        };
        return settings_object;
    }    

    $(function () {
    	//首次新手引导
        if (firstCom == 1) {
            $(".popup-first").show();
        }
        $(".popup-first .guide-close").on("click", function () {
            $(".popup-first").hide();
            $(".popup-second").show();
        })
        $(".popup-second .guide-close").on("click", function () {
            $(".popup-second").hide();
            $(".popup-third").show();
        })
        $(".popup-third .guide-close").on("click", function () {
            $(".popup-third,.popup-bg").hide();
            //发送到服务端.不再显示
            $.ajax({
                url: '${contextPath! }/live/updateIsRead.html',
                type: 'POST',
                dataType: 'json',
                success: function(data){}
            });
        })
        //加载播放器
        mW = $('.live-main').width(), mH = mW/16*9;
        $("#playerArea").css('height',mH+40);
        var params = {
            width: mW,
            height: mH,
            menu: "false",
            quality: "high",
            allowFullScreen: "true",
            bgcolor: "#865ca7",
            allowScriptAccess: "always",
            wmode: "Opaque"
        };
        
        var objAttr = {
            id: "media_player_box"
        }
        swfobject.embedSWF("${staticPath! }/static/lansha/static/flash/play.swf${staticVersion! }", "liveplayer", '100%', '100%', "9.0.0", null,null, params , objAttr,function(){
        });
        //主播 goplay
        $('#goplay').on('click', function() {
            $('.room-popup').show();
        });
        $(".change-close").on('click', function() {
            $('.room-popup').hide();
        });

        /** 新用户领Q币 start **/
        $(".getQB").on("click", function () {
            var _this = $(this);
            if (!isLogin) {
                loginShow();
                return false;
            }
            if (!$(this).hasClass("gray")) {
                $.ajax({
                    type: 'POST',
                    url: '${contextPath! }/isShowQq.html',
                    dataType: 'json',
                    success: function (data) {
                        if (data.status == 1) {
                            $('#QBbox').show();
                        } else {
                            $.xbox.tips(data.msg);
                            return false;
                        }
                    }
                });
            } else {
                return false;
            }
        });
        //提交QQ信息
        $('#closebox').click(function(){
           $('#QBbox').hide();
        });
        $('#submitGetQB').click(function(){
            var qqnum = $.trim($('#qqnum').val());
            if(qqnum==''){
                $.xbox.tips ('请输入QQ号');
                return false;
            }else  if (!RegExp(/^[1-9][0-9]{4,10}$/).test(qqnum)){
                $.xbox.tips('请输入正确的QQ号码');
                return false;
            }else {
                $.ajax({
                    type: 'POST',
                    url: '${contextPath! }/activity/saveActivity.html',
                    dataType: 'json',
                    data: {qq:qqnum,itemId:'${itemId! }'},
                    success: function (data) {
                        if (data.status == 1) {
                            $.xbox.tips('领取Q币资料已提交,请等待审核');
                            $('#closebox').click();
                            $('.getQBBox').hide();
                        } else {
                            $.xbox.tips(data.msg);
                            return false;
                        }
                    }
                });
            }
        });
        
        if(isLogin){
            get_QB_time();  //新用户登陆后启动
        }
        /** 领QB end **/

        //分享
        window._bd_share_config = {
            "common": {
                "bdText": "${platFormName!}全民手游直播，快来与主播${room.nickname! }一起互动！",
                "bdUrl": '${liveUrl! }',
                "bdSize": "24",
                "bdstyle": 1
            }, "share": {}
        };
        with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
        //礼物提示
        $('.gift-xiami').hover(function(){
            $(this).find('.room-tips').show();
        }).mouseout(function(){
            $(this).find('.room-tips').hide();
        })
        //领取虾米
	    $(".chest").on("click", function () {
            var _this = $(this);
            if (!isLogin) {
                loginShow();
                return false;
            }
            if (!_this.hasClass("gray")) {
                $.ajax({
                    type: 'POST',
                    url: '${contextPath! }/live-stock.html?id=${room.id! }',
                    dataType: 'json',
                    success: function (data) {
                        if (data.status == '1') {
                            _this.addClass('chest-visitor');
                            $("#myMoney").html(data.biSum);
                            $(".give-text span").html(data.bi);
                            $(".plus-minus i").html(data.bi);
                            $(".plus-minus b").html("+");
                            //效果
                            $(".popup-center").show().animate({"opacity": "hide"}, 2000);
                            $(".plus-minus").css({"opacity":"1","display":"block"}).animate({"top": "-50px"}, 1000).animate({"opacity": "0"}, 500).animate({"top":"-8px"},1);
                        } else {
                            $.xbox.tips(data.msg);
                        }
                        _this.removeClass('chest-active');
                        _this.removeClass('chest-visitor');
                        _this.addClass('chest-countdown');
                        time = getGiftTime;
                        settime();
                    }
                });
            } else {
                return false;
            }
        });
	    
	    $("#giveGift").on("click", function () {
	    	var buttonT = $(this);
	    	if(buttonT.hasClass("disabled")){
	    		return false;
	    	}
	    	buttonT.addClass("disabled");
    		<#-- 赠送虾米 -->
			$.ajax({
			    type: 'POST',
			    url: "${contextPath! }/live-giveGift.html?id=${room.id! }" ,
			    dataType: 'json',
			    success: function(data){
			    	if(data.status == "1"){
			    		//减少效果
			    		$('.room-tips').hide();
			    		$("#myMoney").html(data.biSum);
			    		$.xbox.tips(data.msg);
			    		//thisCat().sendMessage(data.msg);
			    		$(".plus-minus i").html("1");
                        $(".plus-minus b").html("-");
                        $(".plus-minus").css({"opacity":"1","display":"block"}).animate({"top": "-35px"}, 200).animate({"opacity": "0"}, 100).animate({"top":"-8px"},1);
			    	}else{
			    		$.xbox.tips(data.msg);
			    	}
			    	//置按钮
			    	buttonT.removeClass("disabled");
			    }
			});
	    })
    });
    //
    function get_QB_time() {
    setTimeout(function () {
        qb_time--;
        if (qb_time == 0) {
            $('.getQB').removeClass('getQB-countdown');
            $('.getQB').addClass('getQB-active');
            $('.getQB').removeClass('gray');
        } else if (qb_time > 0) {
            var h = parseInt(qb_time / 60);
            var s = qb_time % 60;
            h = h < 10 ? "0" + h : h;
            s = s < 10 ? "0" + s : s;
            $(".getQB #QBonlineTime").html(h + ":" + s);
            $('.getQB').addClass('gray');
            get_QB_time();
        }
    }, 1 * 1000);
}
    
</script>
</body>
</html>