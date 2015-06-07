//delete user
function deleteUser(id) {
	$.ajax({
		url : '/user/' + id,
		type : 'POST',
		converters : {
			'text json' : true
		},
		success : function(response) {
			response = JSON.parse(response);
			updateList(response);
		},
		error : function(data, request) {
			alert("connection failed;" + data);
		},
	});
};
function updateList(response) {
	output = '';
	for (var i = 0; i < response.length; i++) {
		output += '<tr><th>'
				+ response[i].id
				+ '</th><td>'
				+ response[i].username
				+ '</td><td><a href="javascript:deleteUser('
				+ response[i].id
				+ ')" class="grey"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td></tr>';
	}
	$('#userList').empty().append(output);
}
// password
$('#eye').hover(function() {
    $('#password').attr('type', 'text');
    $('#password').animate({borderColor: '#38B89F'}, 100);
});
$('#eye').mouseleave(function() {
    $('#password').attr('type', 'password');
    $('#password').animate({borderColor: '#e5e6e7'}, 50);
});