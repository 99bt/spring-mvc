<#import "/lansha/wap/common/lanshawap.ftl" as commons>
<!DOCTYPE html>
<html>
<head>
    <@commons.head />
    <title>蓝鲨杯万元现金水友赛 - 蓝鲨TV</title>
    <style>
        * { margin: 0; padding: 0; }
        .shuiyousai { max-width: 750px; position: relative; }
        .shuiyousai img { max-width: 100%; display: block; }
        .btn { width: 29%; position: absolute; top: 11%; left: 53%; }
        .popup { display: none; position: fixed; top: 0; left: 0; z-index: 2; width: 100%; height: 100%; }
        .popup img { max-height: 100%; width: auto; margin: 0 auto;}
        .close { width: 7%; height: 6%; position: absolute; top: 8%; right: 15%; }
        .popup-bg { display: none; position: fixed; top: 0; left: 0; background: #000; opacity: 0.8; overflow: hidden; width: 100%; height: 100%; z-index:1; }
    </style>
</head>

<body>
<div class="shuiyousai">
    <div class="btn"><img src="${uploadPath! }/activity/upload/shuiyousai/baoming-btnh5.png" alt=""/></div>
    <div class="popup-bg"></div>
    <div class="popup">
        <img src="${uploadPath! }/activity/upload/shuiyousai/popboxh5.png" alt=""/>
        <div class="close"></div>
    </div>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-01.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-02.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-03.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-04.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-05.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-06.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-07.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-08.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-09.png" alt=""/>
    <img src="${uploadPath! }/activity/upload/shuiyousai/bgh5-10.png" alt=""/>
</div>

<script src="${staticPath! }/lansha/wap/static/js/zepto.min.js"></script>
<#--埋码-->
${actionAnalysisCode! }
<script>
    $(function () {
        $(".btn").on("click", function () {
            $(".popup,.popup-bg").show();
        })
        $(".close").on("click", function () {
            $(".popup,.popup-bg").hide();
        })
    })
</script>
</body>
</html>