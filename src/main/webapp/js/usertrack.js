

function activityWatcher(ani) {

	//The number of seconds that have passed
	//since the user was active.
	var secondsSinceLastActivity = 0;
	let activeuser = 0;
	//Five minutes. 60 x 5 = 300 seconds.
	var maxInactivity = (60);
	let count = 0;
	//Setup the setInterval method to run
	//every second. 1000 milliseconds = 1 second.
	setInterval(function() {
		secondsSinceLastActivity++;
		activeuser++;
		console.log(secondsSinceLastActivity + ' seconds since the user was last active');
		//if the user has been inactive or idle for longer
		//then the seconds specified in maxInactivity

		if (secondsSinceLastActivity > maxInactivity) {
			console.log('User has been inactive for more than ' + maxInactivity + ' seconds');

			let jsonR = {
				"action": 6,
				"time": "1",
				"status": "Inactive",
				"ani": ani,
				"portal": "mtn"

			}
			let query = new URLSearchParams(jsonR);
			if (count == 0) {
				console.log(jsonR);
				fetch('ApiServlet', { method: 'POST', body: query }).
					then(response => { return response.json() }).then(data => console.log(data)).catch(error => console.log(error))
				count++;
			}
			activeuser = 0;
		}


		else if (activeuser == 60) {
			count = 0;
			let jsonR = {
				"action": 6,
				"time": "1",
				"status": "Active",
				"ani": ani,
				"portal": "mtn"
			}
			console.log(jsonR);
			let query = new URLSearchParams(jsonR);
			console.log('User has been Active Last ' + activeuser + ' seconds');
			fetch('ApiServlet', { method: 'POST', body: query }).
				then(response => { return response.json() }).then(data => console.log(data)).catch(error => console.log(error))
			activeuser = 0;

		}
	}, 1000);

	//The function that will be called whenever a user is active
	function activity() {
		//reset the secondsSinceLastActivity variable
		//back to 0
		secondsSinceLastActivity = 0;
	}

	//An array of DOM events that should be interpreted as
	//user activity.
	var activityEvents = [
		'mousedown', 'mousemove', 'keydown',
		'scroll', 'touchstart'
	];

	//add these events to the document.
	//register the activity function as the listener parameter.
	activityEvents.forEach(function(eventName) {
		document.addEventListener(eventName, activity, true);
	});


}


