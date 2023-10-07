
document.addEventListener("DOMContentLoaded", function () {
    const btnPregunta = document.getElementById("btn__pregunta");

    btnPregunta.addEventListener("click", function () {
        // Verificar si el usuario ha iniciado sesión (aquí debes implementar tu lógica de autenticación)
        const user = JSON.parse(localStorage.getItem('login_success'))||false 

        if (user) {
            window.location.href = "postPregunta.html"; // Redirigir a post.html si el usuario ha iniciado sesión
        } else {
            window.location.href = "login.html"; // Redirigir a login.html si el usuario no ha iniciado sesión
        }
    });
});
