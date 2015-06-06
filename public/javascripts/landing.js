// dropdown menu
$('.dropdown').click(function() {
	$('.dropdownmenu').toggle('slide', {
		direction : 'up',
		easing : 'easeInOutExpo'
	}, 1000);
});
// user icon
var name = $('span.username').text();
var icon;
if (name == 'admin') {
	icon = 5;
} else if (name.charAt(name.length - 1) === 'a') {
	icon = Math.floor((Math.random() * 4) + 1);
} else {
	icon = Math.floor((Math.random() * 4) + 5);
}
$('.user-picture').attr('src', 'assets/icons/user' + icon + '.png');
// voting
var likedSongs = new Array();
var userID;
function vote(userID, songID) {
	var index = likedSongs.indexOf(songID);
	if (index == -1) {
		likedSongs.push(songID);
	} else {
		likedSongs[index] = 0;
	};

	user = userID;
	model_data = JSON.stringify(userID);
	$.ajax({
		url : '/vote/' + userID + '/' + songID,
		type : 'POST',
		converters : {
			'text json' : true
		},
		success : function(response) {
			response = JSON.parse(response);
			update(response);
		},
		error : function(data, request) {
			alert("FAIL " + data);
		}
	});
}
function update(response) {
	var output = "";
	for (i = 0; i < response.length; i++) {
		output += '<tr><th class="hidden-xs">' + (i + 1) + "</th><td>"
				+ response[i].title + '</td><td>' + response[i].artist
				+ '</td><td class"hidden-xs">' + response[i].genre
				+ '</td><td class="hidden-xs">'
				+ Math.round((response[i].duration / 60) / 100 * 100) + ':'
				+ (response[i].duration % 60);
		if ((response[i].duration % 60) < 10) {
			output += '0';
		}
		output += '</td><td>' + response[i].likes
				+ '</td><td><a href="javascript:vote(' + user + ','
				+ response[i].id + ');" class="green">';
		if (likedSongs.indexOf(response[i].id) == -1) {
			output += '<span class="glyphicon glyphicon-heart-empty"';
		} else {
			output += '<span class="glyphicon glyphicon-heart"';
		}
		output += ' aria-hidden="true"></span></a></td></tr>';
	}
	$('#playlist').empty().append(output);
}