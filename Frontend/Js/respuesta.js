let selectedPregunta;
let respuestas;
const user = JSON.parse(localStorage.getItem("login_success")) || false;
const queryString = window.location.search;
const queryParams = parseQueryString(queryString);
const respuestaTemporal =
  JSON.parse(localStorage.getItem("respuestaTemporal")) || false;

if (respuestaTemporal) {
  selectedPregunta.respuestas.push(respuestaTemporal);
  localStorage.setItem("preguntas", JSON.stringify(Preguntas));
  localStorage.removeItem("respuestaTemporal");
}

if (queryParams.id) {
  obtenerRespuestasDesdeBackend();
}

function obtenerRespuestasDesdeBackend() {
  let request = {
    method: "GET"
  };
  if (user) {
    request = {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${user.token}`
      }
    }
  }
  fetch(`http://localhost:8080/api/v1/respuestas/${queryParams.id}`, request)
    .then((response) => response.json())
    .then((data) => {
      selectedPregunta = data.pregunta;
      respuestas = data.respuestas;
      renderSelectedPregunta();
      renderRespuestas();
    })
    .catch((error) => console.error("Error al obtener respuetas:", error));
}

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
  const isLike = accion === "like" ? 1 : 0;

  fetch("http://localhost:8080/api/v1/reacciones", {
    method: "POST",
    headers: {
      "Allow-Origin": "*",
      "Content-Type": "application/json",
      Authorization: `Bearer ${user.token}`,
    },
    body: JSON.stringify({
      isLike: isLike,
      tipo: "PREGUNTA",
      id: data.respuestas.id
    }),
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error("Error al agregar la reacción");
      }
    })
    .then((updatedPregunta) => {
      preguntas[index] = updatedPregunta;
      renderPreguntas(preguntas);
    })
    .catch((error) => console.error("Error al agregar reacción:", error));

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
    (respuesta.favorito = true)
    fetch("http://localhost:8080/api/v1/reacciones", {
        method: "POST",
        headers: {
          'Allow-Origin': '*',
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.token}`,
        },
        body: JSON.stringify({
          tipo: "Reaccion:{}",
          id: respuesta.id,
        }),
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error("Error al agregar la reacción");
          }
        })
        .then((updatedRespuesta) => {
          respuesta[index] = updatedRespuesta;
          renderSelectedPreguntas(respuesta); 
        })
        .catch((error) => console.error("Error al agregar reacción:", error));
    }
}




function renderRespuestas() {
  let respuestasHtml = "";
  respuestas.forEach(function (obj, index) {
    respuestasHtml += `<div class="foro__publicacion">
      <div class="foro__perfil">
          <img src="../../publics/img/user.png">
          <p>${obj.user.username}</p>
          <button class="foro__reaccion" id="btn__like" onclick="agregarReaccionRespuesta('${index}', 'like')">${
      obj.reacciones.likes
    }<i class='bx bx-like'></i></button>
          <button class="foro__reaccion" id="btn__like" onclick="agregarReaccionRespuesta('${index}', 'dislike')">${
      obj.reacciones.dislikes
    }<i class='bx bx-dislike' ></i></button>
          ${
            user && selectedPregunta.user.username === user.username
              ? `<button class="foro__reaccion" onclick="hacerFavorito(${index})"><i class='bx bx-star'></i>Favorito</button>`
              : ""
          }
          ${
            obj.favorito === true
              ? `<p><i class='bx bx-star'></i>Respuesta Favorita</p>`
              : "<i class='bx bx-star'></i>"
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
  document.querySelector(".body__pregunta").innerHTML = `
      <div class="foro__publicadas">
        <div class="foro__pregunta">
            <img src="../../publics/img/user.png">
            <p>${selectedPregunta.user.username}</p>
            <h3>${selectedPregunta.titulo}</h3>
            <p>${selectedPregunta.tag}</p>
            <button class="foro__reaccion" id="btn__like" onclick="agregarReaccionSelectedPregunta('like')">${selectedPregunta.reacciones.likes}<i class='bx bx-like'></i></button>
            <button class="foro__reaccion" id="btn__dislike" onclick="agregarReaccionSelectedPregunta('dislike')">${selectedPregunta.reacciones.dislikes}<i class='bx bx-dislike' ></i></button>
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


