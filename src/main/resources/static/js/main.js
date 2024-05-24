$(function() {

	var visualSlider = $('.visual__slide');
	visualSlider.slick({
		centerMode: false,
		centerPadding: '0',
		slidesToShow: 1,
		arrows: false,
		dots: true,
		infinite: true,
		autoplay: true,
		autoplaySpeed: 5000,
	});

	var stSlider = $('.home__statis__slide');
	stSlider.slick({
		fade: true,
		centerMode: false,
		centerPadding: '0',
		slidesToShow: 1,
		autoplay: true,
	    autoplaySpeed: 2000,
		arrows: false,
		dots: false,
		infinite: true
	});

	$('.datepickerfull').datepicker({
		showOtherMonths: true,
	    selectOtherMonths: true,
	    showMonthAfterYear: true,
	    monthNames: ['01','02','03','04','05','06','07','08','09','10','11','12'],
	    beforeShowDay : scheduleShow,
	    onChangeMonthYear: changeMonthYear,
	    beforeShow : changeMonthYear
	});


	// 탭
	$('.js__tab__nav ul li button').on('click', function(){

		var _this = $(this),
			_thisp = _this.parent('li'),
			_thisi = _thisp.index();


			if(_thisp.is('.active')){

			} else {

				_thisp.addClass('active')
				.siblings('li').removeClass('active')
				$('.js__tab .js__tab__content').eq(_thisi).show()
				.siblings('.js__tab__content').hide()
			}

			if(_this.parents('.js__tab__nav').is('.submain_tab')){
				$(this).parents('ul').removeClass()
				.addClass('li'+(_thisi+1)+'')
			}
	});

	var body = $('body');

	$(body).on("click", function(e){
		if(!$(e.target).hasClass("header__top__util") || !$(e.target).closest("div[class=header__top__util]")){
			if($(".btn_top_util").is(".active")){
				$('.top__box').stop().fadeOut("fast")
				$(".btn_top_util").removeClass("active");
			}
		}
	});

	$('.btn_top_util').on('click',function(){
		var _this = $(this);

		if(_this.is('.active')){
			$('.top__box').stop().fadeOut("fast")
			$(this).removeClass('active')
		} else {
			if(_this.is('.top__push')){
				$('.top__box').hide()
				$('.box__push').stop().fadeIn()
			} else if(_this.is('.top__date')){
				$('.top__box').hide()
				$('.box__date').stop().fadeIn()
			} else if(_this.is('.top__msg')){

			} else if(_this.is('.top__profile')){
				$('.top__box').hide()
				$('.box__profile').stop().fadeIn()
			}
			$(this).addClass('active').siblings().removeClass('active')
		}
		return false;
	});

	// GNB에서 검색 아이콘 클릭시
	$('#btnSearchOpen').on('click' , function(){
		if($(this).is('.active')){
			$('#allSearchLayer,.black__dimmed').removeClass('extend');
			$(this).removeClass('active')
		} else {
			$('#allSearchLayer,.black__dimmed').addClass('extend');
			$(this).addClass('active')
		}
	});
});

function scheduleShow(date){
    var m = ("0" + (date.getMonth() + 1)).slice(-2);
    var d = ("0" + (date.getDate())).slice(-2);
    var y = date.getFullYear();
    for(var idx = 0; idx < scheduleDayList.length; idx++){
    	if(scheduleDayList[idx].date == y + '-' + m + '-' + d){
    		return [true, 'ui-state-notice', scheduleDayList[idx].bbssj.replaceAll(',<br>', ',\r\n')];
    		$('.datepickerfull .ui-state-notice a').tooltip();
    	}
    }
   	return [true];

}
function changeMonthYear(year, month, widget) {
	$.ajax({
		url				: wook.app.common.contextPath + "/noticeSchedule.do",
		data			: {
								year : year,
								month: ("0" + month ).slice(-2)
							},
		dataType		: "json",
		type			: "post",
		success			: function(data, textStatus) {
			scheduleDayList.length = 0;
			if(data.scheduleDayList != undefined ){
				scheduleDayList = data.scheduleDayList;
			}
			$('.datepickerfull').datepicker('refresh');
		},
		beforeSend		: function(XMLHttpRequest) {
			XMLHttpRequest.setRequestHeader("AJAX", "Yes");
		},
		error			: function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status == 403 || XMLHttpRequest.status == 207) {
				wook.app.common.sessionTimeout();
			} else {
			}
		}
	});
}
