function showLogin() {
	$('.showLogin').click(function() {
		$('.showLogin').remove();
		$('.title').animate({
			marginTop : '-100px'
		}, 800);
		$('.form-signin').delay(100).fadeIn(800);
	});
}
$(document).ready(showLogin);
// background
$(document).ready(function() {
	if ($('#backgroundImage').is(':visible')) {
		var i = Math.floor((Math.random() * 6) + 1);
		$('#backgroundImage').css('background', 'url(assets/img/backgroundMobile' + i + '.jpg) no-repeat');
		$('#backgroundImage').css('background-size', 'cover');
	} else {
		var i = Math.floor((Math.random() * 3) + 1);
		if (i == 1) {
			$('#backgroundVideo:first-child').attr('src', 'assets/img/casle.mp4');
			$('#backgroundVideo:last-child').attr('src', 'assets/img/casle.webm');
			$('#sound').attr('src', 'assets/img/rain.mp3');			
			$("#sound").prop("volume", 0.5);
			$('<audio src="assets/img/thunder.mp3" autoplay loop></audio>').insertAfter('#sound');
		} else {
			$('#backgroundVideo:first-child').attr('src', 'assets/img/beach.mp4');
			$('#backgroundVideo:last-child').attr('src', 'assets/img/beach.webm');
			$('#sound').attr('src', 'assets/img/waves.mp3');			
		}
	}
		
});
