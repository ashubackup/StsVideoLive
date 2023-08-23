

	
function addlogging(ani, vid,type) {

	var jsonR = {
		action : '4',
		videoid : vid,
		ani : ani,
		type : type,
		channel: 'NDOTOSTREAM-MTN',
		portal:'sts-mtn'
	};
	
	
console.log(jsonR);

	$.ajax({
		type : "POST",
		url : "ApiServlet",
		data : jsonR,
		success : function(result) {
			console.log(result);

		}
	});
	
	if(type ==='viewed'){
	var url  = "play.jsp?id="+vid+"&cat="+catid;
		window.location.assign(""+url+"");
		
	}else if(type ==='teaser'){
	var url  = "Teaser?id="+vid+"&cat="+catid;
	console.log(url);
		window.location.assign(""+url+"");
		
	} 
}

function hide(){
  
        $('iframe').attr('src', '');
        $('#videoPopup').modal('hide');
   
}
function addtimelogging(ani, id, duration, percentage,cat_id) {

	var jsonR = {
		action : '12',
		videoid : id,
		channel : "NDOTOSTREAM-MTN",
		percentage : percentage,
		duration : duration,
		ani : ani,
		catid:cat_id,
	};
	console.log(jsonR);
	$.ajax({
		type : "POST",
		url : "/CommonApi",
		data : jsonR,
		success : function(result) {
			console.log(result);
		}
	});
}

function addPlaylogging(ani, id, duration, percentage,cat_id) {

	var jsonR = {
		action : '20',
		videoid : id,
		percentage : percentage,
		duration : duration,
		ani : ani,
		catid:cat_id,
	};
	console.log(jsonR);
	$.ajax({
		type : "POST",
		url : "/CommonApi",
		data : jsonR,
		success : function(result) {
			console.log(result);
		}
	});
}


function addWhishlist(id, ani) {

	var jsonR = {
		action : '14',
		videoid : id,
		type : "NDOTOSTREAM-MTN",
		ani : ani
	};
	$.ajax({
		type : "POST",
		url : "/CommonApi",
		data : jsonR,
		success : function(result) {
			console.log("Added to whishlist :: ", result);
			$('#plus').hide();
			$('#check').show();
		}
	});
}


function addViews(id, ani) {

	var jsonR = {
		action : '15',
		videoid : id,
		type : "NDOTOSTREAM-MTN",
		ani : ani
	};
	$.ajax({
		type : "POST",
		url : "/CommonApi",
		data : jsonR,
		success : function(result) {
			console.log("Added to views :: ", result);
		}
	});
}



function UpdateStatus(id,ani,cat){

		var jsonR ={action:'16',videoid:id,ani:ani,type:"NDOTOSTREAM-MTN",category:cat};
		$.ajax({
				type: "POST",
		        url: "/CommonApi",
		        data: jsonR,
		       success:function(result){
		    	   console.log(result);
		    	   	var JsonR = JSON.parse(result);
		    	   	var status = JsonR.status;
		    	   if(status=== '1'){
		    		   if(JsonR.liked === '1'){
		    			   $('#like').removeClass('btn-dark');
			    		   $('#like').addClass('btn-primary');
		    		   }else{		    			   
		    			   $('#like').removeClass('btn-primary');
			    		   $('#like').addClass('btn-dark');
		    		   }
		    		   
		    	   }
	    	   		
		    	   $('#likecnt').html("("+JsonR.count+")");

				
	    	   
		       }
		});
	}
	
	$( "#searchVal" ).keyup(function() {
	 	searchUpdate();
	});
	
	function searchUpdate(){
		var val = $('#searchVal').val();
		var jsonR ={action:'17',data:val};
		if(val== ''){
		 $('#searchData').html('');
		 return;
		}
		$.ajax({
				type: "POST",
		        url: "/CommonApi",
		        data: jsonR,
		       success:function(result){
		    	  
		    	   	var JsonR = JSON.parse(result);
		    	   	var status = JsonR.status;
		    	   if(status=== '1'){
		    		   var data = JsonR.data;
		    		   var html= '';
		    		   $('#searchData').html('');
		    		    
		    		   var list = data.list;
		    		   console.log(list.length);
		    		   if(list.length === 0){
		    		   		  $('#searchData').html('No Result Found');
		    		   }else{
			    		    for(var i=0;i<list.length;i++){
			    		   		html +="<li ><div class='d-flex sImage'><img src='"+list[i].imgurl+"'  class='img-fluid w-10 mr-3' /><a class='flex-grow-1 d-flex justify-content-between align-items-center' href='Play?id="+list[i].videoid+"&cat="+list[i].sub_cat_id+"'>"+list[i].name+" <i class='fa fa-play small ml-2'></i></a></div></li>";
			    		   }
			    		   $('#searchData').append(html);
		    		   }
		    		  
		    		   
		    	   }   
		       }
		});
	}
	
	
	
	function addcomment(ani,id)
{


	var msg=$('#comment').val();
	
    if(msg==''|| msg==null) { return; };
	var jsonr={"action":24,"ani":ani,"msg":msg,"vid":id}
     var html='';
 console.log("Comments  "+jsonr)
	$.ajax({
	url:"/CommonApi",
	type:"Post",
	data:jsonr,
		success:function(result){

		var jsondata=JSON.parse(result);
         console.log(jsondata);
		 var status=jsondata.status;
				if(status == '1')
				{
                     console.log(status);
					 html+='<div class="comments"> <p class="text-white mb-0">user</p><p class="text-white cus-color">'+msg+'</p></div>';
					}
					
						$('.scroll-comment').append(html);

								}
		});

}


function getComment(id)
{

   var jsonr={"action":25,"vid":id}
	 var html='';
  console.log(jsonr)
	$.ajax({
	url:"/CommonApi",
	type:"Post",
	data:jsonr,
		success:function(result){

		var jsondata=JSON.parse(result);

		 var cdata=jsondata.comment;
		  console.log(cdata);
		   for(var i=0;i<cdata.length;i++)
		   {
		    console.log(cdata[i]);
			html+='<div class="comments"> <p class="text-white mb-0">user</p><p class="text-white cus-color">'+cdata[i]+'</p></div>';
			}
//                $('#comments').append(html);
               
               var d1 = document.getElementById("comments");
               d1.insertAdjacentHTML('afterbegin', html);
               
               }
		});

	}
	

