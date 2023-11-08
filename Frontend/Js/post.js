
document.addEventListener("DOMContentLoaded", function () {
const postForm = document.querySelector(".principal__formulario");

  if (postForm) {
    postForm.addEventListener("submit", async (e) => {
      e.preventDefault();

      const titulo = document.getElementById("input__titulo").value;
      const contenido = document.getElementById("input__texto").value;
      const tags = document.getElementById("input__tags").value;
      const user = JSON.parse(localStorage.getItem("login_success"));

      if (!user) {
        // Redirigir a la página de inicio de sesión si el usuario no está autenticado
        window.location.href = "login.html";
      } else {
        // Crear un objeto para almacenar la pregunta
        const preguntaData = {
          pregunta: titulo,
          descripcion: contenido,
          tag: tags,
          username: user.username,
          respuestas: [],
          reaccion: [],
        };

        try {
          const response = await fetch(
            "http://localhost:8080/api/v1/preguntas",
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + localStorage.getItem("token")
              },
              body: JSON.stringify(preguntaData),
            }
          );

          if (response.ok) {
            console.log(response);
            // Pregunta creada con éxito en la base de datos
            window.location.href = "comunidad.html";
          } else {
            const errorData = await response.json();
            console.error("Error al crear la pregunta:", errorData.message);
          }
        } catch (error) {
          console.error("Error de red:", error);
          // Mostrar un mensaje de error
        }
      }
    });
  }
});
