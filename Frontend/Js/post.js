
document.addEventListener("DOMContentLoaded", function () {
    const postForm = document.querySelector('.principal__formulario')

    postForm.addEventListener('submit',(e)=> {
        e.preventDefault()
        
        const titulo = document.getElementById('input__titulo').value;
        const contenido = document.getElementById('input__texto').value;
        const tags = document.getElementById('input__tags').value;

        const nuevaPublicacion = document.createElement('div');
        nuevaPublicacion.innerHTML = `<h3>${titulo}</h3><p>${contenido}</p><p>${tags}</p>`;

        const comunidadContenedor = document.querySelector(".foro__pregunta");
        const comunidadForo = document.querySelector(".foro__publicadas");
        comunidadForo.insertBefore(nuevaPublicacion,comunidadContenedor);
        //Limpiar formulario
        document.getElementById('input__titulo').value = "";
        document.getElementById('input__texto').value = "";
        document.getElementById('input__tags').value = "";

        window.location.href = "comunidad.html";
    });
});