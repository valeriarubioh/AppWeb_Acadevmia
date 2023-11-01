const preguntas = JSON.parse(localStorage.getItem("preguntas")) || [];
const user = JSON.parse(localStorage.getItem("login_success")) || false;

// const preguntas = [
//   {
//     pregunta: "Prueba 1",
//     descripcion: "asgfjhkk",
//     tags: "#Java",
//     respuestas: [
//       {
//         texto: "hola",
//         codigo: "codigo",
//         username: "alex",
//       },
//     ],
//     username: "valeriarubio",
//     reaccion: [
//       {
//         username: "alexaaa",
//         isLike: 1,
//       },
//       {
//         username: "lina",
//         isLike: 0,
//       },
//     ],
//   },
// ];
//   localStorage.setItem("preguntas", JSON.stringify(preguntas));

function agregarReaccion(index, accion) {
  if (!user) {
    window.location.href = "login.html";
  }
  const pregunta = preguntas[index];
  if (pregunta.username === user.username) {
    alert("El mismo usuario no puede reaccionar a su propia publicación");
    return;
  }
  const reaccion = pregunta.reaccion;
  let isLike = accion === "like" ? 1 : 0;

  /*const isNotFound =
    reaccion.findIndex((reaccion) => reaccion.username === user.username) === -1
      ? true
      : false;
  if (isNotFound) {
    reaccion.push({
      username: user.username,
      isLike: isLike,
    });*/
  const foundIndex = reaccion.findIndex(
    (reaccion) => reaccion.username === user.username
  );
  if (foundIndex === -1) {
    reaccion.push({
      username: user.username,
      isLike: isLike,
    });
    localStorage.setItem("preguntas", JSON.stringify(preguntas));
    renderPreguntas();
  }else if(reaccion[foundIndex].isLike === isLike){
    reaccion.splice(foundIndex,1);
    localStorage.setItem("preguntas", JSON.stringify(preguntas));
    renderPreguntas();
  } 
  else {
    alert("Usuario ya ha dado su reacción");
  }
}
function eliminarPregunta(index) {
  preguntas.splice(index, 1);
  localStorage.setItem("preguntas", JSON.stringify(preguntas));
  renderPreguntas();
}
function renderPreguntas(filterPregunta) {
  let preguntasHtml = "";
  let preguntasRendered = filterPregunta !== undefined ? filterPregunta : preguntas;

  preguntasRendered.forEach(function (obj, index) {
    let reacciones = contarReaccion(obj.reaccion);
    preguntasHtml += `
    <div class="foro__publicadas">
      <div class="foro__pregunta">
          <img src="../../publics/img/user.png">
          <p>${obj.username}</p>
          <button class="foro__reaccion" id="btn__like" onclick="agregarReaccion('${index}', 'like')">${
      reacciones.likes
    }<i class='bx bx-like'></i></button>
          <button class="foro__reaccion" id="btn__dislike" onclick="agregarReaccion(${index}, 'dislike')">${
      reacciones.dislikes
    }<i class='bx bx-dislike' ></i></button>
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
renderPreguntas();

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
    window.location.href = "login.html"; // Redirigir a login.html si el usuario no ha iniciado sesión
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
