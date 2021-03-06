// dropzone
Dropzone.options.dropzone = {
	init : function() {
		var _this = this;
		$('#cancel').click(function() {
			_this.removeAllFiles(true);
			$('#upload').attr('disabled', 'disabled');
		});
		$('#upload').click(function() {
			_this.processQueue();
			$('#overlayUpload + div').hide('blind', 1000);
			$('#overlayUpload').delay(500).show(1000);
		});
		_this.on('uploadprogress', function(file, percentage, bytesSent) {
			$('#progress').text(Math.round(percentage) + ' %');
		});
		_this.on('queuecomplete', function() {
			setTimeout(reload, 2500);
		});
		_this.on('addedfile', function(file) {
			if (file.type == "audio/mpeg" || file.type == "audio/mp3") {
				$('#upload').removeAttr('disabled');
			}
		});
	},
	accept : function(file, done) {
		if (file.type == "audio/mpeg" || file.type == "audio/mp3") {
			done();
		} else {
			done('no supported file');
		}
	},
	acceptedFiles : '.mp3',
	uploadMultiple : true,
	parallelUploads : 50,
	autoProcessQueue : false
};
function reload() {
	location.reload();
};
// delete song
function deleteSong(id) {
	$.ajax({
		url : '/deleteSong/' + id,
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

function updateList(songs) {
	$('#allSongs').empty();
	for (i = 0; i < songs.length; i++) {
		$('#allSongs')
				.append(
						'\n<li class="list-group-item">'
								+ songs[i].artist
								+ ' - '
								+ songs[i].title
								+ '\n<a href="javascript:deleteSong('
								+ songs[i].id
								+ ')" class="grey">\n<span class="glyphicon glyphicon-trash pull-right" '
								+ 'aria-hidden="true"></span>\n</a>\n</li>');
	}
}