<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/style.css${staticVersion! }">
    <style>
        .rank-title { border-bottom:1px solid #f1f0f0; }
        .rank-title p { margin-left: 10px;font:20px/1.5 "Microsoft YaHei";color: #171717;margin-top: 8px; }
        .rank-title .btn3 { width: 90px;height: 37px;color: #666666;font: 16px/37px "Microsoft YaHei";background: #cccccc;text-align: center;margin-right: 26px;margin-bottom: 8px; }
        .pageend { display: inline; }
        .pageend .pagin {display: block;margin: 0;padding: 0;float: left; }
        .pageend .pagin.cur a{ background: #f5f5f5;cursor: default;color: #737373;float: left;display: block;margin: 0;padding: 0;list-style: none; }
        .pageend .pagin a{ float: left; width: 31px;height: 28px;border: 1px solid #DDD;text-align: center;line-height: 30px;border-left: none;color: #3399d5; }
        .pageend .pagin a:hover { background: #f5f5f5;}
        .pageend .pagin.pre span{ display: block; background: url("${staticPath! }/static/lansha/static/images/pre.gif") no-repeat center center;width: 31px;  height: 28px; }
        .pageend .pagin.next span{display: block; background: url("${staticPath! }/static/lansha/static/images/next.gif") no-repeat center center;width: 31px;  height: 28px; }
        .haveMoney-con { padding: 0 40px 80px 40px;min-height: 500px; }
        .haveMoney-con .box-1 p { width: 90px;font:12px/40px "Microsoft YaHei";color: #666666;text-align: center; }
        .haveMoney-con .box-1 input { padding: 0 10px;width: 359px;height: 38px;border: 1px solid #cccccc;font:12px/40px "Microsoft YaHei";color: #666666; }
        .haveMoney-con .user-btn { margin-left: 145px;}
    </style>
</head>
<body>
<@lansha.head index="1" showdiv="1"/>

<div class="container">
    <div class="layout  help-box user-center clearfix">
    	<@lansha.userLeft index="8"/>
    	
    	<div class="help-right fr">
            <form id="bankInfoForm" method="post" action="${contextPath! }/user/bankInfo-save.html">
    	             <div class="rank-title clearfix">
                <p class="fl">银行资料</p>
                <!--<a href="#" class="btn3 fr">提现须知</a>-->
            </div>
            <div class="haveMoney-con">
                <div class="clearfix box-1 mgt30">
                    <p class="fl">开户人姓名:</p>
                    <input type="text" placeholder="" id="userPayName"  name="userPay.name"  maxlength="20" class="fl chackInput" data-chack="请输入开户人姓名" value="${(userPay.name)!}">
                </div>
                <div class="clearfix box-1 mgt30">
                    <p class="fl">银 行 卡 号:</p>
                    <input type="text" placeholder="" id="userPayCardNo"  name="userPay.cardNo" maxlength="20" class="fl chackInput" data-chack="请输入银行卡号" value="${(userPay.cardNo)!}">
                </div>
                <div class="clearfix box-1 mgt30">
                    <p class="fl">开户行（支行）:</p>
                    <input type="text" placeholder="" id="userPayBank"  name="userPay.bank" maxlength="200"  class="fl chackInput" data-chack="请输入开户行（支行）" value="${(userPay.bank)!}">
                </div>
	            <a href="javascript:;" class="btn user-btn mgt40" id="bankSave">保存</a>
            </div>
        	</from>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.xBox.min.js"></script>

<script>
   $(function () {
        $("#bankSave").on("click", function () {
            if ($("#userPayName").val() == "") {
                $.xbox.tips("请输入开户人姓名！");
            } else if ($("#userPayCardNo").val() == "") {
                $.xbox.tips("请输入卡号！");
            }else if ($("#userPayBank").val()== "") {
                $.xbox.tips("请输入开户银行！");
            } else {
                var saveUrl = $("#bankInfoForm").attr("action");
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: saveUrl,
                    data: $("#bankInfoForm").serialize(),
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if(data.status == '1'){
                           $.xbox.tips("银行信息已保存成功！");
                        }else{
                            $.xbox.tips(data.failed);
                            return false;
                        }
                    }
                });
            }
        })
    })
</script>
</body>
</html>