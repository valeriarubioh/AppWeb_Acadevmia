//declaramos las variables
// Todo: menu_principal ya está definido y no renderiza index.html, verificar... 
let menu_principal = document.querySelector(".menu_hamburguer");
let line1 = document.querySelector(".line1__menu");
let line2 = document.querySelector(".line2__menu");
let line3 = document.querySelector(".line3__menu");
let menu = document.querySelector(".menu__toggle");
let menuVisible = false;


//creamos la funcion que cambiara las clases para activar la animacion
function animationToggle() {
  line1.classList.toggle("line1__menu-active");
  line2.classList.toggle("line2__menu-active");
  line3.classList.toggle("line3__menu-active");
//si el menu es visible lo cambiamos no visible y viceversa
  if (menuVisible) {
    menu.style.display = "none";
  } else {
    menu.style.display = "flex";
  }
//cambiamos el estado del menu
  menuVisible = !menuVisible;
}
//si la pantalla es menor a 950 quitamos las clases de la animacion
function handleResize() {
  // Verificar el ancho de la pantalla
  if (window.innerWidth > 950) {
    menu.style.display = "none";
    line1.classList.remove("line1__menu-active");
    line2.classList.remove("line2__menu-active");
    line3.classList.remove("line3__menu-active");
    // cambiamos el estado del menu
    menuVisible = false;
  } 
}

// Agregar evento de clic al botón hamburguesa
menu_principal.addEventListener("click", animationToggle);

// Agregar evento de redimensionamiento de ventana
window.addEventListener("resize", handleResize);

// Llamar a handleResize inicialmente para configurar el estado del menú en la carga de la página
handleResize();
document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(localStorage.getItem("login_success")) || false;
  const salida = document.getElementById("btn__salir")
  const pendienteIngreso = document.querySelector(".principal__botones")
  if (user) {
    salida.style.display = "block"
    pendienteIngreso.style.display = "none"
  }

});
