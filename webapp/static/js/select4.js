(function($){
    $.fn.extend({
        select4:function(options){
            var defaults = {
                ajax_url:true,
                search_id: ""
            }
            var options = $.extend(defaults, options);
 
            return this.each(function(){
                $(".h2").remove();               
                var mythis = $(this);
                var mythisId = this.getAttribute("id");
 
                $(document).on("click","#div_" + mythisId + " li",function(){
                    mythis.val($(this).text());
                    $("#"+options.search_id).val($(this).attr("id"));
                    $("#div_" + mythisId).remove();
                });
 
                $(document).click(function(event) {
                    $("#div_" + mythisId).remove();
                });
 
                $("#div_" + mythisId).click(function(event) {
                    event.stopPropagation();
                });
 
                mythis.click(function(event) {
                    var val = $(this).val();
                    var mythis = $(this);
                    var mythisId = this.getAttribute("id");
                    var width = $(this).width()+"px";
                    var top = $(this).position().top+$(this).height();
                    var left = $(this).position().left;
                    if(val){
                    	$.ajax({
                    		url:options.ajax_url,
                    		dataType:"json",
                    		data:{name:val},
                    		success:function(json){
                    			if(json.data.length > 0){
                    				var html = '<div class="select4_box" id="div_' + mythisId + '"><ul>';
                    				$.each(json.data,function(k,v){
                                        alert(v.id)
                    					html += '<li id="'+v.id+'">'+v.name+'</li>';
                    				});
                    				html+='</ul></div>'
                    					$("#div_" + mythisId).remove();
                    				mythis.after(html);
                    				$(".select4_box").css({top:top,left:left,width:width});
                    			}
                    		}
                    	});
                    }
                });
 
                mythis.keyup(function(event) {
                    if(event.keyCode==40){
                        var index = $("#div_" + mythisId + " li.active").index()+1;
                        $("#div_" + mythisId + " li").eq(index).addClass('active').siblings().removeClass('active');
                        mythis.val($("#div_" + mythisId + " li.active").text());
                    }else if(event.keyCode==38){
                        var index = $("#div_" + mythisId + " li.active").index()-1;
                        if(index<0){
                            index = $("#div_" + mythisId + " li").length-1;
                        }
                        $("#div_" + mythisId + " li").eq(index).addClass('active').siblings().removeClass('active');
                        mythis.val($("#div_" + mythisId + " li.active").text());
                    }else if(event.keyCode==13){                       
                        event.stopPropagation();
                        mythis.val($("#div_" + mythisId + " li.active").text());
                        $("#"+options.search_id).val("");
                        return false;
                    }else if(event.keyCode==8){
                    	$("#"+options.search_id).val("");
                    }else{
                        mythis.trigger("click");
                    }
                });
 
            });
        }
    });
})(jQuery);