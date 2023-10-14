document.addEventListener("DOMContentLoaded", function () {

  const Preguntas = JSON.parse(localStorage.getItem("preguntas"));  
  const queryString = window.location.search;
  const queryParams = parseQueryString(queryString);
  const selectedPregunta = Preguntas[queryParams.id];
  const respuestaTemporal =
    JSON.parse(localStorage.getItem("respuestaTemporal")) || false;

  if (respuestaTemporal) {
    selectedPregunta.respuestas.push(respuestaTemporal);
    localStorage.setItem("preguntas", JSON.stringify(Preguntas));
    localStorage.removeItem("respuestaTemporal");
  }

  if (queryParams.id) {
    renderSelectedPregunta();
    renderRespuestas();
  }

  function renderRespuestas() {
    let respuestasHtml = "";
    selectedPregunta.respuestas.forEach(function (obj) {
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
  }

  function renderSelectedPregunta() {
    document.querySelector(".body__pregunta").innerHTML = `
      <div class="foro__publicadas">
        <div class="foro__pregunta">
            <img src="../../publics/img/user.png">
            <h3>${selectedPregunta.pregunta}</h3>
            <p>${selectedPregunta.tags}</p>
            </a>
        </div>
        <div class="foro__respuesta">
            <p>${selectedPregunta.descripcion}</p>
            <div class=" foro__reaccion">
            </div>
        </div>
      </div>`;
  }

  const postForm = document.querySelector(".principal__formulario");

  postForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const texto = document.getElementById("input__texto").value;
    const codigo = document.getElementById("input__codigo").value;
    const user = JSON.parse(localStorage.getItem("login_success")) || false;
    const respuesta = { texto: texto, codigo: codigo };
    if (queryParams.id && user) {
      selectedPregunta.respuestas.push(respuesta);
      localStorage.setItem("preguntas", JSON.stringify(Preguntas));
      renderRespuestas();
    } else if (queryParams.id && !user) {
      localStorage.setItem("respuestaTemporal", JSON.stringify(respuesta));
      window.location.href = `login.html?id=${queryParams.id}&&redirect=comunidadRespuesta`;
    }
  });
});
