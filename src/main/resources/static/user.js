const urlUser = 'http://localhost:8080/api/user';
const urlLogin = 'http://localhost:8080/api/loginInfo'
const tableUser = document.querySelector('tbody');
const infoEmail = document.getElementById('infoEmail')
const infoRoles = document.getElementById('infoRoles')

//Info for identity row
fetch(urlLogin)
    .then(response => response.json())
    .then(user => {
        const infoEmailLogin = `${user.email}`
        const infoRolesLogin = 'with roles ' + `${user.roles.map(role => role.name)}`
        infoEmail.innerHTML = infoEmailLogin
        infoRoles.innerHTML = infoRolesLogin
    })

let result = '';
const currentUser = (user) => {
            result += `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name)}</td>
                    </tr>`
    tableUser.innerHTML = result
}


fetch(urlUser)
    .then(response => response.json())
    .then(data => currentUser(data))
    .catch(error => console.log(error))