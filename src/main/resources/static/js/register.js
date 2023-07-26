$(document).ready(function () {

  });
  

  async function createUser() {
    const firstName = document.getElementById('exampleFirstName').value;
    const lastName = document.getElementById('exampleLastName').value;
    const email = document.getElementById('exampleInputEmail').value;
    const password = document.getElementById('exampleInputPassword').value;
    const repeatPassword = document.getElementById('exampleRepeatPassword').value;

    // Validar que las contraseñas coincidan
    if (password !== repeatPassword) {
        alert('Las contraseñas no coinciden. Por favor, inténtalo nuevamente.');
        return;
    }

    const newUser = {
        name: firstName,
        surname: lastName,
        email: email,
        password: password
    };
    try {
        const response = await fetch('users', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        });

        if (response.ok) {
            // Si el usuario se crea correctamente, redirigir a la página de inicio de sesión
            alert('Usuario creado correctamente');
            window.location.href = 'login.html';
        } else {
            alert('Error al crear el usuario');
            console.error('Error al crear el usuario');
        }
    } catch (error) {
        console.error(error);
    }
}
