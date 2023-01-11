class Model {

	constructor(plain, encoded) {
		this.plain = plain;
		this.encoded = encoded;
	}
}

function submit() {
	var modelPlain = document.getElementById("input-textarea").value;
	encodeModel(modelPlain)
		.then(model => updateWithNewModel(model))
}

function submitWithKeyboardShortcut(e) {
	if (e.keyCode == 13 && e.ctrlKey) {
		submit();
	}
}

function init() {

	document.getElementById("switchLayout").addEventListener("click", switchLayout);
	document.getElementById("input-textarea").addEventListener("keydown", submitWithKeyboardShortcut);
	document.getElementById("submit").addEventListener("click", submit);

	try {
		var pathSegments = window.location.pathname.split('/');
		var indexOfInputSegment = pathSegments.indexOf('input')
		var modelEncoded = pathSegments[indexOfInputSegment + 1];
		/* backward compatiblity: there was a time when input was a query param */
		if (modelEncoded== undefined || modelEncoded === "") {
			const searchParams = new URLSearchParams(window.location.search);
			modelEncoded = searchParams.get("input");
		}
		/* end backward compatiblity */
		
		if (modelEncoded == undefined || modelEncoded === "") {
			submit(); // fallback using hard coded example in html
			return;
		}

		// update components with provided model
		console.log(modelEncoded)
		decodeModel(modelEncoded).then(model => {
			console.log(model);
			updateWithNewModel(model);
		})
	} catch (err) {
		//console.log("Issues with Parsing URL Parameter's - " + err);
	}

}

function encodeModel(modelPlain) {
	function exec(resolutionFunc, rejectionFunc) {
		fetch('/encode', {
			method: 'POST',
			body: modelPlain
		})
			.then((response) => response.text())
			.then(function(modelEncoded) {
				var m = new Model()
				m.encoded = modelEncoded
				m.plain = modelPlain
				resolutionFunc(m)
			});
	}
	return new Promise(exec);
}


function decodeModel(modelEncoded) {
	function exec(resolutionFunc, rejectionFunc) {
		fetch('/decode', {
			method: 'POST',
			body: modelEncoded
		})
			.then((response) => response.text())
			.then(function(data) {
				console.log('gg ' + data);
				var m = new Model()
				m.encoded = modelEncoded
				m.plain = data
				resolutionFunc(m)
			});
	}
	return new Promise(exec);
}

function updateWithNewModel(model) {

	if (model.encoded == undefined || model.plain == undefined) {
		return;
	}
	console.log("updatePage")

	/* update content in textfeld */
	document.getElementById("input-textarea").value = model.plain;

	/* update url */
	var url = new URL(window.location);
	var urlAsString = (window.location.href);
	var urlSegments = urlAsString.split('/');
	var indexOfInputSegment = urlSegments.lastIndexOf('input')
	if (indexOfInputSegment == -1) {
		url = new URL('/input/' + model.encoded, url);
	}
	else {
		if (urlSegments[indexOfInputSegment + 1] == undefined) {
			url = new URL(url, model.encoded);
		}
		else {
			url = new URL(urlAsString.replace(urlSegments[indexOfInputSegment + 1], model.encoded));
		}
	}
	window.history.pushState({}, '', url);

	/* update image link */
	var img = document.getElementById('output-image');
	img.src = window.location.origin + '/image/' + model.encoded

}

function switchLayout() {
	currentLayoutVariant++;
	if (currentLayoutVariant >= layoutVariants.length) {
		currentLayoutVariant = 0;
	}
	var variant = layoutVariants[currentLayoutVariant];
	document.getElementById("content").style.flexDirection = variant[0];
	document.getElementById("input").style.flexBasis = variant[1];
	document.getElementById("output").style.flexBasis = variant[2];

}

var layoutVariants = [
	["row"],
	["column"]]

var currentLayoutVariant = 0;

window.onload = init;