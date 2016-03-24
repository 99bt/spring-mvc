<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta name="author" content="Yowant FE Team" />
    <meta name="Description" content="页面说明" />
    <meta name="Keywords" content="页面关键字" />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <title>美女主播专题h5 - 蓝鲨tv</title>
    <style>
        /*reset.css*/
        body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, form, fieldset, input, textarea, p, blockquote, th, td { padding: 0; margin: 0; font-family: "Microsoft Yahei"; }
        table { border-collapse: collapse; border-spacing: 0; }
        fieldset, img { border: 0; }
        address, caption, cite, code, dfn, em, strong, th, var { font-weight: normal; font-style: normal; }
        ol, ul { list-style: none; }
        caption, th { text-align: left; }
        h1, h2, h3, h4, h5, h6 { font-weight: normal; font-size: 100%; }
        q:before, q:after { content: ''; }
        abbr, acronym { border: 0; }
        input, button, textarea { outline: none; }
        a { text-decoration: none; color: #8a8a8a; }
        a, a:active, a:focus { outline: none; -webkit-tap-highlight-color: transparent; }
        body {background: #e7e7e7;}
        .conent {max-width: 750px;}
        .conent.top {text-align: center;padding-top: 30%;background: url("http://oss.lansha.tv/activity/upload/meinvzhubo/h5_bg.png") center top no-repeat #fff;background-size: contain;    padding-bottom: 4%;}
        .conent .videoBlock {width: 90%;margin: 0 auto;background: #282828;}
        .conent .tjzb {width: 94%;margin: 0 auto;overflow: hidden;margin-top: 10px;}
        .conent .tjzb li {float: left;margin-right: 2%;width:23.5%;}
        .conent .tjzb li:last-child{margin-right: 0px;}
        .conent .tjzb li img {max-width: 100%;}
        .conent.s2 {margin-top: 4%;background: #fff;padding: 4.5% 3%;}
        .s2 .title {font-size: 18px;}
        .s2 .title img {width: 5%;margin-right: 2.5%;}
        .s2 .zbtjbox {overflow: hidden; margin: 0 auto;width: 99%;margin-top: 2%;}
        .zbtjbox .col {width: 32%;margin-right: 2%;overflow: hidden;float: left;}
        .zbtjbox .col a {display: block;}
        .zbtjbox .big, .zbtjbox .small {float: left;margin-bottom: 2.2%;overflow: hidden;}
        .zbtjbox .big img, .zbtjbox .small img {width: 100%;height: 100%;}
        .zbtjbox .col:last-child {margin-right: 0px;}
        .conent.s3 {margin-top: 4%;background: #fff;padding: 4.5% 3%;}
        .s3 .title {font-size: 18px;}
        .s3 .title img {width: 5%;margin-right: 2.5%;}
        .s3 .roomlist {overflow: hidden; margin: 0 auto;width: 99%;}
        .s3 .roomlist li {float: left;width: 48%;margin-right: 3.8%;overflow: hidden;margin-top: 3%;}
        .s3 .roomlist li img {width: 100%;}
        .s3 .roomlist li:nth-child(2n+0) {margin-right: 0px;}
        .s3 .roomlist li p {font-size: 14px;width:100%;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;}
    </style>
</head>

<body>

${name! }

<section class="conent s3">
    <div class="title"><img src="${uploadPath! }/activity/upload/meinvzhubo/dian.png" alt="">最新主播</div>
    <ul class="roomlist">
    <#list list?if_exists as item>
        <li>
            <a href="${contextPath! }/${item.idInt! }"><img src="${uploadPath! }${item.liveImg! }" alt="${item.name! }" title="${item.name! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';"></a>
            <p>${item.name! }</p>
        </li>
    </#list>
    </ul>
</section>
<!--conent end-->
<!--script start-->
<script src="${upload! }/static/lansha/static/js/zepto.min.js"></script>
<!--script end-->
<!--counter-->
<div class="counter">
</div>
</body>
</html>