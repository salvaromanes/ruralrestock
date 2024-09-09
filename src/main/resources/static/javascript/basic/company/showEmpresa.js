function mostrarCIF() {
    fetch("http://localhost:8080/api/company/getAll").then(res => {
        res.json().then(json => {
            users = json;
            imprimirEmpresas();
        });
    });
}

function imprimirEmpresas(){
    let contenedor = document.getElementById("cif");

    contenedor.innerHTML += `<option selected>CIF *</option>`;

    users.forEach(empresa => {
        contenedor.innerHTML += mapearEmpresa(empresa);
    });
}

function mapearEmpresa(empresa){
    return `<option value=${empresa.cif}>${empresa.cif}</option>`
}
