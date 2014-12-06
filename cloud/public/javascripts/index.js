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
		// document.getElementById(data).innerText++;

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
			success : function(response) {
				var answer = JSON.parse(response);
				// response = JSON.parse(response);
				// alert(response[0].title);
				update(answer);
				
			},
			error : function(data, request) {
				alert("FAIL "+data);
			},
		});
	} else {
		// document.getElementById(data).innerText--;

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
				var answer = JSON.parse(response);
				update(answer);
			},
			error : function(data, request) {
				alert(data);
			},
		});
	}

}

function update(response) {
	//$('#liste_songs').innerHTML="";
	var output = "";
	//response = JSON.parse(response);

	for (i = 0; i < response.length; i++) {
		output += '<tr><td>'
				+ response[i].artist
				+ '</td><td> '
				+ response[i].title
				+ '</td> '
				+ '<td> '
				+ response[i].genre
				+ '</td><td class="col-lg-1"><span id= '
				+ response[i].id
				+ ' class="badge"> '
				+ response[i].user_likes
				+ '</td><td class="col-lg-2"><form name="myForm"> '
				+ '<button type="button" class="btn btn-default" '
				+ '	onclick="sendJSON('
				+ response[i].id
				+ ',\'++\')" name="like_me">'
				+ '	<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>  '
				+ '</button><button type="button" class="btn btn-default"> '
				+ '<span class="glyphicon glyphicon-headphones" aria-hidden="true" '
				+ 'onclick="sendJSON(' + response[i].id
				+ ',\'--\')"> </span></button></form></td></tr> ';
	}
	
	document.getElementById('liste_songs').innerHTML = output;
}