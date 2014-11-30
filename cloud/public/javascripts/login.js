document.getElementById("waves").volume = 0.4;

function showLogin() {
	$('.showLogin').click(function() {
		$('.showLogin').remove();
		$('.title').animate({marginTop:'-100px'},800);
		$('.form-signin').delay(100).fadeIn(800);
	});
}

function dismiss() {
	$('.alert').hide(400);
}

$(document).ready(showLogin);