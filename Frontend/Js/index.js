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

document.querySelector(".menu_hamburguer").addEventListener("click", animationToggle);

let line1 = document.querySelector(".line1__menu");
let line2 = document.querySelector(".line2__menu");
let line3 = document.querySelector(".line3__menu");

function animationToggle(){
  line1.classList.toggle("line1__menu-active");
  line2.classList.toggle("line2__menu-active");
  line3.classList.toggle("line3__menu-active");
}

