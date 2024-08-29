function bajaUsuario() {
    fetch("http://localhost:8080/api/user/darseBaja", {
        method: 'GET'
    }).then(res => {
        window.location.href='../../index.html';
    });
}