function toggleContent(id) {
    var element = document.getElementById(id);
    if (element.style.display === "none" || element.style.display === "") {
        element.style.display = "block";
    } else {
        element.style.display = "none";
    }
}

function changeColor() {
    var button = document.getElementById('button');
    button.style.backgroundColor = "#555";
}