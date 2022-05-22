const urlUsers = 'http://localhost:8080/api/admin';
const urlRoles = 'http://localhost:8080/api/roles'

//For users table
const tableUsers = document.getElementById('usersTable');


//For adding new user
const addNewUser = document.getElementById('newUserButton');

const firstNameNew = document.getElementById('firstName')
const lastNameNew = document.getElementById('lastName')
const ageNew = document.getElementById('age')
const emailNew = document.getElementById('email')
const passwordNew = document.getElementById('password')
const rolesNew = document.getElementById('roles')

//For delete user
let deleteModal = $('#deleteModal').modal()
const deleteId = document.getElementById('deleteId')
const deleteFirstName = document.getElementById('deleteFirstName')
const deleteLastName = document.getElementById('deleteLastName')
const deleteAge = document.getElementById('deleteAge')
const deleteEmail = document.getElementById('deleteEmail')
const deleteRoles = document.getElementById('deleteRole')


let result = '';


//Insert data into users table
const allUsers = (users) => {
    users.forEach(user => {
        result += `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name)}</td>
                        <td><button type="button" class="btn btn-primary" data-toggle="modal"
                        >Edit</button></td>
                        <td><button type="submit" class="deleteBtn btn btn-danger" data-toggle="modal"
                        data-target="#deleteModal">Delete</button></td>
                    </tr>`
    })
    tableUsers.innerHTML = result
}


//GET: all users
function showAllUsers() {
    fetch(urlUsers)
        .then(response => response.json())
        .then(data => allUsers(data))
}

showAllUsers()

//GET: insert all roles into form of html file in users table (roles field)
function showAllRoles() {
    fetch(urlRoles)
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                rolesNew.append(new Option(role.name, role.id))
            })
        })
}

showAllRoles()

// POST: add new user
addNewUser.addEventListener('click', (e) => {
        e.preventDefault()
        let rolesForm = document.getElementById('roles')
        let selectedOptions = rolesForm.selectedOptions
        let rolesArray = []
        for (let i = 0; i < selectedOptions.length; i++) {
            rolesArray.push({id: selectedOptions[i].value, name: selectedOptions[i].value})
        }

        fetch(urlUsers, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: firstNameNew.value,
                lastName: lastNameNew.value,
                age: ageNew.value,
                email: emailNew.value,
                password: passwordNew.value,
                roles: rolesArray,
            })
        })
            .then(response => response.json())
            .then(data => allUsers(data))
        firstNameNew.value = ''
        lastNameNew.value = ''
        ageNew.value = ''
        emailNew.value = ''
        passwordNew.value = ''
    }
)

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

on(document, 'click', '#editUser', e => {
    console.log("edited")
})


//                                              DELETE
on(document, 'click', '.deleteBtn', e => {
    let userData = e.target.parentNode.parentNode
    id = userData.children[0].innerHTML
    deleteId.value = userData.children[0].innerHTML
    deleteFirstName.value = userData.children[1].innerHTML
    deleteLastName.value = userData.children[2].innerHTML
    deleteAge.value = userData.children[3].innerHTML
    deleteEmail.value = userData.children[4].innerHTML
    deleteRoles.value = userData.children[5].innerHTML
})

deleteModal.addEventListener('submit', (e) => {
    e.preventDefault()
    fetch(urlUsers + `/${id}`, {
        method: 'DELETE'
    })
        .then(data => showAllUsers(data))
        .catch(error => console.log(error))
})

