function submit() {
	var model = document.getElementById("input-textarea").value;
	//var modelInBas64 = btoa(model.trim);
	console.log("submit")
	fetch('encode', {
		method: 'POST',
		body: model
	})
		.then((response) => response.body)
		.then((data) => console.log(data));

	//const url = new URL(window.location);
	//url.searchParams.set('foo', modelInBas64);
	//window.history.pushState({}, '', url);
}
function submitWithKeyboardShortcut(e) {
	if (e.keyCode == 13 && e.metaKey) {
		submit()
	}
}

document.getElementById("input-textarea").addEventListener("keydown", submitWithKeyboardShortcut);
document.getElementById("submit").addEventListener("click", submit);
