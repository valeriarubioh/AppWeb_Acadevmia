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

// const preguntas = JSON.parse(localStorage.getItem("preguntas")) || [];
// const user = JSON.parse(localStorage.getItem("login_success")) || false;
// function agregarReaccion(index, accion) {
//   if (!user) {
//     window.location.href = "login.html";
//   }
//   const pregunta = preguntas[index];
//   if (pregunta.username === user.username) {
//     alert("El mismo usuario no puede reaccionar a su propia publicación");
//     return;
//   }
//   const reaccion = pregunta.reaccion;
//   let isLike = accion === "like" ? 1 : 0;

//   const foundIndex = reaccion.findIndex(
//     (reaccion) => reaccion.username === user.username
//   );
//   if (foundIndex === -1) {
//     reaccion.push({
//       username: user.username,
//       isLike: isLike,
//     });
//     localStorage.setItem("preguntas", JSON.stringify(preguntas));
//     renderPreguntas();
//   }else if(reaccion[foundIndex].isLike === isLike){
//     reaccion.splice(foundIndex,1);
//     localStorage.setItem("preguntas", JSON.stringify(preguntas));
//     renderPreguntas();
//   } 
//   else {
//     alert("Usuario ya ha dado su reacción");
//   }
// }