// cover photo
var $i = 1;
$('.changeCover').click(function () {
            
	$('.cover-photo').effect('slide', 'slow');
	if ($i == 10) {
        $i = 1;
    } else {
        $i++;
    }
	$('.cover-photo').attr('src','img/cover' + $i + '.jpg');
	$('.cover-photo').show();
});

// sidebar
$('#menu-toggle').click(function (e) {
    e.preventDefault();
    $('#wrapper').toggleClass('toggled');
});

// jQuery Knob dial
$(function ($) {
	$('.knobdial').knob({
        change : function (value) {
		  //console.log("change : " + value);
		},
        release : function (value) {
            //console.log(this.$.attr('value'));
            console.log("release : " + value);
        },
        cancel : function () {
            console.log("cancel : ", this);
        },
        /*format : function (value) {
            return value + '%';
        },*/
        draw : function () {
			// "tron" case
            if (this.$.data('skin') == 'tron') {
				this.cursorExt = 0.3;
				var a = this.arc(this.cv), pa, r = 1;
				this.g.lineWidth = this.lineWidth;
                    if (this.o.displayPrevious) {
					   pa = this.arc(this.v);
                    this.g.beginPath();
                    this.g.strokeStyle = this.pColor;
                    this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, pa.s, pa.e, pa.d);
                    this.g.stroke();
                }
                
                this.g.beginPath();
                this.g.strokeStyle = r ? this.o.fgColor : this.fgColor;
                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, a.s, a.e, a.d);
                this.g.stroke();
                this.g.lineWidth = 2;
                this.g.beginPath();
                this.g.strokeStyle = this.o.fgColor;
                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
                this.g.stroke();
                return false;
            }
        }
    });
});
        
// Toastr
var $dismiss = false;
var $operation;
toastr.options = {
    'closeButton': false,
    'debug': true,
    'newestOnTop': false,
    'progressBar': true,
    'positionClass': 'toast-top-right',
    'preventDuplicates': false,
    'onclick': null,
    'showDuration': '300',
    'hideDuration': '1000',
    'timeOut': '7000',
    'extendedTimeOut': '1000',
    'showEasing': 'swing',
    'hideEasing': 'linear',
    'showMethod': 'show',
    'hideMethod': 'fadeOut'
};
toastr.options.onHidden = function () {
    if ($dismiss == false) {
        switch ($operation) {
                case 0:
                    $('.fullscreen').show('fold', 1000);
                    break;
                case 1:
                    alert('Message to server: thunder');
                    break;
                case 2:
                    alert('Message to server: ledtest');
                    break;
                case 3:
                    alert('Message to server: thunderstorm');
                    break;
                case 4:
                    alert('Message to server: soundreactive');
                    break;
                case 5:
                alert('Message to server: pulsating');
                    break;
                default:
                    alert('error');
        };
    }
    $dismiss = false;
};
$('.shutdown').click(function () {
    Command: toastr['error']('Do you really want to shut down?<br><button class="btn btn-red btn-cancel-red" onclick="$dismiss=true">Cancel</button>');
});

$('.thunder').click(function () {
    Command: toastr['success']('A thunder is starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>');
});

$('.thunderstorm').click(function () {
    Command: toastr['success']('The thunderstorm is starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>');
});

$('.ledtest').click(function() {
   Command: toastr['success']('LED test ist starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>'); 
});

$('.soundreactive').click(function() {
   Command: toastr['success']('The sound reactive mode is starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>'); 
});

$('.pulsating').click(function() {
   Command: toastr['success']('The pulsating mode is starting...<br><button class="btn btn-green btn-cancel-green" onclick="$dismiss=true;">Cancel</button>'); 
});

// Morris chart
new Morris.Area({
    element: 'chart',
    data: [
        { x: '2015-01-01', value: 0, y: 23 },
        { x: '2015-02-01', value: 10, y: 35 },
        { x: '2015-03-01', value: 59, y: 90 },
        { x: '2015-04-01', value: 135, y: 75 },
        { x: '2015-05-01', value: 98, y: 65 }
    ],
    xkey: 'x',
    ykeys: ['value', 'y'],
    xLabels: 'day',
    labels: ['Songs', 'Value'],
    fillOpacity: 0.8,
    lineColors: ['#19aa8d', '#2F4050'],
    pointFillColors: ['#2F4050', '#ED5565'],
    pointSize: 5,
    behaveLikeLine: true,
    resize: true
});

// button active state
$('button').click(function () {
    $(this).toggleClass('active')
});

// Tooltips
$(function () {
   $('[data-toggle="tooltip"]').tooltip();
});

// slide-menu
var $open = false;
$('.slide-button').click(function() {
    if ($open) {
        $('.slide-menu').animate({left: '210px', backgroundColor: '#EE6D7A'}, 1000, 'easeInOutExpo');
    } else {
        $('.slide-menu').animate({left: '0px', backgroundColor: '#293846'}, 1000, 'easeInOutExpo');
    }
    $open = !$open;
});