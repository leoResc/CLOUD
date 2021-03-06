// cover photo
var $i = 1;
$('.changeCover').click(function() {

	$('.cover-photo').effect('slide', 'slow');
	if ($i == 11) {
		$i = 1;
	} else {
		$i++;
	}
	$('.cover-photo').attr('src', 'img/cover' + $i + '.jpg');
	$('.cover-photo').show();
});

// jQuery Knob dial
$(function($) {
	$('.knobdial').knob(
			{
				change : function(value) {
					// console.log("change : " + value);
				},
				release : function(value) {
					$.ajax({
						url : '/setVolume/' + value,
						type : 'POST',
					});
				},
				cancel : function() {
					console.log("cancel : ", this);
				},
				/*
				 * format : function (value) { return value + '%'; },
				 */
				draw : function() {
					// "tron" case
					if (this.$.data('skin') == 'tron') {
						this.cursorExt = 0.3;
						var a = this.arc(this.cv), pa, r = 1;
						this.g.lineWidth = this.lineWidth;
						if (this.o.displayPrevious) {
							pa = this.arc(this.v);
							this.g.beginPath();
							this.g.strokeStyle = this.pColor;
							this.g.arc(this.xy, this.xy, this.radius
									- this.lineWidth, pa.s, pa.e, pa.d);
							this.g.stroke();
						}

						this.g.beginPath();
						this.g.strokeStyle = r ? this.o.fgColor : this.fgColor;
						this.g.arc(this.xy, this.xy, this.radius
								- this.lineWidth, a.s, a.e, a.d);
						this.g.stroke();
						this.g.lineWidth = 2;
						this.g.beginPath();
						this.g.strokeStyle = this.o.fgColor;
						this.g.arc(this.xy, this.xy, this.radius
								- this.lineWidth + 1 + this.lineWidth * 2 / 3,
								0, 2 * Math.PI, false);
						this.g.stroke();
						return false;
					}
				}
			});
});

// Toastr
// Toastr
var $dismiss = false;
var $operation;
toastr.options.onHidden = function() {
	if ($dismiss == false) {
		switch ($operation) {
		case 0:
			$('.fullscreen').show('fold', 1000);
			changeMode(0);
			break;
		case 1:
			changeMode(1);
			break;
		case 2:
			changeMode(2);
			break;
		case 3:
			changeMode(3);
			break;
		case 4:
			changeMode(4);
			break;
		case 5:
			changeMode(5);
			break;
		default:
			alert('error');
		}
	} else {
		$('#' + id).removeClass('active');
	}
	$dismiss = false;
};
function changeMode(mode) {
	$.ajax({
		url : '/changeMode/' + mode,
		type : 'POST',
		success : function(response) {
		},
		error : function(data, request) {
			$('#modes button').removeClass('active');
			alert("Error: Failed to apply mode to CLOUD!");
		}
	});
}
$('.shutdown')
		.click(
				function() {
					if (!$(this).hasClass('active')) {
						Command: toastr['error']
								('Do you really want to shut down?<br><button class="btn btn-red btn-cancel-red" onclick="$dismiss=true">Cancel</button>');
					}
				});

$('.thunder')
		.click(
				function() {
					if (!$(this).hasClass('active')) {
						Command: toastr['success']
								('A thunder is starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>');
					}
				});

$('.thunderstorm')
		.click(
				function() {
					if (!$(this).hasClass('active')) {
						Command: toastr['success']
								('The thunderstorm is starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>');
					}
				});

$('.ledtest')
		.click(
				function() {
					if (!$(this).hasClass('active')) {
						Command: toastr['success']
								('LED test ist starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>');
					}
				});

$('.soundreactive')
		.click(
				function() {
					if (!$(this).hasClass('active')) {
						Command: toastr['success']
								('The sound reactive mode is starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>');
					}
				});

$('.pulsating')
		.click(
				function() {
					if (!$(this).hasClass('active')) {
						Command: toastr['success']
								('The pulsating mode is starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>');
					}
				});

// Morris chart
new Morris.Area({
	element : 'chart',
	data : [ {
		x : '2015-01-01',
		value : 0,
		y : 23
	}, {
		x : '2015-02-01',
		value : 10,
		y : 35
	}, {
		x : '2015-03-01',
		value : 59,
		y : 90
	}, {
		x : '2015-04-01',
		value : 135,
		y : 75
	}, {
		x : '2015-05-01',
		value : 98,
		y : 65
	} ],
	xkey : 'x',
	ykeys : [ 'value', 'y' ],
	xLabels : 'day',
	labels : [ 'Songs', 'Value' ],
	fillOpacity : 0.8,
	lineColors : [ '#19aa8d', '#2F4050' ],
	pointFillColors : [ '#2F4050', '#ED5565' ],
	pointSize : 5,
	behaveLikeLine : true,
	resize : true
});

