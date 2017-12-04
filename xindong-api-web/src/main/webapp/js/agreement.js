$(document).ready(function(){
	$(".jian div").bind("click",function(){
		var fontSize = $("#body").css('font-size'); 
		var size = fontSize.substring(0,fontSize.indexOf('px')); 
		if(size > 16){
			size = Number(size) - 2;
			$(".active").removeClass("active");
			$(this).addClass("active");
			$("#body").css('font-size',size+'px'); 
		}else{
			$(".active").removeClass("active");
		}
	});
	$(".jia div").bind("click",function(){
		var fontSize = $("#body").css('font-size'); 
		var size = fontSize.substring(0,fontSize.indexOf('px')); 
		if(size < 26){
			size = Number(size) + 2;
			$(".active").removeClass("active");
			$(this).addClass("active");
			$("#body").css('font-size',size+'px'); 
		}else{
			$(".active").removeClass("active");
		}
	});
});