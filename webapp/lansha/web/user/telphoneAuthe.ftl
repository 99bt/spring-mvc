<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/jquery.xBox.min.css">
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
                    <a class="fl cur" href="#">绑定手机</a>
                    <a class="fl" href="becomeAnchor.html">主播认证</a>
                    <a class="fl" href="invite.html">邀请朋友</a>
                </div>
                <!--手机认证-->
                <form id="frmbindMP" action="telphoneAuthe-update.html">
                    <ul class="realname-list password-list">
                    	<#if (user.mobile)?default("") == "">
                    	<li class="realname-one clearfix">
                            <span class="realname-name fl">手机号:</span>
                            <input class="user-int01" type="input" id="mphome" maxlength="11" />
                            <input type="hidden" name="telphone" id="realmphone" value="">
                            <a href="javascript:;" class="have-code col-orange " id="SendMPCode" data-rcode="1" data-apiurl="telphoneAuthe-mt.html">获取验证码</a>
                        </li>
                        <li class="realname-one clearfix">
                            <span class="realname-name fl">验证码:</span>
                            <input class="user-int01 user-int05" type="input" maxlength="6" name="code" id="rcode" />
                        </li>
                        <li class="realname-one clearfix">
                            <a class="btn user-btn mgt40" href="javascript:;" id="frmBDMP_submit">提交</a>
                        </li>
                        <#else>
                        <!--已认证-->
                        <li class="realname-one clearfix">
                            <span class="realname-name fl">手机认证:</span>
                            <span>${(user.mobile)!}</span>
                            <span class="col-orange"><#if (user.mobile)?default("") == "">未认证<#else>已认证</#if></span>
                        </li>
                        <!--已认证end-->
                        </#if>
                        <input type="hidden" name="user.id" value="${(user.id)!}">
                        <input type="hidden" name="user.password" id="pwdhide" value="">
                        <input type="hidden" name="user.oldpassword" id="pwdhide_old" value="">
                    </ul>
                </form>
                <!--手机认证 end-->
            </div>
        </div>
    </div>
</div>

<@lansha.foot />
<!--发送站内验证码-->
<div class="RcodeBox">
    <span>请输入验证码完成本次发送</span>
    <span class="wrongText" id="wrongText"></span>
    <span><input id="captchaInput" name="captchaCode" type="text" class="picCode" maxlength="6"></span>
    <div>
        <img id="captchaImage" data-apiurl="${contextPath! }/web/validate.html" class="codePic">
        <a class="changePic" id="changeCaptchaImage">看不清,换一个</a>
    </div>
    <a class="allBtn confirmBtn" id="checkRcode">确认</a>
    <a class="allBtn cancelBtn" id="cancelBtn">取消</a>
    <div class="clear"></div>
</div>
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script>
    $(function () {
        new LSUser.bindMP().init();
    })
</script>
</body>
</html>
