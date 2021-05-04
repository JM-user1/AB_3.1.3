/*
$(document).ready(refreshUsersTable());
*/
let elem = document.querySelector( '#add_button');

/* let btnEdit = document.querySelector(`#edit-btn`);*/

let btnDelete = document.querySelector(`#modal-delete`)

let usersList = document.querySelector( '#nav-list-tab');


let userinfo;//для вывода инфы юзера
let usersinfo;//для вывода в таблу юзеров
let userinfo_v1;//для верхего левого вывода роли и мыла
let user;




UsersTable()

$.ajax({//для вывода инфы юзера
    url: '/user/authUser',
    method: 'get',
    contentType: "application/json",
    dataType: 'json',
    success: function(user){
        userinfo += `<td>${user.name}</td>`
        userinfo += `<td>${user.email}</td>`
        userinfo += `<td>${user.roleSet.map(role => role.name)}</td>`

        $("#user_table").html(userinfo)

        userinfo_v1 = `${user.email} with roles: ${user.roleSet.map(role => role.name)}`
        $("#info").html(userinfo_v1)
    }
});

usersList.addEventListener('click',()=> UsersTable());

function UsersTable(){
    $.ajax({//для вывода в таблу юзеров
        url: '/admin/userList',
        method: 'get',
        contentType: "application/json",
        dataType: 'json',
        success: function(users){
            console.log(users)
            $.each(users, function () {/*<td>${this.password}</td>  data-id=${this.id} */
                usersinfo += `<tr>
                            <td>${this.id}</td>
                            <td>${this.name}</td>
                            
                            <td>${this.email}</td>
                            <td>${this.roleSet.map(role => role.name)}</td>
                          <td>
                            <button onclick="openEditUserModal(this.id)" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#updateForm" data-id=${this.id}> UPDATE </button>
                          </td>    
       
                          <td>
                            <button onclick="deleteUser(this.id)" type="button" class="btn btn-danger" data-bs-toggle="modal" data-id=${this.id}> DELETE </button>
                          </td>
                </tr>`
            })
            $("#users_table").html(usersinfo)
        }
    });
}


/*создание нового пользователя*/
elem.addEventListener('click',()=> createUser());
function createUser() {
    let user = {
        "name": $('#firstNameCreate').val(),
        "email": $('#emailCreate').val(),
        "password": $('#passwordCreate').val(),
            "roleSet": [
                {
                    "name": "USER",
                    "authority": "USER",
                    "role_id": 2
                }
            ]
    };
    $.ajax({
        url: '/admin/newUser',
        type: 'post',
        data: JSON.stringify(user),
        headers: {
            'x-auth-token': localStorage.accessToken,
            "Content-Type": "application/json"
        },
        dataType: 'json',
        success: function () {
        }
    })
}
/*
btnEdit.addEventListener('click',()=> openEditUserModal("${user.id}"));*/
function openEditUserModal(Id) {
    $.ajax({
        url: "http://localhost:8080/admin/user/" + {Id},
        type: 'get',
        headers: {
            'x-auth-token': localStorage.accessToken,
        },
        success: (user) => {
            console.log("it is all users table: " + user.name+" roles id: " + user.roles.map(role => role.name))
            alert("Found user by Id" )
            sendEditRequest(user);
        },
        error: alert("Not found")
    })
}

function sendEditRequest(user) {
    $('#idE').val(user.id)
    $('#usernameE').val(user.username);
    $('#emailE').val(user.email);
    $('#passwordE').val(user.password);


    $('#edit_button').click(() => {
        let userE = {
            "id" : user.id,
            "username": $('#usernameE').val(),
            "email": $('#emailE').val(),
            "password": $('#passwordE').val(),
            "roles": $('#rolesSelect').val()
        };
        console.log("this is edited user: ", JSON.stringify(userE))
        $.ajax({
            url: `http://localhost:8080/admin/updateUser`,
            type: 'put',
            data: JSON.stringify(userE),
            headers: {
                'x-auth-token': localStorage.accessToken,
                "Content-Type": "application/json"
            },
            dataType: 'json',
            success: UsersTable,
            error: (data) => alert(data)
        })
    });

}

function deleteUser(userId) {
    $.ajax({
        url: `http://localhost:8080/admin/deleteUser/${userId}`,
        type: 'delete',
        headers: {
            'x-auth-token': localStorage.accessToken
        },
        success: usersList,
        error: (data) => alert(data)
    })
}
