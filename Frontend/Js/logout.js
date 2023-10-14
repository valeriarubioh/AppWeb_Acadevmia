const logout = document.querySelector('#btn__salir')

logout.addEventListener('click', ()=>{
    // aparece msj diciendo que se finalizó la sesión
    alert('Hasta pronto')
    // remueve la info almacenada en almacenamiento local
    localStorage.removeItem('login_success')
    // cuando finalicemos sesión, nos redirigirá al login
    window.location.href = 'login.html'
    
})