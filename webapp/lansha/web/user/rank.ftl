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
        .rank-title p { margin-left: 10px;font:20px/1.5 "Microsoft YaHei";color: #171717;margin-top: 8px; }
        .rank-title .btn3 { width: 90px;height: 37px;color: #666666;font: 16px/37px "Microsoft YaHei";background: #cccccc;text-align: center;margin-right: 26px;margin-bottom: 8px; }
        .rank-con { padding: 0 23px; }
        .rank-con .p1 { font: 14px/46px "Microsoft YaHei";color: #000;margin-right: 15px; }
        .rank-con .p1 em { color: #3ba2ef; }
        .select_time { padding-left: 35px;width: 170px;height: 33px;font:15px/33px "Microsoft YaHei";border:1px solid #cccccc;border-bottom:none;text-align: center; }
        .rank-table { padding: 0px 0 80px 0;border:1px solid #cccccc; }
        .rank-table .title {background: #ededed;height: 40px;line-height: 40px; border-bottom: 1px solid #ccc;}
        .rank-table .p2.fr {width: 160px;text-align: left;}
        .rank-table .p2 { font: 16px/1.5 "Microsoft YaHei";color: #171717;height: 40px;line-height: 40px;}
        .rank-table li { padding: 0 48px 0 40px; }
        .rank-table .p3 { background: #f5f5f5;font: 16px/50px "Microsoft YaHei";text-align:center;color: #3ba2ef; }
        .rank-table .p3 em {font-size: 22px;}
        .RuleBox {width: 900px;height: 540px;position: fixed;z-index: 99;left: 50%;top: 50%;margin-top: -290px;margin-left: -470px;border: 1px solid #ccc;background: #fff;box-shadow: 0px 1px 5px #ccc;padding: 20px;display: none;}
        .RuleBox iframe {width: 100%;height: 490px;border: 1px solid #e6e5e5;background: #FFF;}
        .RuleBox p {text-align: center;margin-top: 15px;}
        .RuleBox p a {padding: 8px 20px;background: #2e9cee;border-radius: 5px;line-height: 34px;text-align: center;font-size: 16px;color: #fff;}
    </style>
</head>
<body>
<@lansha.head index="1" showdiv="1"/>

<div class="container">
    <div class="layout  help-box user-center clearfix">
    <@lansha.userLeft index="7"/>

        <div class="help-right fr">
            <div class="rank-title clearfix">
                <p class="fl">积分排行</p>
                <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1207079404&site=qq&menu=yes"  style="margin-right:15px"><img border="0" src="${staticPath! }/static/lansha/static/images/apply.png" alt="申请签约" title="申请签约"/></a>
                <a href="javascript:;" id="showRule" class="btn3 fr">规则</a>
            </div>
            <div class="rank-con">
                <div class="clearfix">
                <#if sign==1>
                    <p class="p1 fl">身份：<em>签约主播</em></p>
                    <p class="p1 fl">今日直播总时长：<em>${totalPlayTime!}</em></p>
                    <p class="p1 fl">今日直播有效时长：<em>${playTime!}</em></p>
                    <p class="p1 fl">薪资：<em>${salary!}元/月</em></p>
                <#elseif sign==0>
                    <p class="p1 fl">身份：<em>非签约主播</em></p>
                    <p class="p1 fl">昨日排名：<em><#if dayData??>第${dayData.ranking!}名<#else>暂无排名</#if></em></p>
                    <p class="p1 fl">今日直播总时长：<em>${totalPlayTime!}</em></p>
                    <p class="p1 fl">今日直播有效时长：<em>${playTime!}</em></p>
                    <p class="p1 fl">昨日收益：<em>${salary!}元</em></p>
                    <p class="p1 fl">本月累计收益：<em>${totalSalary!}元</em></p>
                    <p class="p1 fl">本月奖金：<em>${totalBonus!}元</em></p>
                    <p class="p1 fl">本月罚金：<em>${totalForfeit!}元</em></p>
                    <p class="p1 fl">本月最终收益：<em>${lastSalary!}元</em></p>
                    <p class="p1 fl">（最终收益=累计收益+奖金-罚金）</em></p>
                </#if>
                </div>
                <div class="clearfix">
                    <select name="time" id="mySelect" class="select_time fr">
                    <#if timeData?exists>
                        <#list timeData?keys as key>
                            <option value="${key}" <#if key==time>selected</#if>> ${timeData.get(key)} </option>
                        </#list>
                    </#if>

                    </select>
                </div>

                <div class="rank-table">
                    <ul class="clearfix">
                        <li class="clearfix title">
                            <p class="p2 fl">排名</p>
                            <p class="p2 fr">昵称</p>
                        </li>
                    <#list list?if_exists as item>

                        <li class="clearfix">
                            <p class="p2 fl">${(item_index+1)}</p>

                            <p class="p2 fr">${item.userName!}</p>
                        </li>

                    </#list>
                    </ul>
                <#if sign==1>
                    <p class="p3 mgt10">签约主播不参加排名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                <#elseif sign==0>
                    <p class="p3 mgt10">您当前排名：<em>${sortInfo!}</em> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        提升直播时长对排名有积极影响哦！</p>
                </#if>

                </div>
            </div>
        </div>
    </div>
</div>
<div class="RuleBox" id="RuleBox">
    <iframe src="${contextPath! }/integralrule.html" frameborder="0"></iframe>
    <p><a href="javascript:;" id="closeBox">关闭</a></p>
</div>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script>
    $(function () {
        new LSUser.rank().init();

      //显示规则
      $('#showRule, #closeBox').on('click',function(){
          $('.RuleBox').toggle();
      });
    });
</script>
</body>
</html>