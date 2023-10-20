document.addEventListener("DOMContentLoaded", function () {
  const postForm = document.querySelector(".principal__formulario");

  postForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const titulo = document.getElementById("input__titulo").value;
    const contenido = document.getElementById("input__texto").value;
    const tags = document.getElementById("input__tags").value;
    const user = JSON.parse(localStorage.getItem("login_success"));
    const Preguntas = JSON.parse(localStorage.getItem("preguntas")) || [];
    Preguntas.push({
      pregunta: titulo,
      descripcion: contenido,
      tags: tags,
      respuestas: [],
      user: user.username, 
      reaccion:[],
    });
    localStorage.setItem("preguntas", JSON.stringify(Preguntas));
    window.location.href = "comunidad.html";
  });
});
