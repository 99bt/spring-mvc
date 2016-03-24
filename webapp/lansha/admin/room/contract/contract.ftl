<#list list?if_exists as item>
<div class="cell-group colgree">
    <div class="cell-wrap tcol_psg1">
        <div class="cell-inner">
            <input type="radio" id="gid-${item.roomId! }" name="id" value="${item.roomId!},${item.userId},${item.mobile!},${item.userName!},${item.gameName!}" data-gamename='${item.roomId! }'>
        </div>
    </div>
    <div class="cell-wrap tcol_psg4">
        <label for="gid-${item.roomId! }">
            <div class="cell-inner">${item.roomId! }</div>
        </label>
    </div>
</div>
</#list>

