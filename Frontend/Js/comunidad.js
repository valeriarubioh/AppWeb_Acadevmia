// Define un array de preguntas con objetos de ejemplo
const user = JSON.parse(localStorage.getItem("login_success")) || false;
let preguntas;

//obtener la el listado de preguntas
function obtenerPreguntasDesdeBackend() {
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
  fetch("http://localhost:8080/api/v1/preguntas", request)
    .then((response) => response.json())
    .then((data) => {
      // Actualiza las preguntas con los datos del backend
      preguntas = data;
      renderPreguntas(data);
      // Vuelve a renderizar las preguntas en la interfaz
    })
    .catch((error) => console.error("Error al obtener preguntas:", error));
}
obtenerPreguntasDesdeBackend();

function agregarReaccion(index, accion) {
  if (!user) {
    window.location.href = "login.html";
    return;
  }

  const pregunta = preguntas[index];
  if (pregunta.user.username === user.username) {
    alert("El mismo usuario no puede reaccionar a su propia publicación");
    return;
  }

  const isLike = accion === "like" ? 1 : 0;

  fetch("http://localhost:8080/api/v1/reacciones", {
    method: "POST",
    headers: {
      'Allow-Origin': '*',
      "Content-Type": "application/json",
      "Authorization": `Bearer ${user.token}`,
    },
    body: JSON.stringify({
      isLike: isLike,
      tipo: "PREGUNTA",
      id: pregunta.id,
    }),
  })
    .then((response) => {
      if (response.ok) {
        obtenerPreguntasDesdeBackend();
      }
    })
    .catch((error) => console.error("Error al agregar reacción:", error));
}


//funcion para eliminar pregunta con el back
function eliminarPregunta(index) {
  const idPregunta = preguntas[index].id;
  //preguntar sobre la ruta
  fetch(`http://localhost:8080/api/v1/preguntas/${idPregunta}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${user.token}`,
    },
  })
    .then(() => {
      preguntas.splice(index, 1);
      // Elimina la pregunta del array local después de eliminarla en el backend
      renderPreguntas();
      // Vuelve a renderizar las preguntas en la interfaz
    })
    .catch((error) => console.error("Error al eliminar la pregunta:", error));
}

function renderPreguntas(filterPregunta) {
  let preguntasHtml = "";
  let preguntasRendered =
    filterPregunta !== undefined ? filterPregunta : preguntas;

  preguntasRendered.forEach(function (obj, index) {
    // let reacciones = contarReaccion(obj.reaccion);
    preguntasHtml += `
   <div class="foro__publicadas">
      <div class="foro__pregunta">
        <img src="../../publics/img/user.png">
        <p>${obj.user.username}</p>
        <button class="foro__reaccion" id="btn__like" onclick="agregarReaccion('${index}', 'like')">
          ${obj.reacciones.likes}
          ${
            obj.reacciones.userHasReacted == "LIKE" && obj.reacciones.userHasReacted !== "NONE"
              ? "<box-icon type='solid' name='like'></box-icon>"
              : "<i class='bx bx-like'></i>"
          }
        </button>
        <button class="foro__reaccion" id="btn__dislike" onclick="agregarReaccion(${index}, 'dislike')">
          ${obj.reacciones.dislikes}
          ${
            obj.reacciones.userHasReacted == "DISLIKE" && obj.reacciones.userHasReacted !== "NONE"
              ? "<box-icon type='solid' name='dislike'></box-icon>"
              : "<i class='bx bx-dislike'></i>"
          }
        </button>
        <a href="/Frontend/pages/comunidadRespuesta.html?id=${obj.id}" class="foro__ref">
          <h3>${obj.titulo}</h3>
          <p>${obj.tag}</p>
        </a>
        ${
          user && user.username === obj.user.username
            ? `<button class="eliminar__post" onclick="eliminarPregunta(${index})"><i class="fa-solid fa-trash"></i></button>`
            : ""
        }
      </div>
    </div>`;
  });
  document.querySelector(".body__preguntas").innerHTML = preguntasHtml;
}
// renderPreguntas(preguntas);

function contarReaccion(reaccion) {
  if (reaccion.length === 0) {
    return { likes: 0, dislikes: 0 };
  }
    const likes = obj.reacciones.filter((reaccion) => reaccion.isLike === 1).length;
    const dislikes = obj.reacciones.filter((reaccion) => reaccion.isLike === 0).length;
    return { likes: likes, dislikes: dislikes };
}

const btnPregunta = document.getElementById("btn__pregunta");
btnPregunta.addEventListener("click", function () {
  if (!user) {
    window.location.href = "login.html";
    // Redirigir a login.html si el usuario no ha iniciado sesión
  }
});
function filtro(consulta) {
  return preguntas.filter((pregunta) =>
    pregunta.titulo.toLowerCase().includes(consulta.toLowerCase())
  );
}
const consulta = document.getElementById("buscador");
consulta.addEventListener("keyup", function (event) {
  if (event.key === "Enter") {
    event.preventDefault();
    const consultaTexto = consulta.value;
    const resultadoFiltro = filtro(consultaTexto);
    renderPreguntas(resultadoFiltro);
  }
});
