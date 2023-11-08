// Define un array de preguntas con objetos de ejemplo
const user = JSON.parse(localStorage.getItem("login_success")) || false;


//obtener la el listado de preguntas
function obtenerPreguntasDesdeBackend() {
  let preguntas = [];
  fetch("http://localhost:8080/api/v1/preguntas", {
    method: "GET",
  })
    .then((response) => response.json())
    .then((data) => {
      preguntas = data;
      // Actualiza las preguntas con los datos del backend
      renderPreguntas();
      // Vuelve a renderizar las preguntas en la interfaz
    })
    .catch((error) => console.error("Error al obtener preguntas:", error));
}

function agregarReaccion(index, accion) {
  if (!user) {
    window.location.href = "login.html";
  } else {
    window.location.href = "../../index.html";
  }

  const pregunta = preguntas[index];
  if (pregunta.username === user.username) {
    alert("El mismo usuario no puede reaccionar a su propia publicación");
    return;
  }

  const isLike = accion === "like" ? 1 : 0;

  // Hacer la solicitud para agregar la reacción
  fetch("http://localhost:8080/api/v1/reacciones", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      preguntaId: pregunta.id,
      username: user.username,
      isLike: isLike,
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
      // Actualiza la pregunta con las reacciones actualizadas del backend
      renderPreguntas();
      // Vuelve a renderizar las preguntas en la interfaz
    })
    .catch((error) => console.error("Error al agregar reacción:", error));
}

//funcion para eliminar pregunta con el back
function eliminarPregunta(index) {
  const idPregunta = preguntas[index].id;
  //preguntar sobre la ruta
  fetch(`http://localhost:8080/api/v1/preguntas/${idPregunta}`, {
    method: "DELETE",
  })
    .then(() => {
      preguntas.splice(index, 1);
      // Elimina la pregunta del array local después de eliminarla en el backend
      renderPreguntas();
      // Vuelve a renderizar las preguntas en la interfaz
    })
    .catch((error) => console.error("Error al eliminar la pregunta:", error));
}

function renderPreguntas(filterPregunta, preguntas) {
  let preguntas = [];
  let preguntasHtml = "";
  let preguntasRendered =
    filterPregunta !== undefined ? filterPregunta : preguntas;

  preguntasRendered.forEach(function (obj, index) {
    let reacciones = contarReaccion(obj.reaccion);
    preguntasHtml += `
   <div class="foro__publicadas">
      <div class="foro__pregunta">
        <img src="../../publics/img/user.png">
        <p>${obj.username}</p>
        <button class="foro__reaccion" id="btn__like" onclick="agregarReaccion('${index}', 'like')">
          ${reacciones.likes}
          ${
            obj.reaccion.some(
              (r) => r.username === user.username && r.isLike === 1
            )
              ? "<box-icon type='solid' name='like'></box-icon>"
              : "<i class='bx bx-like'></i>"
          }
        </button>
        <button class="foro__reaccion" id="btn__dislike" onclick="agregarReaccion(${index}, 'dislike')">
          ${reacciones.dislikes}
          ${
            obj.reaccion.some(
              (r) => r.username === user.username && r.isLike === 0
            )
              ? "<box-icon type='solid' name='dislike'></box-icon>"
              : "<i class='bx bx-dislike'></i>"
          }
        </button>
        <a href="/Frontend/pages/comunidadRespuesta.html?id=${index}" class="foro__ref">
          <h3>${obj.pregunta}</h3>
          <p>${obj.tags}</p>
        </a>
        ${
          user && obj.username === user.username
            ? `<button class="eliminar__post" onclick="eliminarPregunta(${index})"><i class="fa-solid fa-trash"></i></button>`
            : ""
        }
      </div>
    </div>`;
  });
  document.querySelector(".body__preguntas").innerHTML = preguntasHtml;
}
renderPreguntas(preguntas);

function contarReaccion(reaccion) {
  if (reaccion.length === 0) {
    return { likes: 0, dislikes: 0 };
  }
  const likes = reaccion.filter((reaccion) => reaccion.isLike === 1).length;
  const dislikes = reaccion.filter((reaccion) => reaccion.isLike === 0).length;
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
    pregunta.pregunta.toLowerCase().includes(consulta.toLowerCase())
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
