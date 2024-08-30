document.getElementById("csvIncidencias").addEventListener("click", function() {
    fetch("http://localhost:8080/api/incident/download/csv")
        .then(response => response.blob())
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
            a.download = 'incidencias.csv';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
        })
        .catch(error => console.error('Error al descargar el archivo:', error));
});