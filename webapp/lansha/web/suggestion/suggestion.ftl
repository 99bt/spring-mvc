<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${platFormName! } - 意见反馈</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css${staticVersion! }">
</head>
<body class="fffbf8-body">
<@lansha.head index="1"/>

<div class="container">
    <div id="wrap" class="layout clearfix">
        <div class="live_wrap clearfix">
            <div class="u_header">
                <h1 class="fl">意见反馈</h1>
            </div>
            <div class="u_main feedback clearfix">
                <div class="u_mainbody">
                    <div class="feedbackMes">
                        <p class="notice01" id="turn-page"></p>
                    </div>
                    <!--feedbackMes-end-->
                    <div class="feedback">
                        <form name="frm" id="frm" method="post" action="">
                            <div class="formGroup clearfix">
                                <label class="controlLable">类型：</label>

                                <div class="col">
                                	<#list listSuggestionType?if_exists as item>
                                		<label class="radioBox">
	                                        <input type="radio" value="${item.thisId! }" <#if item_index == 0>checked="checked"</#if> name="suggestion.type"> ${item.content! }
	                                    </label>
                                	</#list>
                                </div>
                            </div>
                            <div class="formGroup clearfix">
                                <label class="controlLable">标题：</label>
                                <div class="col">
                                    <input type="text" class="inputHdline" name="suggestion.title" id="message_title" maxlength="50">
                                </div>
                            </div>
                            <div class="formGroup clearfix">
                                <label class="controlLable">内容：</label>
                                <div class="col">
                                    <textarea class="textarea" name="suggestion.content" id="content" maxlength="300"></textarea>
                                </div>
                            </div>
                            <input type="hidden" name="check" id="check" value="6232">
                            <div class="btn">
                                <button id="frmbut" type="button" class="primary_button01 fl">提交</button>
                            </div>
                        </form>
                    </div>
                    <!--feedback-end-->
                </div>
            </div>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>

<script type="text/javascript">
    $(".primary_button01").on("click", function () {
        if ($(".inputHdline").val() == "") {
            $.xbox.tips("请输入标题");
            return false;
        } else if ($("#content").val() == "") {
            $.xbox.tips("请输入内容");
            return false;
        } else {
            $.ajax({
                url: '${contextPath! }/doSuggestion.html',
                type: 'POST',
                async: false,
                dataType: 'json',
                data: $("#frm").serialize(),
                success: function (data) {
                	if (data.status == '1') {
	                    new LSUser().outTimeGo("${contextPath! }/suggestion.html", 2, "反馈成功");
                	} else {
                		$.xbox.tips(data.failed);
            			return false;
                	}
                }
            });
        }
    });
</script>
</body>
</html>