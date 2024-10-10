var fileUpload = function(targetObj, objectId, formId, insertUrl, callback, acceptFile) {
	this.targetObj 	= 	targetObj;
	this.objectId	=	objectId;
	this.formId		=	formId;
	this.insertUrl	=	insertUrl;
	this.callback	=	callback;

	// 파일 갯수 최대를 설정하면 최대 갯수만큼만 업로드 가능 (기본 5개까지만 허용)
	this.maxCnt		=	5;					// 최대 업로드 갯수는 기본 5개

	this.btnVisible =	"N";				// 전송버튼 Visiblilty

	this.fileList	=	[];

	this.acceptFile = (acceptFile != undefined ) ? acceptFile :"pdf,hwp";

	this.init();
};

fileUpload.prototype = {

		init : function() {
			this.contextPath 	= 	"";
			this.makeFileUpload();
		},

		makeFileUpload : function() {
			if ($("#" + this.targetObj).data("maxcnt") != null) {
				this.maxCnt	= $("#" + this.targetObj).data("maxcnt");		// maxCount를 따로 설정하면 변경
			}

			if ($("#" + this.targetObj).data("btnvisible") != null) {
				this.btnVisible	= $("#" + this.targetObj).data("btnvisible");
			}

			var htmlStr = "";
			var acceptFileList = this.acceptFile.split(',')


			htmlStr += "<div>";
			htmlStr += "<a href='javascript:;' onclick='"+this.objectId+".toggleFileUpload(this);' title='파일첨부 열기' class = 'attachedFile fl_right'><img src='/images/icons/icon_arrow_down.png' width='26' height='26' alt=' 파일첨부 열기 아이콘' /></a>";
			//htmlStr += "	<button type='button' class='btn_basic fl_right' onClick='"+this.objectId+".toggleFileUpload(this);'>파일업로드 <i class='fas fa-chevron-down'></i><i class='fas fa-chevron-up' style='display:none;'></i></button>";
			htmlStr += "		<div>";
			htmlStr += "			<label for='attachFileList' class='label_file fl_right'>파일선택</label>";
			//htmlStr += "			<input type='file' id='attachFileList' name='' multiple='multiple' title='jpg|jpeg|gif|png|hwp|doc|docx|ppt|pptx|xls|xlsx|zip 파일 추가' accept='.jpg, .jpeg, .gif, .png, .hwp, .doc, .docx, .ppt, .pptx, .xls, .xlsx, .zip' />";
			htmlStr += "			<input type='file' id='attachFileList' name='' multiple='multiple'";
			if(acceptFileList != undefined){
				htmlStr += " title='";

				for(var idx = 0; idx < acceptFileList.length; idx++){
					htmlStr += acceptFileList[idx]
					if(idx < acceptFileList.length -1 ){
						htmlStr += '|';
					}
				}

				htmlStr += " 파일 선택'";
				htmlStr += " accept='";

				for(var idx = 0; idx < acceptFileList.length; idx++){
					htmlStr += '.'+acceptFileList[idx];
					if(idx < acceptFileList.length -1 ){
						htmlStr += ', ';
					}
				}
				htmlStr += "'";
			}
			else {
				htmlStr += "title='pdf|hwp 파일 선택' accept='.pdf, .hwp'";
			}
			htmlStr += "/>";
			htmlStr += "		</div>";

			htmlStr += "	<div class='div-file-body' style='display:none;'>";
			htmlStr += "		<div class='div-drag-files'>";
			htmlStr += "			<p>이곳에 드래그 하세요</p>";
			htmlStr += "			<ul></ul>";
			htmlStr += "		</div>";
			htmlStr += "	</div>";
			htmlStr += "</div>";

			$("#" + this.targetObj).append(htmlStr);

			this.dragFile();
		},

		toggleFileUpload : function(object){
			if($(object).hasClass('active')){
				$(object).removeClass('active');
				$(object).siblings('.div-file-body').hide();
				$(object).children('img').attr('src', '/images/icons/icon_arrow_down.png');
			}
			else {
				$(object).addClass('active');
				$(object).siblings('.div-file-body').show();
				$(object).children('img').attr('src', '/images/icons/icon_arrow_up.png');
			}
		},

		fileUploadShow: function(){
			var $target 	= $("#"+this.targetObj).find("div[class=div-file-body]");
			var object = $target.siblings('a.attachedFile')
			if(!$(object).hasClass('active')){
				$(object).addClass('active');
				$(object).siblings('.div-file-body').show();
				$(object).children('img').attr('src', '/images/icons/icon_arrow_up.png');
			}
		},

		// DragFile
		dragFile : function(){
			var $target 	= $("#"+this.targetObj).find("div[class=div-file-body]");
			var obj 		= this;
			var curCnt 		= 0;
			var avableCnt 	= 0;

			$target.children("div[class=div-drag-files]").on("dragleave", function(e) {
				e.stopPropagation();
				e.preventDefault();
				// 드롭다운 영역 CSS
				$(this).css('background-color', '#FFFFFF');
			}).on("dragenter dragover", function(e) {
				e.stopPropagation();
				e.preventDefault();
				// 드롭다운 영역 CSS
				$(this).css('background-color', '#E3F2FC');
			}).on("drop", function(e) {
				e.preventDefault();
				$(this).css('background-color', '#FFFFFF');

				curCnt = $("#" + obj.targetObj).prev("div.div-file-list").children("ul").children("li").not("li.nohover").length;
				if(obj.fileList != null ) {
					curCnt+=obj.fileList.length;
				}
				avableCnt = obj.maxCnt - curCnt;

				if(!(avableCnt > 0)) {
					wook.app.common.messageBox(null, "업로드 갯수 제한", "파일 갯수가 제한되었습니다.", null);
					return false;
				}

				var files = e.originalEvent.dataTransfer.files;

				if(files.length >= avableCnt) {
					wook.app.common.messageBox(null, "업로드 갯수 제한", "파일 갯수가 제한되었습니다.", null);
					return false;
				}

				//첨부파일 허용 체크
				if(obj.fileAcceptCheck(files)){
					obj.addFiles(files);
					obj.fileUploadShow(this);
				}

			});

			$("#"+this.targetObj).find("input[type='file']").on("change", obj.fileChange.bind(this));

		},

		fileChange : function(e){
			var $target 	= $("#"+this.targetObj).find("div[class=div-file-body]");
			var inputFile   = '';
			var obj 		= this;
			var curCnt 		= 0;
			var avableCnt 	= 0;

			curCnt = $("#" + obj.targetObj).prev("div[class=div-file-list]").children("ul").children("li").not("li.nohover").length;
			if(obj.fileList != null ) {
				curCnt+=obj.fileList.length;
			}
			avableCnt = obj.maxCnt - curCnt;

			if(!(avableCnt > 0)) {
				var htmlStr = '';
				if(obj.chptNo != null) {
					inputFile	= 'attachFileList_'+this.chptNo;
					htmlStr = "			<input type='file' id='attachFileList_"+this.chptNo+"' name='attachFileList_"+this.chptNo+"' multiple='multiple' title='pdf|hwp 파일 추가' accept='.pdf, .hwp'/>";
				} else {
					inputFile	= 'attachFileList';
					htmlStr = "			<input type='file' id='attachFileList' name='' multiple='multiple' title='pdf|hwp 파일 추가' accept='.pdf, .hwp'/>";
				}
				$("#"+inputFile).remove();
				var newinputFile = $("label[for="+inputFile+"]").after(htmlStr);

				$("#"+inputFile).on("change", obj.fileChange.bind(this));
				wook.app.common.messageBox(null, "업로드 갯수 제한", "파일 갯수가 제한되었습니다.", null);
				return false;
			}

			var files = e.target.files;

			if(files.length > avableCnt) {

				var htmlStr = '';
				if(obj.chptNo != null) {
					inputFile	= 'attachFileList_'+this.chptNo;
					htmlStr = "			<input type='file' id='attachFileList_"+this.chptNo+"' name='attachFileList_"+this.chptNo+"' multiple='multiple' title='pdf|hwp 파일 추가' accept='.pdf, .hwp'/>";
				} else {
					inputFile	= 'attachFileList';
					htmlStr = "			<input type='file' id='attachFileList' name='' multiple='multiple' title='pdf|hwp 파일 추가' accept='.pdf, .hwp'/>";
				}
				$("#"+inputFile).remove();
				var newinputFile = $("label[for="+inputFile+"]").after(htmlStr);

				$("#"+inputFile).on("change", obj.fileChange.bind(this));
				wook.app.common.messageBox(null, "업로드 갯수 제한", "파일 갯수가 제한되었습니다.", null);
				return false;
			}

			//첨부파일 허용 체크
			if(obj.fileAcceptCheck(files)){
				obj.addFiles(files);
				obj.fileUploadShow(obj);
			}
		},

		addFiles : function(files) {
			var obj 	= this;
			var $target = $("#"+this.targetObj).find("div[class=div-file-body]");
			var fileArr = Array.prototype.slice.call(files);

			for(var i = 0; i < fileArr.length; i++) {
				this.fileList.push(fileArr[i]);
			}

			//$target.children("div").children('ul').empty();
			$target.children("div[class=div-drag-files]").children("p").hide();
			for(var i = 0; i < files.length; i++) {
				var html = "";
				html += "<li>";
				html += files[i].name
				html += " <a href='javascript:;' title='첨부파일 삭제' class = 'delete' >"
				html += "		<img alt='첨부파일 삭제 아이콘' width='18' height='18' src='/images/icons/icon_x_mark.png'>";
				html += " </a>"
				html += "</li>";

				$target.children("div").children('ul').append(html);
			}

			$target.children("div").children('ul').find('a.delete').on('click',function (){
				var $target 	= $(this).parents('div.div-file-body');
				var index 		= $(this).parents('li').index();
				$(this).parents('li').remove();
				obj.fileList.splice(index, 1);

				var htmlStr = '';
				if(obj.chptNo != null) {
					inputFile	= 'attachFileList_'+obj.chptNo;
					htmlStr = "			<input type='file' id='attachFileList_"+obj.chptNo+"' name='attachFileList_"+obj.chptNo+"' multiple='multiple' title='pdf|hwp 파일 추가' accept='.pdf, .hwp'/>";
				} else {
					inputFile	= 'attachFileList';
					htmlStr = "			<input type='file' id='attachFileList' name='' multiple='multiple' title='pdf|hwp 파일 추가' accept='.pdf, .hwp'/>";
				}
				$("#"+inputFile).remove();
				var newinputFile = $("label[for="+inputFile+"]").after(htmlStr);

				$("#"+inputFile).on("change", obj.fileChange.bind(obj));

			});
		},
		fileAcceptCheck : function(fileList) {
			if(fileList != null ){
				for(var idx = 0; idx < fileList.length; idx++) {
					var fileName = fileList[idx].name;
					var ext = fileName.split('.').pop().toLowerCase();
					if($.inArray(ext,  this.acceptFile.split(',')) == -1) {
						wook.app.common.messageBox(null, "업로드 확장자 제한", this.acceptFile+" 확장자만 등록 가능합니다.", null);
						return false;
					}
					//2. 파일명에 특수문자 체크
				    var pattern =   /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
				    if(pattern.test(fileName) ){
				        //alert("파일명에 허용된 특수문자는 '-', '_', '(', ')', '[', ']', '.' 입니다.");
				    	wook.app.common.messageBox(null, "업로드 파일명 특수문제 제한", fileName+" 파일명에 특수문자를 제거해주세요.<br/>[허용 특수 문자'-', '_', '(', ')', '[', ']', '.' ]", null);
				    	return false;
				    }
				}

			}

			return true;

		}
		,
		insertFile : function() {
			var obj 		= 	this;
			var form		= 	$("#"+this.formId);
	    	var formData 	= 	new FormData(form[0]);
			var $target 	= 	$("#"+this.targetObj).find("div[class=div-file-body]");

			for(var i = 0; i < this.fileList.length; i++){
				formData.append("fileList", this.fileList[i]);
			}

			// 기본계획 장번호
			formData.append('chptNo', this.chptNo);

			// 파일등록
			$.ajax({
				url			: 	obj.contextPath + this.insertUrl,
				enctype		: 	'multipart/form-data',
				type		: 	'post',
				data		: 	formData,
				contentType	: 	false,
				processData	: 	false,
				beforeSend	:	function(XMLHttpRequest) {
					XMLHttpRequest.setRequestHeader("AJAX", "Yes");
				},
				success		: 	function(data, textStatus, XMLHttpRequest){
					if (data.result.status == "success") {
						$target.children("div").children('ul').empty();
						$target.children("div[class=div-drag-files]").children("p").show();

						var htmlStr = '';
						if(obj.chptNo != null) {
							inputFile	= 'attachFileList_'+obj.chptNo;
							htmlStr = "			<input type='file' id='attachFileList_"+obj.chptNo+"' name='attachFileList_"+obj.chptNo+"' multiple='multiple' title='pdf|hwp 파일 추가' accept='.pdf, .hwp'/>";
						} else {
							inputFile	= 'attachFileList';
							htmlStr = "			<input type='file' id='attachFileList' name='' multiple='multiple' title='pdf|hwp 파일 추가' accept='.pdf, .hwp'/>";
						}
						$("#"+inputFile).remove();
						var newinputFile = $("label[for="+inputFile+"]").after(htmlStr);

						$("#"+inputFile).on("change", obj.fileChange.bind(obj));

						obj.fileList = [];
						obj.callback(obj.chptNo);
					}
				},
				error		: 	function(e) {
					wook.app.common.messageBox(null, "파일전송 실패", "파일전송이 실패하였습니다." + e, null);
				}
			});
		},
		beforeSend : function(dataForm, fileListName) {
			var appendFileListName = 'attachFileList';
			//첨부파일리스트 전송 이름
			if(fileListName != undefined) {
				appendFileListName = fileListName;
			}

			if(dataForm != undefined && dataForm.data != undefined ){
				for(var i = 0; i < this.fileList.length; i++){
					dataForm.data.append(appendFileListName, this.fileList[i]);
				}
			}
		}
};
