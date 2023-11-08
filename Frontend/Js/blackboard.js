// user me permite capturar los datos, sino hay datos, no podrá iniciar sesión
const user = localStorage.getItem("user") || false;

// Especificamos que si el user no existe que nos redirija al login
if(!user){
    window.location.href = 'login.html'
}