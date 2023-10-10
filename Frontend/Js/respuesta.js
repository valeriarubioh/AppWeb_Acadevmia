document.addEventListener("DOMContentLoaded", function () {
  const Preguntas = JSON.parse(localStorage.getItem("preguntas"));
  
  // let preguntasHtml = "";

  // Preguntas.forEach(function (obj, index) {
  //   preguntasHtml += `<div class="foro__publicadas">
  //   <div class="foro__pregunta">
  //       <img src="../../publics/img/user.png">
  //       <a href="/Frontend/pages/comunidadRespuesta.html" class="foro__ref">
  //       <h3>${obj.pregunta}</h3>
  //       <p>${obj.tags}</p>
  //       </a>
  //   </div>
  //   <div class="foro__respuesta">
  //       <p>${obj.descripcion}</p>
  //       <div class=" foro__reaccion">
  //       </div>
  //   </div>
  //   </div>`;
  // });
  // document.querySelector(".body__preguntas").innerHTML = preguntasHtml;

  const Respuestas = JSON.parse(localStorage.getItem("respuestas")) || [];

  /*const respuestas = [
    {
      texto: "Crear un Array tiene la sig estructura: ",
      codigo:
        'localStorage.setItem("respuestas", JSON.stringify(respuestas));',
    },
    {
      texto: "Crear un Array tiene la sig estructura: ",
      codigo:
        'localStorage.setItem("respuestas", JSON.stringify(respuestas));',
    },
  ];
  localStorage.setItem("respuestas", JSON.stringify(respuestas)); */
  const postForm = document.querySelector(".principal__formulario");

  postForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const texto = document.getElementById("input__texto").value;
    const codigo = document.getElementById("input__codigo").value;
    const Respuestas = JSON.parse(localStorage.getItem("respuestas")) || [];
    Respuestas.push({ texto: texto, codigo: codigo });
    localStorage.setItem("respuestas", JSON.stringify(Respuestas));
    window.location.href = "comunidadRespuesta.html";
  });
  
  let respuestasHtml = "";

  Respuestas.forEach(function (obj, index) {
    respuestasHtml += `<div class="foro__publicacion">
    <div class="foro__respuesta">        
        <p>${obj.texto}</p>
        <p>${obj.codigo}</p>
        <br>      
    </div>
    <div class="foro__perfil">
      <img src="../../publics/img/user.png">
      <div class=" foro__reaccion"></div>
    </div>
    </div>`;
  });
  document.querySelector(".body__respuestas").innerHTML = respuestasHtml;

});
