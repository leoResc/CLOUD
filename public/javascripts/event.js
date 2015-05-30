// dateDropper
var date = new Date();
$('#begin').dateDropper({
	format : 'd/m/y',
	lock : 'from',
	placeholder : 'Beginning date',
	color : '#19aa8d',
	minYear : 2015,
	maxYear : 2060
});
$('#end').dateDropper({
	format : 'd/m/y',
	lock : 'from',
	placeholder : 'Ending date',
	color : '#19aa8d',
	minYear : 2015,
	maxYear : 2060
});
// delete event
function deleteEvent(id) {
	$.ajax({
		url : '/events/' + id,
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
function updateList(events) {
	$('#updateList').empty();
	for (i = 0; i < events.length; i++) {
		$('#updateList')
				.append(
						'<tr><th>'
								+ events[i].id
								+ '</th><td>'
								+ events[i].name
								+ '</td><td>'
								+ events[i].password
								+ '</td><td>'
								+ events[i].description
								+ '</td><td>'
								+ events[i].begin
								+ '</td><td>'
								+ events[i].end
								+ '</td><td><a href="javascript:deleteEvent('
								+ events[i].id
								+ ')" class="grey"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></td></tr>');
	}
};