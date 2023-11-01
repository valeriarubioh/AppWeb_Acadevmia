const Preguntas = JSON.parse(localStorage.getItem("preguntas"));
const user = JSON.parse(localStorage.getItem("login_success")) || false;
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

// const respuestas = [
//   {
//     texto: "otra respuesta",
//     codigo: "otro codigo",
//     username: "emilypina",
//     reaccion: [
//       {
//         username: "marce",
//         isLike: 1,
//       },
//       {
//         username: "juan",
//         isLike: 0,
//       },
//     ],
//   },
// ];

function agregarReaccionRespuesta(index, accion) {
  if (!user) {
    window.location.href = "login.html";
    return;
  }
  const respuesta = selectedPregunta.respuestas[index];
  if (respuesta.username === user.username) {
    alert("El mismo usuario no puede reaccionar a su propia publicación");
    return;
  }
  const reaccion = respuesta.reaccion;
  let isLike = accion === "like" ? 1 : 0;
  const foundIndex = reaccion.findIndex(
    (reaccion) => reaccion.username === user.username
  );
  if (foundIndex === -1) {
    reaccion.push({
      username: user.username,
      isLike: isLike,
    });
    localStorage.setItem("preguntas", JSON.stringify(Preguntas));
    renderRespuestas();
  } else if (reaccion[foundIndex].isLike === isLike) {
    reaccion.splice(foundIndex, 1);
    localStorage.setItem("preguntas", JSON.stringify(Preguntas));
    renderRespuestas();
  } else {
    alert("Usuario ya ha dado su reacción");
  }
}

function agregarReaccionSelectedPregunta(accion) {
  if (!user) {
    window.location.href = "login.html";
    return;
  }
  if (selectedPregunta.username === user.username) {
    alert("El mismo usuario no puede reaccionar a su propia publicación");
    return;
  }
  const reaccion = selectedPregunta.reaccion;
  let isLike = accion === "like" ? 1 : 0;
  const foundIndex = reaccion.findIndex(
    (reaccion) => reaccion.username === user.username
  );
  if (foundIndex === -1) {
    reaccion.push({
      username: user.username,
      isLike: isLike,
    });
    localStorage.setItem("preguntas", JSON.stringify(Preguntas));
    renderSelectedPregunta();
  } else if (reaccion[foundIndex].isLike === isLike) {
    reaccion.splice(foundIndex, 1);
    localStorage.setItem("preguntas", JSON.stringify(Preguntas));
    renderSelectedPregunta();
  } else {
    alert("Usuario ya ha dado su reacción");
  }
}

function hacerFavorito(index) {
  const respuesta = selectedPregunta.respuestas[index];
  const foundIndex = selectedPregunta.respuestas.findIndex(
    (respuesta) => respuesta.favorito === true
  );
  if (foundIndex >= 0) {
    selectedPregunta.respuestas[foundIndex].favorito = false;
  }
  if (respuesta) {
    (respuesta.favorito = true),
    localStorage.setItem("preguntas", JSON.stringify(Preguntas));
    renderRespuestas();
  }
}

function renderRespuestas() {
  let respuestasHtml = "";
  selectedPregunta.respuestas.forEach(function (obj, index) {
    let reacciones = contarReaccion(obj.reaccion);
    respuestasHtml += `<div class="foro__publicacion">
      <div class="foro__perfil">
          <img src="../../publics/img/user.png">
          <p>${obj.username}</p>
          <button class="foro__reaccion" id="btn__like" onclick="agregarReaccionRespuesta('${index}', 'like')">${
      reacciones.likes
    }<i class='bx bx-like'></i></button>
          <button class="foro__reaccion" id="btn__like" onclick="agregarReaccionRespuesta('${index}', 'dislike')">${
      reacciones.dislikes
    }<i class='bx bx-dislike' ></i></button>
          ${
            user && selectedPregunta.username === user.username
              ? `<button class="foro__reaccion" onclick="hacerFavorito(${index})"><i class='bx bx-star'></i>Favorito</button>`
              : ""
          }
          ${
            obj.favorito === true
              ? `<p><i class='bx bx-star'></i>Respuesta Favorita</p>`
              : ""
          }
      </div>  
      <div class="foro__respuesta">
            <p>${obj.texto}</p>
            <p>${obj.codigo}</p>
            <br>
        </div>
        </div>`;
  });
  document.querySelector(".body__respuestas").innerHTML = respuestasHtml;
}
function contarReaccion(reaccion) {
  if (reaccion.length === 0) {
    return { likes: 0, dislikes: 0 };
  }
  const likes = reaccion.filter((reaccion) => reaccion.isLike === 1).length;
  const dislikes = reaccion.filter((reaccion) => reaccion.isLike === 0).length;
  return { likes: likes, dislikes: dislikes };
}

function renderSelectedPregunta() {
  reacciones = contarReaccion(selectedPregunta.reaccion);
  document.querySelector(".body__pregunta").innerHTML = `
      <div class="foro__publicadas">
        <div class="foro__pregunta">
            <img src="../../publics/img/user.png">
            <p>${selectedPregunta.username}</p>
            <h3>${selectedPregunta.pregunta}</h3>
            <p>${selectedPregunta.tags}</p>
            <button class="foro__reaccion" id="btn__like" onclick="agregarReaccionSelectedPregunta('like')">${reacciones.likes}<i class='bx bx-like'></i></button>
            <button class="foro__reaccion" id="btn__dislike" onclick="agregarReaccionSelectedPregunta('dislike')">${reacciones.dislikes}<i class='bx bx-dislike' ></i></button>
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
  const respuesta = {
    texto: texto,
    codigo: codigo,
    username: user.username,
    reaccion: [],
    favorito: false,
  };
  if (queryParams.id && user) {
    selectedPregunta.respuestas.push(respuesta);
    localStorage.setItem("preguntas", JSON.stringify(Preguntas));
    renderRespuestas();
  } else if (queryParams.id && !user) {
    localStorage.setItem("respuestaTemporal", JSON.stringify(respuesta));
    window.location.href = `login.html?id=${queryParams.id}&&redirect=comunidadRespuesta`;
  }
});
