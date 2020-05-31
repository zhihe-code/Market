/**
 * 当标签出现在脚本代码后面时，不能使用$().click();
 * on绑定事件称之为委托方式绑定，on()也需要标签已经被解析才可以绑定事件
 * 解决方法：调用jQuery的ready函数执行加载完后处理事件绑定
 * @returns
 */
$(function(){//文档加载完成后执行的回调函数
	$("td").on("click",".delbtn",function(){
		console.log("click invoke...");
		return confirm("确定删除吗？这个操作将不可恢复");
	});
	var uids={uids:[]};
	$(".cuid").click(function(){
		uids.uids=[];
		$(".cuid:checked").each(function(){//遍历所有被选择的复选框
			uids.uids.push($(this).val());
		});
	});
	$(".delbtns").click(function(){//多条信息删除
		if(uids.uids.length<=0) return;
		if(confirm("确定要删除所选择的信息吗？这个操作不可恢复")){
			var json = JSON.stringify(uids);
			console.log("======"+json);
			$("#deleteuids").val(json);
			$("#form1").attr("action","/deleteusers");
			$("#form1").submit();
		}
	});
});