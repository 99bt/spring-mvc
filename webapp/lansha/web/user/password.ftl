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
                    <a class="fl cur" href="#">修改密码</a>
                    <a class="fl" href="telphoneAuthe.html">绑定手机</a>
                    <a class="fl" href="becomeAnchor.html">主播认证</a>
                    <a class="fl" href="invite.html">邀请朋友</a>
                </div>
                <!--修改密码-->
                <form id="frmChangePWD" action="password-update.html">
                    <ul class="realname-list">
                        <li class="realname-one clearfix">
                            <span class="realname-name fl">手机认证:</span>
                            <span>${(user.mobile)!}</span>
                        	<span class="col-orange"><#if (user.mobile)?default("") == "">未认证<#else>已认证</#if></span>
                        </li>
                        <li class="realname-one clearfix">
                            <span class="realname-name fl">当前密码:</span>
                            <input class="user-int01" type="password" id="oldpwd" maxlength="16" />
                        </li>
                        <li class="realname-one clearfix">
                            <span class="realname-name fl">新密码:</span>
                            <input class="user-int01" type="password" id="pwd" maxlength="16" />
                        </li>
                        <li class="realname-one clearfix">
                            <span class="realname-name fl">确认密码:</span>
                            <input class="user-int01" type="password" id="pwd2" maxlength="16" />
                        </li>
                        <li class="realname-one clearfix">
                            <a class="btn user-btn" href="javascript:;" id="frmChangePWD_submit">提交</a>
                        </li>
                        <input type="hidden" name="user.id" value="${(user.id)!}">
                        <input type="hidden" name="user.password" id="pwdhide" value="">
                        <input type="hidden" name="user.oldpassword" id="pwdhide_old" value="">
                    </ul>
                </form>
                <!--修改密码 end-->
            </div>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.md5.min.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script>
    $(function () {
        //调用用户注册
        new LSUser.modifyPWD().init();
    })
</script>
</body>
</html>