<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${platFormName! }-主播排行</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }"/>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/zhubopaihang.css${staticVersion! }">    
</head>
<body class="boby">
<@lansha.head index="1"/>
<#--conent start-->
<#-- header end-->
<div class="content">
    <div class="emptydiv"></div>
    <div class="biaoge div01">
        <div class="bgheader ">
            <div class="flag">01</div>
            <h2>努力开播冲榜拿奖 iPhone6等你领回家</h2>
        </div>
        <div class="bgbody">
            <div class="img">
                <img src="${staticPath! }/static/lansha/upload/jx01.png" alt="奖品1">
                <p>苹果6s</p>
            </div>
            <div class="img">
                <img src="${staticPath! }/static/lansha/upload/jx02.png" alt="奖品2">
                <p>iPad Air2</p>
            </div>
            <div class="img">
                <img src="${staticPath! }/static/lansha/upload/jx03.png" alt="奖品3">
                <p>iPad Air2</p>
            </div>
            <div class="img">
                <img src="${staticPath! }/static/lansha/upload/jx04.png" alt="奖品4">
                <p>iPad Air2</p>
            </div>
            <div class="clear"></div>
        </div>
        <div class="bgend"></div>
    </div>
    <div class="jiange01"></div>
    <div class="biaoge div02">
        <div class="bgheader ">
            <div class="flag">02</div>
            <h2>主播积分排行榜 <p>排行周期：2016.01.21~01.31</p></h2>

        </div>
        <div class="bgbody">
            <dl>
                <dt class="titles"><strong>排名</strong></dt>
                <dd class="titles"><strong>呢称</strong></dd>
                <dd class="titles"><strong>房间ID</strong></dd>
				<#list rooms?if_exists as item>
					<#if (item_index + 1) % 2 ==0>
						<dt class="titles1-1 ">${(item_index + 1)}</dt>
                		<dd class="titles1-1 ">${item.nickname! }</dd>
                		<dd class="titles1-1 ">${item.idInt! }</dd>
					<#else>
						<dt class="titles1-1 cur">${(item_index + 1)}</dt>
                		<dd class="titles1-1 cur">${item.nickname! }</dd>
                		<dd class="titles1-1 cur">${item.idInt! }</dd>
					</#if>
				</#list>
            </dl>
        </div>
        <div class="bgend">
        <#if myRankScore?default(0)==0>
        	您当前没有排名
        <#else>
        	您当前排名： 第  <em>${myRankScore! }</em>名
        </#if>
            ,提升直播时长对排名有积极影响哦！
        </div>
    </div>
    <div class="jiange02"></div>
    <div class="biaoge div03">
        <div class="bgheader ">
            <div class="flag">03</div>
            <h2>活动规则</h2>
        </div>
        <div class="bgbody">
            <p>本着公平和积极的愿景举办本次活动，希望每位主播能有一个良性的竞争氛围，优秀的主播能获得更好的待遇。</p>
            <p>1  活动时间: 活动开始至活动结束</p>
            <p>2.  主播通过平时直播的表现影响排名, 直播时长、关注数、虾米数、鲜花数等都是影响主播排名的重要因素。</p>
            <p>3.  排名每日过0点后更新，10天为一周期，过周期之后清零重新排名</p>
            <p>4.  排名越高获得的奖品越好，1-3名获得：，4-6名获得：，7-10名获得：，11-20名获得：</p>
            <p>5.  在每一周期结束之后的5个工作日内客服将会联系主播进行奖品发放</p>
            <p>6.一切以作弊、利用bug等手段造成本次活动不正常或不公平运作的现象，蓝鲨TV将介入进行调整和处理</p>
            <p>7.本活动的最终解释权归蓝鲨TV所有</p>
        </div>
        <div class="bgend"></div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
</body>
</html>