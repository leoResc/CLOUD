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