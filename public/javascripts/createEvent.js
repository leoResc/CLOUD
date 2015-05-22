var begin;
var end;

$("input[name='begin']").change(function() {
	value = Date.parse($("input[name='begin']").val());
	begin = new Date(value);
	compare();
});

$("input[name='end']").change(function() {
	value = Date.parse($("input[name='end']").val());
	end = new Date(value);
	compare();
});

function compare() {
	if (begin < end) {
		$(".btn").removeAttr("disabled");
	} else {
		if (end != null) {
			$('.alert').show(400);
		}
	}
}

function dismiss() {
	$('.alert').hide(400);
}