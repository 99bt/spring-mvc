<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <title>${platFormName! }-用户注册协议</title>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <style>
        html, body, div, h1, h2, p { margin: 0; padding: 0; }
        .agreement { max-width: 960px; margin: 0 auto; font: 12px/20px "Microsoft Yahei"; color: #404040; padding: 0 15px 30px; }
        .agreement h1 { font: bold 20px/40px "Microsoft Yahei"; text-align: center; margin-top: 10px; }
        .agreement h2 { font: bold 14px/24px "Microsoft Yahei"; margin-top: 12px; }
        .agreement p { padding: 3px 0; }
    </style>
</head>

<body>

<div class="agreement">
  <@lansha.agreementContent />
</div>


</body>
</html>