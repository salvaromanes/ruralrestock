function mostrarViviendas(){
    fetch("http://localhost:8080/api/livingPlace/getAll").then(res => {
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
    </tr>`;
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