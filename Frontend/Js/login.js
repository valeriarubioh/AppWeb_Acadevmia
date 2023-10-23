const loginForm = document.querySelector(".ingreso__formulario")

const queryString = window.location.search;

const queryParams = parseQueryString(queryString);

loginForm.addEventListener('submit',(e) =>{
    e.preventDefault()
    const userId = document.querySelector('#user').value
    const password = document.querySelector('#password').value
    // inicializamos una variable que nos permita ingresar al sistema
    const Users = JSON.parse(localStorage.getItem('users'))||false
    // validamos si los datos son iguales a los que se registraron
    const validUser = Users && Users.find(user => (user.email === userId || user.username === userId) && user.password === password)
    //
    if(!validUser){
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

        toastr.warning("usuario o contraseña son incorrectos")
    } 
    // pero si los datos son correctos, nos muestra bienvenida
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

    toastr.success(`Bienvenido ${validUser.name}`)
    // aquí almacenamos los datos String a JSON para que se guarden en la lista de objetos
    localStorage.setItem('login_success', JSON.stringify(validUser))
    // TOCA REDIRIGIR A LA PREGUNTA Y SOBREESCRIBIR EL FORMULARIO 
    if(queryParams.redirect === "comunidadRespuesta"){
        setTimeout(function () {
            window.location.href = `comunidadRespuesta.html?id=${queryParams.id}`;
        }, 1000); // Redirige después de 1 segundos
    } else {
        setTimeout(function () {
            window.location.href = 'comunidad.html';
        }, 1000); // Redirige después de 1 segundos
    }
})
