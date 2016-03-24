<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
<@lansha.meta />
<title>${platFormName! }</title>
<link rel="stylesheet"
	href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
<link rel="stylesheet"
	href="${staticPath! }/static/lansha/static/css/center.css${staticVersion! }">
<link rel="stylesheet"
	href="${staticPath! }/static/lansha/static/css/style.css${staticVersion! }">
<!--[if lt IE 9]>
    <script ="${staticPath! }/static/lansha/static/js/html5.min.js"></script>
    <![endif]-->
</head>
<body>
	<@lansha.head index="1" showdiv="1"/>

	<div class="container">
		<div class="layout  help-box user-center clearfix">
			<@lansha.userLeft index="2"/>

			<div class="help-right fr">
				<h3 class="history-title">
					<span>我的关注</span>
				</h3>
				<#if list?has_content>
				<ul class="user-gamelist clearfix">
					<#list list?if_exists as item>
                       <li class="game-one fl<#if (item_index + 1) % 4 ==0> last</#if>" >
                          <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }" class="game-pic"  target="_blank">
                             <img class="lazy" src="${uploadPath! }${item.liveImg! }"  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }';" alt="${item.name! }" width="280" height="156" />
                             <div class="live-icon"><img src="${uploadPath! }${item.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="${item.name! }" /></div>
                             <div class="live-bg"></div>
                             <div class="live-begin"></div>
                          </a>
                          <h3 class="room-name">
                             <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }" class="room-name-title">${item.name! }</a>
                             <a href="${contextPath! }/gameLive.html?id=${item.gameId! }" class="fr room-game-name">${item.gameName! }</a>
                          </h3>
                          <div class="room-topic clearfix">
                            <div class="fl">${item.nickname! }</div>
                            <div class="fr"><i class="icon-sprite icon-people fl"></i>${item.onLineNumber! }</div>
                          </div> 
                    <#if item.online == 1> <!--状态层-->
						<div class="live-static static01"></div> 
					</#if> 
						<i class="show-line-b"></i></li> 
				    </#list>
				</ul>
				<#else>
				<div class="no-data">
					<img
						src="${staticPath! }/static/lansha/static/images/nodata.png${staticVersion! }"
						alt=""> 暂无数据
				</div>
				</#if>
			</div>
		</div>
	</div>

	<@lansha.foot /> <#-- 页尾js --> <@lansha.footjs />
</body>
</html>