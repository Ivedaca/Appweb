// Call the dataTables jQuery plugin
$(document).ready(function() {
     //on ready
});

async function registrarUsuario() {
     let datos = {};
     datos.nombre = document.getElementById('txtNombre').value;
     datos.apellido = document.getElementById('txtApellido').value;
     datos.email = document.getElementById('txtEmail').value;
     datos.telefono = document.getElementById('txtTelefono').value;
     datos.contrasenha = document.getElementById('txtContrasenha').value;

     let repetirContrasenha = document.getElementById('txtRepeatContrasenha').value;

     if (repetirContrasenha != datos.contrasenha) {
        alert('Verifique contraseña!');
        return;
     }

     const request = await fetch('api/usuarios', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
     });
     alert("Registro exitoso!");
     window.location.href = 'login.html';

}