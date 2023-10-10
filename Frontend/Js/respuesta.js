document.addEventListener("DOMContentLoaded", function () {
  const Preguntas = JSON.parse(localStorage.getItem("preguntas"));

  const queryString = window.location.search;

  // Create a function to parse the query string and return an object of query parameters
  function parseQueryString(queryString) {
    const params = {};
    const pairs = (
      queryString[0] === "?" ? queryString.substr(1) : queryString
    ).split("&");

    for (const pair of pairs) {
      const [key, value] = pair.split("=");
      params[decodeURIComponent(key)] = decodeURIComponent(value || "");
    }

    return params;
  }

  const queryParams = parseQueryString(queryString);

  if (queryParams.id) {
    renderRespuestas();
  }

  function renderRespuestas() {
    const selectedPregunta = Preguntas[queryParams.id];
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

  const postForm = document.querySelector(".principal__formulario");

  postForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const texto = document.getElementById("input__texto").value;
    const codigo = document.getElementById("input__codigo").value;

    if (queryParams.id) {
      const selectedPregunta = Preguntas[queryParams.id];
      selectedPregunta.respuestas.push({ texto: texto, codigo: codigo });
      localStorage.setItem("preguntas", JSON.stringify(Preguntas));
      renderRespuestas();
    }
  });
});
