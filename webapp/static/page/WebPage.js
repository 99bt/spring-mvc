
/**
 * 用于分页js
 * 
 * @param page 当前页
 * @param pagination 数量
 * @param pagesize 每页显示数
 * @param num_edge_entries 当前显示数
 * @param num_display_entries 首尾显示数
 */
function Page(page, pagination, pagesize, num_edge_entries, num_display_entries){
	var _this = this;
	
	if(pagination < 1){
		pagination = 1;
	}
	
	var last = parseInt(pagination%pagesize==0?(pagination/pagesize):(pagination/pagesize+1));

	_this.run = function(id,url){
		if(page > last){
			//当前页大于最后一页
			window.location = formatURL(url,last);
			return;
		}
		var obj = document.getElementById(id);
		
		if(page==1){
			//当前页=1
			obj.innerHTML += "<li class='border' disabled>首页</li>";
			obj.innerHTML += "<li class='border' disabled>上一页</li>";
		}else{
			obj.innerHTML += "<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+1+")'>首页</a></li>";
			obj.innerHTML += "<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+(page-1)+")'>上一页</a></li>";
		}

		if(page<=num_edge_entries-2){
			var emd =num_edge_entries+num_display_entries<last?num_edge_entries:last;
			
			for(var i=1;i<=emd;i++){
				if(i==page){
					obj.innerHTML+="<li class='border click'>"+i+"</li> ";
				}else{
					obj.innerHTML+="<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+i+")'>"+i+"</a></li> ";
				}
			}
			if(emd!=last){
				obj.innerHTML+="<li class='border'>...</li>";
				for(var i=last-num_display_entries+1;i<=last;i++){
					obj.innerHTML+="<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+i+")'>"+i+"</a></li> ";
				}
			}
		}else if(page<=last-num_edge_entries+2){
			var i;
			for(i=1;i<=num_display_entries;i++){
				obj.innerHTML+="<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+i+")'>"+i+"</a></li> ";
			}
			
			if(num_display_entries+1<page-parseInt(num_edge_entries/2)){
				obj.innerHTML+="<li class='border'>...</li>";
				i=page-parseInt(num_edge_entries/2);
			}
			for(;i<=page+parseInt(num_edge_entries/2);i++){
				if(i==page){
					obj.innerHTML+="<li class='border click'>"+i+"</li> ";
				}else{
					obj.innerHTML+="<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+i+")'>"+i+"</a></li> ";
				}
			}
			if(last-num_display_entries+1>i){
				obj.innerHTML+="<li class='border'>...</li>";
				i=last-num_display_entries+1
			}
			for(;i<=last;i++){
				obj.innerHTML+="<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+i+")'>"+i+"</a></li> ";
			}
		}else{
			var emd =num_edge_entries+num_display_entries<last?num_display_entries:last;
			
			for(var i=1;i<=emd;i++){
				if(i==page){
					obj.innerHTML+="<li class='border click'>"+i+"</li> ";
				}else{
					obj.innerHTML+="<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+i+")'>"+i+"</a></li> ";
				}
			}
			if(emd!=last){
				obj.innerHTML+="<li class='border'>...</li>";
				for(var i=last-num_edge_entries+1;i<=last;i++){
					if(i==page){
						obj.innerHTML+="<li class='border click'>"+i+"</li> ";
					}else{
						obj.innerHTML+="<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+i+")'>"+i+"</a></li> ";
					}
				}
			}
		}

		if(page==last){
			obj.innerHTML += "<li class='border' disabled>下一页</li>";
			obj.innerHTML += "<li class='border' disabled>尾页</li>";
		}else{
			obj.innerHTML += "<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+ (page+1) +")'>下一页</a></li>";
			obj.innerHTML += "<li class='border'><a href='javascript:void(0);' onclick='pages.formSubmit(\""+url+"\","+last+")'>尾页</a></li>";
		}
		
		obj.innerHTML += "<div class='clear'></div>";
	};
		
	_this.formSubmit = function(url,num){
		var page = document.getElementById("page_form");
		if(page != null){
			page.action = formatURL(url,num);
			page.submit();
		}else{
			window.location.href = formatURL(url,num);
		}
	};
	
	var formatURL = function(url,i){
		return url+i+"";
	};
}