var Pager = function(paginatorId, pagerObjectId, pagerObjectNum, rangeCount){
	this.itemTotal		= 0; 	// 전체 게시물 수
	this.itemPerPage	= 0; 	// 한 페이지당 게시물 수
	this.rangeCount		= (rangeCount == undefined ? 10 : rangeCount); 	// 그룹당 보여줄 페이지 수
	this.selectIndex	= 0; 	//현재 페이지

	this.pageTotalCount	= 0; 	// 전체 페이지 수
	this.pageGroup		= 0; 	// 1,11,21,...각 그룹의 첫페이지 1~10 까지가 한그룹
	this.pageGroupCount	= 0; 	// 페이지 그룹 수

	this.paginatorId	= paginatorId;
	this.pagerObjectId	= pagerObjectId;
	this.num			= pagerObjectNum;	// paginator number
	this.Pager();	// 페이저 초기화

};

Pager.prototype = {

	Pager	: function() {
		// 객체가 없음
		if ($("#" + this.paginatorId).length == 0) return false;

		var htmlStr = "";
		htmlStr += "<a id='firstA" + this.num + "' href='javascript:"+ this.pagerObjectId + ".firstBtnClick()'><img id='firstBtn" + this.num + "' src='' alt='목록 처음으로 가기 아이콘' title='처음' style='margin-top:0px;width:32px;height:33px;cursor:pointer;'></a>";
		htmlStr += "<a id='prevA" + this.num + "' href='javascript:"+ this.pagerObjectId  + ".prevBtnClick()'><img id='prevBtn" + this.num + "' src='' alt='이전 페이지그룹으로 가기 아이콘' title='이전' style='margin-top:0px;width:32px;height:33px;cursor:pointer;'></a>";
		htmlStr += "<div id='paginator" + + this.num + "' style='display:inline;margin-left:5px;'>1 2 3 4<\/div>";
		htmlStr += "<a id='nextA" + this.num + "' href='javascript:"+ this.pagerObjectId +  ".nextBtnClick()'><img id='nextBtn" + this.num + "' src='' alt='다음 페이지그룹으로 가기 아이콘' title='다음' style='margin-top:0px;width:32px;height:33px;cursor:pointer;'></a>";
		htmlStr += "<a id='lastA" + this.num + "' href='javascript:"+ this.pagerObjectId + ".lastBtnClick()'><img id='lastBtn" + this.num + "' src='' alt='목록 끝으로 가기 아이콘' title='끝' style='margin-top:0px;width:32px;height:33px;cursor:pointer;'></a>";

		$("#" + this.paginatorId).empty().append(htmlStr);

	},

	// 전체 게시물 수 입력
	setItemTotal : function(total){
		this.itemTotal = total;
	},

	// 전체 게시물 수 조회
	getItemTotal : function(){
		return this.itemTotal;
	},

	// 전체 페이지 수
	getPageTotalCount : function(){
		return this.pageTotalCount;
	},

	// 현재 페이지 입력
	setSelectIndex : function(index){
		this.selectIndex = index;
	},

	// 현재 페이지 조회
	getSelectIndex : function(){
		return this.selectIndex;
	},

	// 한 페이지당 게시물 수 입력
	setItemPerPage : function(num){
		this.itemPerPage = num;
	},

	// 그룹당 보여줄 페이지 수 입력
	setRangeCount : function(num){
		this.rangeCount = num;
	},

	// 초기화
	pagingInit : function(){
		if(this.itemTotal == 0) {
			this.pageTotalCount = 1;
		} else {
			this.pageTotalCount = Math.ceil(this.itemTotal/this.itemPerPage); // 전체 페이지 수 구하기
			pageGroupCount = Math.ceil(this.pageTotalCount/this.rangeCount); // 페이지 그룹 수

			if(this.selectIndex >= 1 && this.selectIndex <= this.pageTotalCount){
				this.setPageList();
				this.setBtnDisplay();
			}
		}
	},

	// 이미지 이름 가져오기
	getImageName : function(id){
		var image = document.getElementById(id);
		var imgUrl = image.src;
		var imageName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1);
		var dot = imageName.lastIndexOf('.');
		return dot == -1 ? imageName : imageName.substring(0, dot);
	},

	// 처음 페이지 클릭
	firstBtnClick : function(){
		if (this.num == 0) {
			if(this.getImageName("firstBtn") == "firstOn")		this.goPage(1);
		} else {
			if(this.getImageName("firstBtn" + this.num) == "firstOn")	this.goPage(1);
		}
	},

	// 이전 페이지 클릭
	prevBtnClick : function(){
		if (this.num == 0) {
			if(this.getImageName("prevBtn") == "prevOn")		this.goPage(parseInt(this.pageGroup) - parseInt(this.rangeCount));
		} else {
			if(this.getImageName("prevBtn" + this.num) == "prevOn") this.goPage(parseInt(this.pageGroup) - parseInt(this.rangeCount));
		}
	},

	// 다음 페이지 클릭
	nextBtnClick : function(){
		if (this.num == 0) {
			if(this.getImageName("nextBtn") == "nextOn")		this.goPage(parseInt(this.pageGroup) + parseInt(this.rangeCount));
		} else {
			if(this.getImageName("nextBtn" + this.num) == "nextOn") this.goPage(parseInt(this.pageGroup) + parseInt(this.rangeCount));
		}
	},

	// 마지막 페이지 클릭
	lastBtnClick : function(){
		if (this.num == 0) {
			if(this.getImageName("lastBtn") == "lastOn")		this.goPage(this.pageTotalCount);
		} else {
			if(this.getImageName("lastBtn" + this.num) == "lastOn") this.goPage(this.pageTotalCount);
		}
	},

	// 페이지 이동
	goPage : function(pageNum){
		this.setSelectIndex(pageNum);		// 현재 페이지 바꾸기
		this.setBtnDisplay();						// 버튼 block 바꾸기
		this.setPageList();							// 페이징 번호 바꾸기
		searchPage(pageNum, this.num);	// 해당 페이지 보이기
	},

	// 그룹의 첫 페이지 구하기
	getPageGroup : function(){
		this.pageGroup = Math.floor((parseInt(this.selectIndex)-1)/this.rangeCount);
		this.pageGroup = this.pageGroup * this.rangeCount + 1;
	},

	// 페이지 리스트 초기화
	removePaging : function(){
		$("#paginator" + this.num ).empty();
	},

	// 페이지 리스트
	setPageList : function(){
		this.removePaging();
		this.getPageGroup();
		var pageNumber = this.pageGroup;
		for(var i=1; i<=this.rangeCount; i++, pageNumber++){
			if(pageNumber == this.pageTotalCount + 1) // 마지막 페이지인 경우
				i = this.rangeCount + 1; // for문을 빠져 나간다
			else
				this.createPaging(pageNumber);
		}
	},

	// 페이지 리스트 생성
	createPaging : function(pageNumber){
		var htmlStr = "";
		if(pageNumber == this.selectIndex) { //현재 선택한 페이지
			htmlStr = "<a id='a" + pageNumber + "' href='#'>";
			htmlStr += "<span style='cursor:pointer; border:1px solid #e3e3e3; display:inline-block; text-align:center; padding-top:6px; padding-bottom:6px; width:40px; margin-left:-1px; font-weight:bold;'>" + pageNumber + "</span></a>";
		} else {
			htmlStr = "<a id='a" + pageNumber + "' href='javascript:"+this.pagerObjectId+".goPage(" + pageNumber + ")'>";
			htmlStr += "<span style='cursor:pointer; border:1px solid #e3e3e3; display:inline-block; text-align:center; padding-top:6px; padding-bottom:6px; width:40px; margin-left:-1px;'>";
			htmlStr += pageNumber + "</span></a>";
		}

		if(pageNumber == this.pageGroup + this.rangeCount - 1 || pageNumber == this.pageTotalCount)
			htmlStr += "&nbsp;";

		$("#paginator" + this.num ).append(htmlStr);
	},

	// 버튼 보이기, 숨기기
	setBtnDisplay : function(){
		$("#firstBtn" + this.num).css("display", "inline");
		$("#prevBtn" + this.num).css("display", "inline");
		$("#nextBtn" + this.num).css("display", "inline");
		$("#lastBtn" + this.num).css("display", "inline");

		// 처음 페이지 버튼, 이전 페이지 버튼
		var groupNo = Math.floor(parseInt(this.selectIndex - 1)/this.rangeCount) + 1;
		var maxGroupNo = Math.floor(parseInt(this.pageTotalCount - 1)/this.rangeCount) + 1;

		if(groupNo == 1){ // 첫 번째 페이지 선택한 경우
			$("#firstBtn" + this.num).hide();
			$("#prevBtn" + this.num).hide();

			$("#firstA" + this.num).hide();
			$("#prevA" + this.num).hide();
		}else if(groupNo > 1){
			$("#firstBtn" + this.num).show();
			$("#prevBtn" + this.num).show();
			$("#firstBtn" + this.num).attr("src", wook.app.common.contextPath + "/resources/images/page/firstOn.gif");
			$("#prevBtn" + this.num).attr("src",  wook.app.common.contextPath + "/resources/images/page/prevOn.gif");

			$("#firstA" + this.num).show();
			$("#prevA" + this.num).show();
		}

		//다음 페이지 버튼, 마지막 페이지 버튼
		if(groupNo == maxGroupNo){
			$("#nextBtn" + this.num).hide();
			$("#lastBtn" + this.num).hide();
			$("#nextA" + this.num).hide();
			$("#lastA" + this.num).hide();
		}else{
			$("#nextBtn" + this.num).show();
			$("#lastBtn" + this.num).show();
			$("#nextBtn" + this.num).attr("src", wook.app.common.contextPath + "/resources/images/page/nextOn.gif");
			$("#lastBtn" + this.num).attr("src", wook.app.common.contextPath + "/resources/images/page/lastOn.gif");

			$("#nextA" + this.num).show();
			$("#lastA" + this.num).show();
		}

		this.setCursor("firstBtn" + this.num);
		this.setCursor("prevBtn" + this.num);
		this.setCursor("nextBtn" + this.num);
		this.setCursor("lastBtn" + this.num);
	},

	//마우스 커서 모양 바꾸기
	setCursor : function(item){
		if($("#" + item).css("display") != "none")
			$("#" + item).css("cursor","pointer");
		else
			$("#" + item).css("cursor","default");
	},

	//페이징 초기화
	makePaging : function (index, totalCount, rows, totalPageObj, rangeCount) {
		this.setItemTotal(totalCount);		// 전체 항목  수
		this.setItemPerPage(rows);			// 한 페이지당 게시물 수
		if (rangeCount != undefined ) {
			this.setRangeCount(rangeCount);	// 그룹당 보여줄 페이지 수
		}
		this.setSelectIndex(index);			// 현재 페이지 입력
		if(this.getItemTotal() >= 0) {		// 전체 갯수가 0 보다 크면
			this.pagingInit();				// 페이징 초기화
		}

		if (this.getPageTotalCount() > 1) {
			$("#" + this.paginatorId).show();	// 2페이지 이상일 경우에  페이징 보이기
		} else {
			$("#" + this.paginatorId).hide();	// 페이징 보이기
		}

		var htmlStr = "";

		if (this.getItemTotal() > 0) {
			htmlStr+= "검색결과 : ";
			htmlStr+= "<span style=\"font-weight:bold;color:#FF9900;\">";
			htmlStr+= this.getItemTotal().toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			htmlStr+= "항목 (" + this.getSelectIndex() + "/" + this.getPageTotalCount().toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + ")<\/span>&nbsp;페이지";
			totalPageObj.show();
		} else {
			totalPageObj.hide();
		}
		totalPageObj.html(htmlStr);
	}
};
