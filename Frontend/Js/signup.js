// Inicializamos una variable que recibirá los datos del formulario de registro
const signupForm = document.querySelector(".registro__formulario")

// Inicializamos una función que nos permitirá envíar los datos del formulario
signupForm.addEventListener('submit', (e) => 
{
    //  Nos ayuda a evitar que se envíe datos sin dar click al botón
    e.preventDefault()
    // Inicializamos las variables que almacenarán el valor ingresado en los campos
    const name = document.querySelector('#name').value
    const username = document.querySelector('#username').value
    const email = document.querySelector('#email').value
    const password = document.querySelector('#password').value

    const user = {
        name: name,
        username: username,
        email: email,
        password: password
    }

    fetch("http://127.0.0.1:8080/api/auth/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (response.ok) {

            // Aquí especificamos de que si el registro fue correcto, entonces nos aparecerá un msj de alerta de que fue exitoso
            alert('Registro Exitoso!')

            // Si el registro fue exitoso, nos redigirá al login
            window.location.href = 'login.html'
            } else {
                alert('Error al registrar usuario: ' + response.statusText)
            }
        })
        .catch(error => {
            alert('Error al registrar usuario: ' + error)
        });

})