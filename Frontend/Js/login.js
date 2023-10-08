const loginForm = document.querySelector(".ingreso__formulario")

loginForm.addEventListener('submit',(e) =>{
    e.preventDefault()

    const userId = document.querySelector('#user').value
    const password = document.querySelector('#password').value
    // inicializamos una variable que nos permita ingresar al sistema
    const Users = JSON.parse(localStorage.getItem('users'))||false
    // validamos si los datos son iguales a los que se registraron
    const validUser = Users.find(user => (user.email === userId || user.username === userId) && user.password === password)
    //
    if(!validUser){
        return alert('Usuario y/o contraseña son incorrectos')
    } 
    // pero si los datos son correctos, nos muestra bienvenida
    alert(`Bienvenido ${validUser.name}`)
    // aquí almacenamos los datos String a JSON para que se guarden en la lista de objetos
    localStorage.setItem('login_success', JSON.stringify(validUser))
    // Si el registro fue exitoso, nos redigirá al index
    window.location.href = 'comunidad.html'
})