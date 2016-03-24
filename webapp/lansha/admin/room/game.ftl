<#list list?if_exists as item>
<div class="cell-group colgree">
    <div class="cell-wrap tcol_psg1">
        <div class="cell-inner">
            <input type="radio" id="gid-${item.id! }" name="id" value="${item.id! }" data-gamename='${item.name! }'>
        </div>
    </div>
    <div class="cell-wrap tcol_psg4">
        <label for="gid-${item.id! }"><div class="cell-inner">${item.name! }</div></label>
    </div>
</div>
</#list>

<div id="ppg_paginator" style="margin-top: 15px;">
    <div class="p_page">
        <div class="p_pages">
            <a class="page_num" href="javascript:;" data-page="${pageDto.currentPage + 1! }">下一页>></a><a class="page_num " href="javascript:;" data-page="${pageDto.currentPage - 1! }"><<上一页</a>
        </div>
    </div>
</div>