var id;
// buttons active state control
$('button').click(
		function() {
			if (!$(this).hasClass('active')) {
				id = $(this).attr('id');
				// music control
				if (id.indexOf('play') >= 0 || id.indexOf('pause') >= 0
						|| id.indexOf('forward') >= 0) {
					$('div.music-control button').removeClass('active');
					$(this).addClass('active');
					var control = -1;
					if (id.indexOf('play') >= 0) {
						control = 0;
					} else if (id.indexOf('pause') >= 0) {
						control = 1;
					} else if (id.indexOf('forward') >= 0) {
						control = 2;
						$('#forward2').animate({
							backgroundColor : '#5FFFE0'
						}, 500).removeClass('active');
						$('#forward2').animate({
							backgroundColor : '#49C4AB'
						}, 300);
						$('#play1').addClass('active');
						$('#play2').addClass('active');
					}
					$.ajax({
						url : '/musicControl/' + control,
						type : 'POST',
					});
				// light modes
				} else {
					$('#modes button').removeClass('active');
					$(this).addClass('active');
				}
			}
		});

// Tooltips
$(function() {
	$('[data-toggle="tooltip"]').tooltip();
});

var updated = false;
$(document).ready(function() {
	// clock
	setInterval(warning, 2000);
	$.ajax({
		url : '/getTime',
		type : 'GET',
		success : function(response) {
			setTime(response, false);
		}
	});
	// volume
	$.ajax({
		url : '/getVolume',
		type : 'GET',
		success : function(response) {
			$('.knobdial').val(JSON.parse(response)).trigger('change');
		},
	});
});
function warning() {
	if (!updated) {
		var serverTime = new Date();
		serverTime.setHours($('.hour').text());
		serverTime.setMinutes($('.minute').text());
		var clientTime = new Date();
		if (Math.abs(clientTime - serverTime) > 600000) {
			$('#time .infobox-footer').delay(700).animate({
				backgroundColor : '#E16874'
			}, 300);
			$('#time .infobox-footer').animate({
				backgroundColor : '#435361'
			}, 300);
		}
		;
	}
	;
};

$('#updateTime').click(function() {
	$(this).find('.glyphicon-refresh').removeClass('glyphicon-refresh');
});

// update time
$('#updateTime').click(function() {
	var date = new Date();
	$.ajax({
		url : '/updateTime/' + date.getTime(),
		type : 'POST',
		success : function(response) {
			updated = true;
			response = JSON.parse(response);
			setTime(response, true);
		}
	});
});
function setTime(response, updatedTime) {
	var date = new Date();
	date.setTime(response);
	$('.hour').text(
			date.getHours() <= 9 ? '0' + date.getHours() : date.getHours());
	$('.minute').text(
			date.getMinutes() <= 9 ? '0' + date.getMinutes() : date
					.getMinutes());
	var day;
	switch (date.getDay()) {
	case 0:
		day = 'Sunday';
		break;
	case 1:
		day = 'Monday';
		break;
	case 2:
		day = 'Tuesday';
		break;
	case 3:
		day = 'Wednesday';
		break;
	case 4:
		day = 'Thursday';
		break;
	case 5:
		day = 'Friday';
		break;
	case 6:
		day = 'Saturday';
		break;
	}
	var month;
	switch (date.getMonth()) {
	case 0:
		month = 'January';
		break;
	case 1:
		month = 'February';
		break;
	case 2:
		month = 'March';
		break;
	case 3:
		month = 'April';
		break;
	case 4:
		month = 'May';
		break;
	case 5:
		month = 'June';
		break;
	case 6:
		month = 'July';
		break;
	case 7:
		month = 'August';
		break;
	case 8:
		month = 'September';
		break;
	case 9:
		month = 'October';
		break;
	case 10:
		month = 'November';
		break;
	case 11:
		month = 'December';
		break;
	}
	$('.date').text(
			day + ', ' + month + ' ' + date.getDate() + ', '
					+ date.getFullYear());
	if (updatedTime) {
		$('#updateTime').find('.glyphicon').toggleClass('glyphicon-ok');
		$('#time .infobox-footer').delay(700).animate({
			backgroundColor : '#38B89F'
		}, 300);
		$('#time .infobox-footer').animate({
			backgroundColor : '#435361'
		}, 300);
	}
}