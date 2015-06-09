	
	function setHeight() {
	 }

    monthNames = [ "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];

	function timeExam()

	{
		var testt1 = $("#if_pub").context;
		var testt2 = $(".GlobalStyle").context;
		
		

		var theFrame = $("#if_pub", parent);
		
		 $("#if_pub").resize({minHeight: 500, minWidth: 500});
		
		
		//   parent.document.getElementById('if_pub').height = document['body'].offsetHeight;
			
		
		var currentTime = new Date();
				
		var day = currentTime.getDate();
		var month = currentTime.getMonth();
		var year = currentTime.getFullYear();
				
		var hours = currentTime.getHours();
		var minutes = currentTime.getMinutes();
		var seconds = currentTime.getSeconds();
				
		if (minutes < 10){
			minutes = "0" + minutes;
		}
		if (seconds < 10){
			seconds = "0" + seconds;
		}
		
		/*if(hours > 11){
		document.write("PM")
		} else {
		document.write("AM")
		}*/

		$("#data").html(day+" de "+monthNames[month]+" de "+year+". "+hours + ":" + minutes + ":" + seconds + " ");

		setTimeout("timeExam()", 1000);

	}
	
	
timeExam();

	$(function() {
		$('.InitMenu').menuBox({menuWi:179,speedIn:600,speedOut:400,align:'vertical'});
	});


	function overInItemDoc(value) {		
		$('#'+value).css('font-size','12pt');
	}

	function overOutItemDoc(value) {
		$('#'+value).css('font-size','11pt');
	}
	
	function toogleDocs(obj, idDiv){
		var _val = $('#'+idDiv).css('display');
		
		if (_val == 'none') {
			$('#'+idDiv).css('display','block');
			$('#s'+idDiv).html('-');
	    }
		else {		
		    $('#'+idDiv).css('display','none');
		    $('#s'+idDiv).html('+');
		}
	}
	
	