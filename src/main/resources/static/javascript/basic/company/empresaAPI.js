function mostrarEmpresas(){
    fetch("http://localhost:8080/api/company/getAll").then(res => {
        res.json().then(json => {
            users = json;
            imprimirEmpresas();
        });
    });
}

function imprimirEmpresas(){
    let contenedor = document.getElementById("cuerpoTabla");

    users.forEach(empresa => {
        contenedor.innerHTML += mapearEmpresa(empresa);
    });
}

function mapearEmpresa(empresa){
    return `<tr>
        <td>${empresa.cif}</td>
        <td>${empresa.nombre}</td>
        <td>${empresa.propietario}</td>
        <td>${empresa.direccion}</td>
        <td>${empresa.descripcion}</td>
        <td>${empresa.municipio}</td>
        <td><input type="submit" class="delete-btn" value="Eliminar empresa"></td>
    </tr>`;
}