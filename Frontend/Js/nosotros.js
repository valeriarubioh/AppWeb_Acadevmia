const user = JSON.parse(localStorage.getItem("login_success")) || false;

document.addEventListener('DOMContentLoaded', () => {
  const comunidadLink = document.getElementById('comunidadLink');
    console.log(user);
  if (!user) {
    comunidadLink.style.display = 'none';
  }
});

let actualImagen = 0;
            const images = document.querySelectorAll('.imagenes');

            function mostrarImagen(index) {
                images.forEach((image, i) => {
                    if (i === index) {
                        image.style.display = 'block';
                    } else {
                        image.style.display = 'none';
                    }
                });
            }

            function nextImage() {
                actualImagen = (actualImagen + 1) % images.length;
                mostrarImagen(actualImagen);
            }

            mostrarImagen(actualImagen);
            setInterval(nextImage, 4000);