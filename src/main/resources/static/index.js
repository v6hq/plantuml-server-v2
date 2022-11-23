function submit() {
	var model = document.getElementById("input-textarea").value;
	//var modelInBas64 = btoa(model.trim);
	console.log("submit")
	fetch('encode', {
		method: 'POST',
		body: model
	})
		.then((response) => response.json())
		.then(function(data) {
			console.log(data)
			const url = new URL(window.location);
			url.searchParams.set('model', data.modelEncoded);
			window.history.pushState({}, '', url);
			
			var img = document.getElementById('output-image');
			img.src='image/?model='+data.modelEncoded
		});

}
function submitWithKeyboardShortcut(e) {
	if (e.keyCode == 13 && e.metaKey) {
		submit();
	}
}

document.getElementById("input-textarea").addEventListener("keydown", submitWithKeyboardShortcut);
document.getElementById("submit").addEventListener("click", submit);

//http://www.plantuml.com/plantuml/png/SoWkIImgAStDuNBAJrBGjLDmpCbCJbMmKiX8pSd9vt98pKi1IW80