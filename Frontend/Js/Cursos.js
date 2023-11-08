
const user = JSON.parse(localStorage.getItem("login_success")) || false;
document.addEventListener('DOMContentLoaded', () => {
  const comunidadLink = document.getElementById('comunidadLink');
  
  if (!user) {
    comunidadLink.style.display = 'none';
  } 
});
