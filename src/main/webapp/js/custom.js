$('.slider-big .owl-carousel').owlCarousel({
    loop:true,
    margin:5,
    dots: false,
    autoplay: true,
    autoPlay : 5000,
    items:3,
    center: true,
    responsiveClass:true,
    responsive:{
        0:{
            items:3,
            nav:true
        },
        600:{
            items:3,
            nav:false
        },
        1000:{
            items:3,
            nav:true,
            loop:false
        }
    }
});

$(window).scroll(function() {    
    var scroll = $(window).scrollTop();

     //>=, not <=
    if (scroll >= 300) {
        //clearHeader, not clearheader - caps H
        $("header").addClass("sticky-top");
    }
    else{
    	$("header").removeClass("sticky-top");
    }
}); //missing );


$('.slider-bignew .owl-carousel').owlCarousel({
    loop:false,
    margin:15,
    dots: false,
    responsiveClass:true,
    responsive:{
        0:{
            items:1,
            nav:true
        },
        600:{
            items:1,
            nav:false
        },
        1000:{
            items:1,
            loop:false
        }
    }
})

$('.catslider .owl-carousel').owlCarousel({
    loop:false,
    margin:15,
    dots: false,
    responsiveClass:true,
    responsive:{
        0:{
            items:3,
            nav:true
        },
        600:{
            items:4,
            nav:false
        },
        1000:{
            items:5,
            nav:true,
            loop:false
        }
    }
})
$('.movie-content .owl-carousel').owlCarousel({
    loop:false,
    margin:15,
    dots: false,
    responsiveClass:true,
    responsive:{
        0:{
            items:3
        },
        600:{
            items:3
        },
        1000:{
            items:5
        }
    }
})

$("#searchfoot").click(function(){
  $(".searchModal").toggleClass("show-bar");
});
$("#backbtn").click(function(){
  $(".searchModal").removeClass("show-bar");
});
