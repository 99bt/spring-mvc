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
    	<@lansha.userLeft index="4"/>
    	
    	<div class="help-right fr">
    	       <ul class="address-list">
                <h3 class="title"><span class="fl">收货地址</span><span class="showAddress fr" id="showAddress">查看</span></h3>
                <form id="addressForm" method="post" action="${contextPath! }/user/address-save.html">
            	<input type="hidden" id="id" name="entity.id" value="${(entity.id)!}" />
                    <li class="address-one">
                        <input type="text" id="addressName" name="entity.realname" value="${(entity.realname)!}"  maxlength="10" class="address-int01" placeholder="收件人姓名"/>
                    </li>
                    <li class="address-one">
                        <input type="text" id="addressAdd" name="entity.address" value="${(entity.address)!}"  maxlength="30" class="address-int01" placeholder="收件人地址"/>
                    </li>
                    <li class="address-one">
                        <input type="text" id="qq"  name="entity.qq" value="${(entity.qq)!}"    maxlength="11" class="address-int01" placeholder="QQ号码" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" disabled />
                    </li>
                    <li class="address-one">
                        <input type="text" id="addressPhone" name="entity.telphone" value="${(entity.telphone)!}"  maxlength="11" class="address-int01" placeholder="收件人手机号"/>
                    </li>
                    <li class="address-two">
                        <a href="javascript:;" class="btn user-btn address-btn">保存</a>
                    </li>
                </form>
            </ul>
            <dl class="prize-record">
                <dt>获奖记录</dt>
                <#if list?has_content>
                	<#list list?if_exists as item>
                		<dd>
		                    <span class="prize-td01">${(item.nickName)! }</span>
		                    <span class="prize-td02">${(item.createTime?string("yyyy-MM-dd HH:mm:ss"))!}</span>
		                    <span class="prize-td03">${(item.giftName)! }</span>
		                    <span class="prize-td04">
		                    <#if item.status?default('') == '1'>
		                    	<#if item.type?default('') == '3' || item.type?default('') == '4'>
			                    	${(item.remark)! }
		                    	<#else>
		                    		已发货
		                    	</#if>
		                    <#elseif item.status?default('') == '2'>
		                    	<p style="color:red;">${(item.remark)! }</p>
		                    <#elseif item.status?default('') == '0'>
		                    	等待客服人员发放
		                    </#if>
		                    </span>
		                </dd>
                	</#list>
               	<#else>
               		<div class="no-data">
		                <img src="${staticPath! }/static/lansha/static/images/nodata.png${staticVersion! }" alt="">
						暂无数据
		            </div>
                </#if>
            </dl>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jq_input.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.xBox.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.YWPlaceholder.min.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script>

    $('[placeholder]').YWPlaceholder();
    $('#showAddress').on('click',function(){
       $('#addressForm').toggle();
        var text = $.trim($(this).html());
        if(text=='查看'){
            $(this).html('隐藏');
        }else{
            $(this).html('查看');
        }
    });

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