function mostrarPeticionesViviendas(){
    fetch("http://localhost:8080/api/livingPlace/getAllRequest").then(res => {
        res.json().then(json => {
            users = json;
            imprimirViviendas();
        });
    });
}

function imprimirViviendas(){
    let contenedor = document.getElementById("cuerpoTabla");

    users.forEach(vivienda => {
        contenedor.innerHTML += mapearViviendas(vivienda);
    });
}

function mapearViviendas(vivienda){
    return `<tr>
        <td>${vivienda.direccion}</td>
        <td>${vivienda.propietario}</td>
        <td>${vivienda.tipo}</td>
        <td>${vivienda.precio}</td>
        <td>${vivienda.informacion}</td>
        <td>${vivienda.contacto}</td>
        <td>${vivienda.municipio}</td>
        <td>
            <button type="button" class="btn btn-primary" onclick="eliminarVivienda('${vivienda.direccion}')">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                  <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                </svg>
            </button>
        </td>
        <td>
            <button type="button" class="btn btn-primary" onclick="crearViviendaDesdePeticion('${vivienda.direccion}')">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-circle" viewBox="0 0 16 16">
                  <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
                </svg>
            </button>
        </td>
    </tr>`;
}

function eliminarVivienda(vivienda) {
    fetch('http://localhost:8080/api/livingPlace/deleteById/' + vivienda, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Error al eliminar el empleo');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function crearViviendaDesdePeticion(vivienda) {
    fetch('http://localhost:8080/api/livingPlace/createFromPeticion/' + vivienda, {
        method: 'POST',
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Error al crear el empleo');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

$(document).ready(function() {
  $(".search").keyup(function () {
    var searchTerm = $(".search").val();
    var listItem = $('.results tbody').children('tr');
    var searchSplit = searchTerm.replace(/ /g, "'):containsi('")

  $.extend($.expr[':'], {'containsi': function(elem, i, match, array){
        return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
    }
  });

  $(".results tbody tr").not(":containsi('" + searchSplit + "')").each(function(e){
    $(this).attr('visible','false');
    $(this).hide();
  });

  $(".results tbody tr:containsi('" + searchSplit + "')").each(function(e){
    $(this).attr('visible','true');
    $(this).show();
  });

  var jobCount = $('.results tbody tr[visible="true"]').length;
    $('.counter').text(jobCount + ' item');

  if(jobCount == '0') {$('.no-result').show();}
    else {$('.no-result').hide();}
		  });
});