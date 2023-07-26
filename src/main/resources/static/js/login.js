// Call the dataTables jQuery plugin
$(document).ready(function () {

});

async function login() {
    const userForm = {
        email: document.getElementById('exampleInputEmail').value,
        password: document.getElementById('exampleInputPassword').value
    };

    try {
        const request = await fetch('login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userForm)
        });

        const response = await request.text();

        if (response !== "Invalid credentials") {
            sessionStorage.setItem('token', response);
            sessionStorage.setItem('email', userForm.email);
            window.location.href = 'tables.html';
        } else {
            alert("Invalid credentials. Try again");
        }
    } catch (error) {
        console.error(error);
        alert("There was an error in the request. Please try again later.");
    }
}


