// user me permite capturar los datos, sino hay datos, no podrá iniciar sesión
const user = JSON.parse(localStorage.getItem('login_success'))||false 

// Especificamos que si el user no existe que nos redirija al login
if(!user){
    window.location.href = 'login.html'
}
// logout nos permite salir el sistema, cuando demos al boton "cerrar sesión"
const logout = document.querySelector("#btn__salir")

logout.addEventListener('click', ()=>{
    // aparece msj diciendo que se finalizó la sesión
    alert('Hasta pronto')
    // remueve la info almacenada en almacenamiento local
    localStorage.removeItem('login_success')
    // cuando finalicemos sesión, nos redirigirá al login
    window.location.href = 'login.html'


    
})