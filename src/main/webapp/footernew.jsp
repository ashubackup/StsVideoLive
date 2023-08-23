<%
	String ani =  (String) session.getAttribute("user");
%>
<footer class="py-3">
	<div class="container">
		<div class="row">
			<div class="col-12 text-center text-white">
				<a href="/" class="footer-logo"><img src="images/logo.png"
					alt="logo" class="img-fluid" /></a>
				<p class="mb-0">
					<span id="year"></span> &copy; Hollywood
				</p>
			</div>
		</div>
	</div>
</footer>
<div class="modal fade cusmodal" id="videoPopup" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="videoPopupLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
       	<button type="button" class="close" onclick="hide()" aria-label="Close">
          <span aria-hidden="true">&times;</span>
       	</button>
      	<div class="modal-body pt-2 px-2 pb-0 modal-video shadow rounded overflow-hidden">
        	<iframe width="100%" height="380" id="Tvideo" src="" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
      	</div>
    </div>
  </div>
</div>
<div class="footerbar bg-black shadow py-2">
        <div class="footerbar-inner d-flex text-center">
            <div class="foot-link flex-grow-1">
                <a href="/NdotoStream" class="text-white"><i class="fa fa-home"></i> Home</a>
            </div>
            
            <div class="foot-link flex-grow-1">
                <a href="#" id="searchfoot" class="text-white"><i class="fa fa-search"></i> Search</a>
            </div>
           
        </div>
    </div>
    
    <div class="searchModal bg-black">
        <div class="searchModal-inner py-3 px-3">
        	<h2 class="text-white pb-2">Search</h2>
            <div class="form-group">
                <a href="#" id="backbtn"><i class="fa fa-angle-left"></i></a>
                <div class="form-inner">
                    <input type="text" class="form-control" placeholder="Enter Keywords" name="" id="searchVal" />
                    <button class="btn btn-light" type="submit"><i class="fa fa-search"></i></button>
                </div>
            </div>
            <div class="recent-search">
               
                <ul id="searchData">
                   
                </ul>
            </div>
        </div>
    </div>


	<!-- Modal -->
	<div class="modal fade under_age" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-body pb-1 text-center">
	        <h4>Your age under 18 or not</h4>
	      </div>
	      <div class="modal-footer justify-content-center border-0 pt-0">
	        <button type="button" onclick="enterageno(<%=ani %>)"  id="no" class="btn btn-secondary" data-dismiss="modal" value="no">No</button>
	        <button type="button" onclick="enterage(<%=ani %>)" id="yes" class="btn btn-primary" value="yes">Yes</button>
	      </div>
	    </div>
	  </div>
	</div>
<script>
	function date() {
		var d = new Date();
		var n = d.getFullYear();
		document.getElementById("year").innerHTML = n;
	}
	date();	
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

	<script type="text/javascript">

	
	
	function pop(){ 
		 
		alert("you enter .....")
	 }
	
	
	$( document ).ready(function() {

// 		startUp();
		
	    $("img").each(function() {
	            $(this).attr("src",$(this).attr("data-src"));
	        $(this).removeAttr("data-src");
	       // console.log($(this)[0].outerHTML);
	    });
	});

	function startUp(){
		var json = {"action":"21",ani:<%=ani%>};
		ajaxJsonProcess(json)
	}
	
	const ajaxJsonProcess = async (jsonR) => {
		$.ajax({
			type: "POST",
// 			url: "/NdotoMTN/CommonApi",
       url: "/CommonApi",
            data: jsonR,
			success: async function(result) {
				console.log(result);
				var jsondata = JSON.parse(result);
				var status = jsondata.status;
				var data = jsondata.data;
				var list = data.list;
			//	$('#staticBackdrop').modal('show');
				//$('#staticBackdrop').show();
// 				console.log(list.length.data.status);
				
			}
		});
	}
	
	</script>

<script src="js/owl.carousel.min.js"></script>
<!-- <script src="js/custom.js"></script> -->
<script src="js/index.js"></script>	
<script src="js/newcustom.js"></script>
<script src="js/newslick.js"></script>
