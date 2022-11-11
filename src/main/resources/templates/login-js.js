document.getElementById('sendLogin').addEventListener('click', async () => {
    const inputLogin = document.getElementById('login');
    const inputPassword = document.getElementById('password');
    const login = inputLogin.value;
    const password = inputPassword.value;

    if (login) {
        const response = await fetch(`http://localhost:8080/api/v1/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({login, password})
        });

        const loginRes = await response.json();
        console.log(loginRes);
        const token = loginRes.body.get('token').name;
        console.log(token);
        window.localStorage.setItem('token', token)
        console.log(window.localStorage.getItem('token'));
    }
});