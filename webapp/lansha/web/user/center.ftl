<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/jquery.xBox.min.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/style.css${staticVersion! }">
	<!--[if lt IE 9]>
    <script ="${staticPath! }/static/lansha/static/js/html5.min.js"></script>
    <![endif]-->
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
                    <a class="fl cur" href="#">基本资料</a>
                    <a class="fl" href="password.html">修改密码</a>
                    <a class="fl" href="telphoneAuthe.html">绑定手机</a>
                    <a class="fl" href="becomeAnchor.html">主播认证</a>
                    <a class="fl" href="invite.html">邀请朋友</a>
                </div>
                <!--基本资料-->
                <ul class="realname-list">
                	<form id="frmUserNickName" onsubmit="return false;"  action="center-update.html" onSubmit="return false;">
                        <li class="realname-one clearfix">
                            <span class="realname-name fl">昵称:</span>
                            <input type="text" id="realName" name="user.nickname" maxlength="16" class="realName user-int01 user-int04" value="${(user.nickname)!}" disabled="true">
                            <span class="col-orange change-btn">修改</span>
                            <span class="col-orange change-certain" id="saveNickName">确定</span>
                            <span class="col-orange change-cancel">取消</span>
                        </li>
                        <input type="hidden" name="user.id" value="${(user.id)!}" />
                    </form>
            		<form id="frmUserInfo" action="center-update.html">
                    <li class="realname-one clearfix" id="gendercol">
                        <span class="realname-name fl">性别:</span>
                        <label class="checkbox <#if (user.sex)?default(0) == 1>checked</#if>" data-value="1"><span class="fl checkbox-icon"></span><span class="fl">男</span></label>
                        <label class="checkbox <#if (user.sex)?default(0) == 2>checked</#if>" data-value="2"><span class="fl checkbox-icon"></span><span class="fl">女</span></label>
                        <input type="hidden" name="user.sex" id="gender" value="${(user.sex)!}">
                    </li>
                    <li class="realname-one realname-border clearfix">
                        <span class="realname-name fl">头像:</span>
                        <div class="clearfix">
                        	<div class="regheader">
	                            <img class="fl file-img"  id="headerFace" src="${(user.headpic)!}" onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'">
                                <!--<span class="hide-file"></span>-->
                                <a class="fl file-input" id="isupload" data-apiurl='${contextPath!}/uploadImg.html'>正在初始化</a>
                            </div>
                            <input type="hidden" id="headerFaceVal" name="user.headpic" value="${(user.headpic)!}">
                            <div class="wrongface"></div>
                        </div>
                    </li>
                    <li class="realname-one clearfix">
                        <span class="realname-name fl">手机认证:</span>
                        <span>${(user.mobile)!}</span>
                        <span class="col-orange"><#if (user.mobile)?default("") == "">未认证<#else>已认证</#if></span>
                    </li>
                    <#--<li class="realname-one clearfix">
                        <span class="realname-name fl">实名认证:</span>
                        <#if (user.authe)?default(0) == 1><span class="col-orange">已认证</span><#else>
                        <a class="IDcard-example" href="becomeAnchor.html">实名认证</a></#if>
                    </li>-->
                    <li class="realname-one clearfix">
                        <span class="realname-name fl">用户类型:</span>
                        <span><#if (user.userType)?default(0) == 1>主播<#else>普通用户</#if></span>
                    </li>
                    <li class="realname-one clearfix">
                            <a class="btn user-btn" href="javascript:;" id="frmUserInfo_submit">保存</a>
                    </li>
						<input type="hidden" name="user.id" value="${(user.id)!}">
	                </form>
                </ul>
                <!--基本资料 end-->
            </div>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/jquery.md5.min.js"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/upload/plupload.full.min.js"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>

<script>
   var BASE_PATH = '/lansha/';
    $(function () {
        //调用用户注册
        $(function(){
            new LSUser.modifyInfo().init();
        });
    })
</script>
</body>
</html>