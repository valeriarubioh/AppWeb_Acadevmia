document.addEventListener("DOMContentLoaded", function () {
  const Preguntas = JSON.parse(localStorage.getItem("preguntas")) || [];

  /* const preguntas = [
    {
      pregunta: "¿Como puedo crear un array en js?",
      descripcion:
        "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido u",
      tags: "javascript",
    },
    {
      pregunta: "¿Como puedo crear un array en js?",
      descripcion:
        "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido u",
      tags: "javascript",
    },
  ];
  localStorage.setItem("preguntas", JSON.stringify(preguntas)); */
  let preguntasHtml = "";

  Preguntas.forEach(function (obj, index) {
    preguntasHtml += `
    <div class="foro__publicadas">
      <div class="foro__pregunta">
          <img src="../../publics/img/user.png">
          <a href="/Frontend/pages/comunidadRespuesta.html?id=${index}" class="foro__ref">
          <h3>${obj.pregunta}</h3>
          <p>${obj.tags}</p>
          </a>
      </div>
    </div>`;
  });
  document.querySelector(".body__preguntas").innerHTML = preguntasHtml;

  const btnPregunta = document.getElementById("btn__pregunta");
  btnPregunta.addEventListener("click", function () {
    // Verificar si el usuario ha iniciado sesión (aquí debes implementar tu lógica de autenticación)
    const user = JSON.parse(localStorage.getItem("login_success")) || false;
    if (!user) {
      window.location.href = "login.html"; // Redirigir a login.html si el usuario no ha iniciado sesión
    }      
    
  });
});
