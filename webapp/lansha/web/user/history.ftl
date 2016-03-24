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
    	<@lansha.userLeft index="3"/>
    	
    	<div class="help-right fr">
            <h3 class="history-title">
                <span>观看历史</span>
            </h3>
			<#if list?has_content>
				<ul class="user-gamelist clearfix">
					<#list list?if_exists as item>
						<li class="game-one fl">
		                    <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }" class="game-pic" target="_blank">
		                        <img src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }';" alt=""/>
		                        <div class="live-icon"><img src="${item.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt=""/></div>
		                        <div class="live-bg"></div>
		                        <div class="live-begin"></div>
		                    </a>
                            <h3 class="room-name">
                                <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }" class="room-name-title" target="_blank">${item.name! }</a>
                                <a href="${contextPath! }/gameLive.html?id=${item.gameId! }" class="fr room-game-name">${item.gameName! }</a>
                            </h3>
                            <div class="room-topic clearfix">
                                <div class="fl">${item.nickname! }</div>
                                <div class="fr"><i class="icon-sprite icon-people fl"></i>${item.onLineNumber! }</div>
                            </div>
                            <i class="show-line-b"></i>
                        </li>
					</#list>
	            </ul>
            <#else>
	            <div class="no-data">
	                <img src="${staticPath! }/static/lansha/static/images/nodata.png${staticVersion! }" alt="">
					暂无数据
	            </div>
	    	</#if>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
</body>
</html>