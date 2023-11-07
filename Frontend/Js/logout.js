const logout = document.querySelector('#btn__salir')
const logoutRef = document.getElementById('btn__logout')


logout.addEventListener('click', ()=>{
    // aparece msj diciendo que se finalizó la sesión
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-left",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "100",
        "hideDuration": "300",
        "timeOut": "3000",
        "extendedTimeOut": "100",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "slideDown",
        "hideMethod": "slideUp"
    };

    toastr.info("hasta pronto")
    // remueve la info almacenada en almacenamiento local
    localStorage.removeItem('token');
    localStorage.removeItem('user');    
    // cuando finalicemos sesión, nos redirigirá al login
    // Agrega un retraso de 1 segundo (1000 milisegundos) antes de redirigir
    setTimeout(() => {
        // Redirige al usuario a la página de inicio de sesión ("login.html")
        window.location.href = 'login.html';

        // Oculta el botón de salida ("btn__salir")
        document.querySelector('#btn__salir').style.display = 'none';
    }, 1000);
});

document.addEventListener('DOMContentLoaded', function() {
    const logoutRef = document.getElementById('btn__logout');
    
    logoutRef.addEventListener('click', () => {
        // Aparece un mensaje diciendo que se ha finalizado la sesión
        toastr.options = {
            "closeButton": false,
            "debug": false,
            "newestOnTop": false,
            "progressBar": false,
            "positionClass": "toast-top-left",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "100",
            "hideDuration": "300",
            "timeOut": "3000",
            "extendedTimeOut": "100",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "slideDown",
            "hideMethod": "slideUp"
        };
        
        toastr.info("hasta pronto")
        
        // Remueve la información almacenada en el almacenamiento local
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        
        // Agrega un retraso de 1 segundo (1000 milisegundos) antes de redirigir
        setTimeout(() => {
            // Redirige al usuario a la página de inicio de sesión ("login.html")
            window.location.href = '/Frontend/pages/login.html';

            // Oculta el botón de salida ("btn__salir")
            document.querySelector('#btn__salir').style.display = 'none';
        }, 1000);
    });
});