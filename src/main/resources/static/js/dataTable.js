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
    console.log(users);

    let listHTML = '';
    for (let user of users) { // Asumiendo que 'users' es la lista de usuarios
      let taskHTML = `<tr>
                          <td>${user.id}</td>
                          <td>${user.name} ${user.surname}</td>
                          <td>${user.email}</td>
                          <td>${user.phone}</td>
                          <td>
                            <a onclick="deleteTask(${user.id})" href="#" class="btn btn-danger btn-circle btn-sm">
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

