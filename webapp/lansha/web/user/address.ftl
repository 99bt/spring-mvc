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
    	<@lansha.userLeft index="7"/>
    	
    	<div class="help-right fr">
            <form id="addressForm" method="post" action="${contextPath! }/user/address-save.html">
            	<input type="hidden" id="id" name="entity.id" value="${(entity.id)!}" />
	            <ul class="address-list">
	                <li class="address-one">
	                    <input type="text" id="addressName" name="entity.realname" value="${(entity.realname)!}" maxlength="10" class="address-int01" placeholder="收件人姓名"/>
	                </li>
	                <li class="address-one">
	                    <input type="text" id="addressAdd" name="entity.address" value="${(entity.address)!}" maxlength="30" class="address-int01" placeholder="收件人地址"/>
	                </li>
	                <li class="address-one">
	                    <input type="text" id="qq" name="entity.qq" value="${(entity.qq)!}"  maxlength="11" class="address-int01" placeholder="QQ号码" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
	                </li>
	                <li class="address-one">
	                    <input type="text" id="addressPhone" name="entity.telphone" value="${(entity.telphone)!}" maxlength="11" class="address-int01" placeholder="收件人手机号" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" autocomplete="off"/>
	                </li>
	                <li class="address-two">
	                    <a href="javascript:;" class="btn user-btn address-btn">保存</a>
	                </li>
	            </ul>
        	</from>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jq_input.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script>
   $(function () {
        $(".address-btn").on("click", function () {
            var reg_MPnum = /^0{0,1}(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
            if ($("#addressName").val() == "") {
                $.xbox.tips("请输入收件人姓名！");
            } else if ($("#addressAdd").val() == "") {
                $.xbox.tips("请输入收件人地址！");
            }else if (!reg_MPnum.test($("#addressPhone").val())) {
                $.xbox.tips("请输入正确的手机号！");
            } else {
                var addressUrl = $("#addressForm").attr("action");
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: addressUrl,
                    data: $("#addressForm").serialize(),
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if(data.status == '1'){
                           $.xbox.tips("收货人信息已保存成功！");
                           if (data.url!='') {
	                           setTimeout("window.location.href = "+data.url, 1000);
                            };
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