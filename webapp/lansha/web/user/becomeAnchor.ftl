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
    	<#-- 用户菜单 -->
        <@lansha.userLeft index="1"/>
        
        <div class="help-right fr">
            <@lansha.userCenter />
            <div class="user-con">
	        <div class="user-conTitle clearfix">
                    <a class="fl" href="center.html">基本资料</a>
                    <a class="fl" href="password.html">修改密码</a>
                    <a class="fl" href="telphoneAuthe.html">绑定手机</a>
                    <a class="fl cur" href="#">主播认证</a>
                    <a class="fl" href="invite.html">邀请朋友</a>
                </div>
            	<#if userApply?exists && (userApply.status)?default("0") != "2">
	            	<!--认证结果-->
	                <div class="approve">
	                    <!--认证判断开始-->
	                    <#if (userApply.status)?default("0") == "0">
            				<!--未通过认证-->
		                    <div class="clearfix approve-fail">
		                        <span class="approve-pic fl"></span>
		                        <p class="approve-text fl">正在认证审核中...</p>
		                    </div>
	            		<#else>
	            			<!--通过认证-->
		                    <div class="clearfix">
		                        <span class="approve-pic fl"></span>
		                        <p class="approve-text fl">你已经成功通过认证审核</p>
		                    </div>
	            		</#if>
	                    <!--认证判断结束-->
	                    <ul class="approve-list">
	                        <li class="approve-one">
	                            <span>认证日期: </span>${(userApply.aduitTime?string("yyyy-MM-dd"))!}
	                        </li>
	                        <li class="approve-one">
	                            <span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</span>${(userApply.realname)!}
	                        </li>
	                        <li class="approve-one">
	                            <span>身份证号: </span>${(userApply.identitycard)!}
	                        </li>
	                        <#if (userApply.status)?default("0") == "1" && userIsAnchor?string("true","false") == "false">
	                        	<li class="approve-one">
		                            <font class="col-orange">请重新登录，获得开通功能和权限</font>
		                        </li>
	                        </#if>
	                    </ul>
	                    <#if userApply.roomIdInt ??>
	                    <p class="approve-address">您的直播间地址: <em>${hostPath! }${contextPath! }${appsetting.getLivePath(userApply.roomIdInt)! }</em></p>
	                    </#if>
	                    <div class="clearfix mgt30">
                        <a href="${contextPath! }/help-iOS.html" class="renzhen-btn renzhen-btn1 fl">直播教程</a>
                        <a href="${contextPath! }/appdownload.html" class="renzhen-btn renzhen-btn2 fl">下载直播工具</a>
                    </div>
	                </div>
	                <!--认证结果 end-->
                <#else>
	                <!--实名认证-->
	                <form id="frmSMRZ" action="becomeAnchor-save.html" enctype=“multipart/form-data”>
	                	<input type="hidden" name="userApply.id" value="${(userApply.id)!}">
	                    <ul class="realname-list smrz-list">
                        	<li class="realname-one clearfix">
	                            <span class="realname-name fl">手机认证:</span>
	                            <span>${(user.mobile)!}</span>
	                        	<span class="col-orange"><#if (user.mobile)?default("") == "">未认证<#else>已认证</#if></span>
	                        </li>
	                        <#if (userApply.status)?default("0") == "2">
		                        <li class="realname-one clearfix">
		                            <span class="realname-name fl">失败原因:</span>
		                             ${(userApply.remark)!}
		                        </li>
	                        </#if>
	                        <li class="realname-one clearfix">
	                            <span class="realname-name fl">姓名:</span>
	                            <input class="user-int01" type="input" name="userApply.realname" id="realName" value="${(userApply.realname)!}"/>
	                        </li>
	                        <li class="realname-one clearfix">
	                            <span class="realname-name fl">身份证号:</span>
	                            <input class="user-int01" type="input" maxlength="18" name="userApply.identitycard" id="cardNo" value="${(userApply.identitycard)!}"/>
	                        </li>
	                        <li class="realname-one clearfix">
	                            <span class="realname-name fl">身份证到期时间:</span>
	                            <input class="user-int01" type="input" maxlength="4" name="userApply.expirationTime" id="enddate" value="${((userApply.expirationTime)?date)!}"/>
	                        </li>
	                        <li class="realname-one clearfix">
	                            <span class="realname-name fl">上传身份证:</span>
	                            <div class="IDcard-list clearfix">
	                                <div class="IDcard-one fl">
	                                    <img class="file-img" width="150" height="91" data-tips="IDcard1" id="img_cardzm" src="<#if (userApply.pic1)?default("") == ""> ${staticPath! }/static/lansha/static/images/IDcard01.png<#else>becomeAnchor-pic.html?name=1&id=${(userApply.id)!}</#if>" alt="上传身份证正面照">
	                                    <div class="hide-file">上传身份证正面照</div>
                                    	<input class="fl file-input" id="isupload_zm" data-apiurl='${contextPath! }/uploadImg.html' type="button" value="上传">
                                    	 <a class="IDcard-example" >示例
                                            <div class="IDcard1 IDcard"></div>
                                        </a>
	                                    <input type="hidden" name="userApply.pic1" id="img_cardzm_val" value="${(userApply.pic1)!}">
	                                </div>
	                                <div class="IDcard-one fl">
	                                    <img class="file-img" width="150" height="91" data-tips="IDcard2" id="img_cardfm" src="<#if (userApply.pic2)?default("") == ""> ${staticPath! }/static/lansha/static/images/IDcard02.png<#else>becomeAnchor-pic.html?name=2&id=${(userApply.id)!}</#if>" alt="上传身份证反面照">
	                                    <div class="hide-file">上传身份证反面照</div>
                                    	<input class="fl file-input" id="isupload_fm" data-apiurl='${contextPath! }/uploadImg.html' type="button" value="上传">
                                    	<a class="IDcard-example" >示例
                                            <div class="IDcard2 IDcard"></div>
                                        </a>
	                                    <input type="hidden" name="userApply.pic2" id="img_cardfm_val" value="${(userApply.pic2)!}">
	                                </div>
	                                <div class="IDcard-one fl">
	                                    <img class="file-img" width="150" height="91" data-tips="IDcard3" id="img_cardsc" src="<#if (userApply.pic3)?default("") == ""> ${staticPath! }/static/lansha/static/images/IDcard02.png<#else>becomeAnchor-pic.html?name=3&id=${(userApply.id)!}</#if>" alt="上传手持身份证照">
	                                    <div class="hide-file">上传手持身份证照</div>
                                    	<input class="fl file-input" id="isupload_sc" data-apiurl='${contextPath! }/uploadImg.html' type="button" value="上传">
                                    	<a class="IDcard-example" >示例
                                            <div class="IDcard3 IDcard"></div>
                                        </a>	
	                                    <input type="hidden" name="userApply.pic3" id="img_cardsc_val" value="${(userApply.pic3)!}">
	                                </div>
	                            </div>
	                            <div class="topic_switch"><span class="fl">查看上传身份证要求的详细说明</span><i class="fl"></i></div>
	                            <div class="realname-topic">
	                                <p>身份证及身份照将会进行人工审核，照片务必做到以下几点：</p>
	                                <p>*您需要年满16岁</p>
	                                <p>*全面：照片内包含以下内容：</p>
	                                <p>1、您的面部、身份证正面和您姓名签名</p>
	                                <p>2、身份证正面和您姓名签名</p>
	                                <p>3、身份证反面和您姓名签名</p>
	                                <p>*清晰：身份证照片文字及图片清晰可见</p>
	                                <p>*真实：身份证照片无PS</p>
	                                <p>*有效：身份证为二代身份证，在有效期内，正反内容相符</p>
	                                <p>如果以上信息有所不符，实名认证将驳回</p>
	                            </div>
	                        </li>
	                        <li class="realname-one clearfix">
	                            <!--房间信息-->
	                            <span class="realname-name fl">房间名称：</span>
	                            <input class="user-int01" id="roomName" name="userApply.roomName" type="text" value="${(userApply.roomName)!}" maxlength="30">
	                        </li>
	                        <li class="realname-one clearfix">
	                            <span class="realname-name fl">所属游戏：</span>
	                            <p class="room-num fl">
	                                <select name="userApply.gameId" id="gameSelect" class="select">
	                                    <option value="" selected="selected">请选择要直播的游戏</option>
			                        	<#list listGame?if_exists as item>
			                        		<option value="${item.id! }" <#if (item.id) == (userApply.gameId)!>selected</#if> >${item.name! }</option>
			                        	</#list>
	                                </select>
	                            </p>
	                        </li>
	                        <li class="realname-one clearfix">
	                            <span class="realname-name fl">直播公告：</span>
	                            <textarea name="userApply.notice" class="textarea" id="roomNote" maxlength="200" placeholder="欢迎来到我的直播间，喜欢记得点关注哟~"><#if (userApply.roomName)?default("") == "">欢迎来到我的直播间，喜欢记得点关注哟~<#else>${(userApply.notice)!}</#if></textarea>
	                        </li>
	                        <li class="realname-one clearfix">
	                            <a class="btn user-btn" href="javascript:;" id="frmSMRZ_submit">提交</a>
	                        </li>
	                        <li class="realname-one clearfix lawarea">
	                            <span class="realname-name fl"></span>
								申请主播即默认同意<a href="${contextPath!}/lansha/web/user/managementregulations.htm" target="_blank">《蓝鲨主播管理条例》</a>
                        	</li>
	                    </ul>
	                </form>
	                <!--实名认证 end-->
                </#if>
            </div>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/upload/plupload.full.min.js"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script>
    $(function () {
        //调用用户注册
		<#if !(userApply?exists) || (userApply.status)?default("0") == "2">
        new LSUser.SMRZ().init();
        $('.topic_switch').on('click',function(){
            $(this).toggleClass('on');
            $('.realname-topic').toggle();
        });
		</#if>
    })
</script>
</body>
</html>