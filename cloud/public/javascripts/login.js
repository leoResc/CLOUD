document.getElementById("waves").volume = 0.4;

function showLogin() {
	$('.showLogin').click(function() {
		$('.form-signin').show();
		$('.showLogin').remove();
	});
}

$(document).ready(showLogin);