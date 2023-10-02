document.addEventListener("DOMContentLoaded", function () {
  const frase = "El lugar ideal para aprender y enseñar!";
  const fraseAnimada = document.getElementById("hero-titulo-animado");

  // Función para animar la escritura
  function animarEscritura() {
    let i = 0;
    const intervalo = setInterval(function () {
      fraseAnimada.textContent += frase.charAt(i);
      i++;
      if (i > frase.length - 1) {
        clearInterval(intervalo);
      }
    }, 120); // Velocidad de escritura
  }

  animarEscritura(); // Inicia la animación
});
