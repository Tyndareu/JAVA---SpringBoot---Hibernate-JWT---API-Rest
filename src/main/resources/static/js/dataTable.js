// Call the dataTables jQuery plugin
$(document).ready(function () {
  loadTable()
  $('#dataTable').DataTable();
});

async function loadTable() {
  try {
    const response = await fetch('users', {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    });
    const users = await response.json();

    let listHTML = '';
    for (let user of users) {
      let taskHTML = `<tr>
                          <td>${user.id}</td>
                          <td>${user.name} ${user.surname}</td>
                          <td>${user.email}</td>
                          <td>${user.phone}</td>
                          <td>
                            <a onclick="deleteUser(${user.id})" href="#" class="btn btn-danger btn-circle btn-sm">
                               <i class="fas fa-trash"></i>
                            </a>
                          </td>
                        </tr>`;
      listHTML += taskHTML;
    }
    document.querySelector('#dataTable tbody').innerHTML = listHTML;
  } catch (error) {
    console.error(error);
  }
}

async function deleteUser(id) {
  // Confirmar la eliminación del usuario.
  if (!window.confirm(`¿Desea eliminar el usuario con id ${id}?`)) {
    return;
  }

  try {
    const response = await fetch(`user/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    });

    if (response.ok) {
      loadTable(); // Actualizar la tabla después de la eliminación.
    } else {
      // Manejar errores de respuesta HTTP.
      console.error('Error al eliminar el usuario:', response.status, response.statusText);
    }
  } catch (error) {
    // Manejar errores de red u otros errores.
    console.error('Error de red:', error);
  }
}
