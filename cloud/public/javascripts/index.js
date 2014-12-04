function up() {
	var y = 0;
	if (window.pageYOffset) {
		y = window.pageYOffset;
	} else if (document.body && document.body.scrollTop) {
		y = document.body.scrollTop;
	}
	if (y > 0) {
		window.scrollBy(0, -60);
		setTimeout("up()", 10);
	}
}

function down(div) {
	if (div.scrollTop < div.scrollHeight - div.clientHeight) {
		div.scrollTop += 10; // move down
	}
}

function sendJSON(data, action) {
	model_action = JSON.stringify(action);

	if (action == "++") {
		document.getElementById(data).innerText++;
		
		model_data = JSON.stringify(data);
		$.ajax({
			url : '/vote/' + data,
			type : 'POST',
			contentType : 'application/json',
			data : model_data,
			dataType : 'json html',
			converters : {
				'text json' : true
			},
			success : function(data, response) {
				alert(response);
			},
			error : function(data, request) {
				alert("FAIL " + data);
			},
		});
	}
	else{
		document.getElementById(data).innerText--;
		
		model_data = JSON.stringify(data);
		$.ajax({
			url : '/deVote/' + data,
			type : 'POST',
			contentType : 'application/json',
			data : model_data,
			dataType : 'json html',
			converters : {
				'text json' : true
			},
			success : function(data, response) {
				alert(response);
			},
			error : function(data, request) {
				alert(data);
			},
		});
	}

}