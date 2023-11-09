// user me permite capturar los datos, sino hay datos, no podrá iniciar sesión
const user = JSON.parse(localStorage.getItem('login_success'))||false 

// Especificamos que si el user no existe que nos redirija al login
if(!user){
    window.location.href = 'login.html'
}