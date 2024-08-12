function logoutUsuario() {
    fetch('http://localhost:8080/api/user/logout', {
        method: 'GET',
        credentials: 'include'
    }).then(response => {
        if (response.ok) {
            window.location.href = '/index.html';         }
    });
}