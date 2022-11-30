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
		var url_string = (window.location.href);
		var url = new URL(url_string);
		var modelEncoded = url.searchParams.get("input");
		if (modelEncoded != undefined) {
			console.log(modelEncoded)
			decodeModel(modelEncoded).then(model => {
				console.log(model);
				updateWithNewModel(model
				);
			})
		}
	} catch (err) {
		//console.log("Issues with Parsing URL Parameter's - " + err);
	}

}

function encodeModel(modelPlain) {
	function exec(resolutionFunc, rejectionFunc) {
		fetch('encode', {
			method: 'POST',
			body: modelPlain
		})
			.then((response) => response.text())
			.then(function (data) {
				var m = new Model()
				m.encoded = data
				m.plain = modelPlain
				resolutionFunc(m)
			});
	}
	return new Promise(exec);
}


function decodeModel(modelEncoded) {
	function exec(resolutionFunc, rejectionFunc) {
		fetch('decode', {
			method: 'POST',
			body: modelEncoded
		})
			.then((response) => response.text())
			.then(function (data) {
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
	console.log("updatePage with " + model)

	/* update content in textfeld */
	document.getElementById("input-textarea").value = model.plain;

	/* update url */
	const url = new URL(window.location);
	url.searchParams.set('input', model.encoded);
	window.history.pushState({}, '', url);

	/* update image link */
	var img = document.getElementById('output-image');
	img.src = 'image/?input=' + model.encoded

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
	["row", '33%', '66%'],
	["row", '50%', '50%'],
	["column", '33%', '66%'],
	["column", '50%', '50%']]

var currentLayoutVariant = 0;

window.onload = init;