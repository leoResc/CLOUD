function showLogin() {
	$('.showLogin').click(function() {
		$('.showLogin').remove();
		$('.title').animate({
			marginTop : '-100px'
		}, 800);
		$('.form-signin').delay(100).fadeIn(800);
	});
}

$("#waves").volume = 0.4;
$(document).ready(showLogin);