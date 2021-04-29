/*
$(document).ready(refreshUsersTable());
*/

let userinfo;//для вывода инфы юзера
let usersinfo;//для вывода в таблу юзеров
let userinfo_v1;//для верхего левого вывода роли и мыла
let user;



function refreshUsersTable() {
    alert('add user')
}


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


$.ajax({//для вывода в таблу юзеров
    url: '/admin/userList',
    method: 'get',
    contentType: "application/json",
    dataType: 'json',
    success: function(users){
        console.log(users)
        $.each(users, function () {/*<td>${this.password}</td>*/
            usersinfo += `<tr>
                        <td>${this.id}</td>
                        <td>${this.name}</td>
                        
                        <td>${this.email}</td>
                        <td>${this.roleSet.map(role => role.name)}</td>
                        <td><button class="btn btn-info" th:attr="data-target='#upd' + ${this.id}" id="modal-edit"> Edit </button></td>
                        <td><button class="btn btn-danger" th:attr="data-target='#del' + ${this.id}" id="modal-delete"> DELETE </button></td>
                         </tr>`
        })
        $("#users_table").html(usersinfo)

        userinfo_v1 = `${user.email} with roles: ${user.roleSet.map(role => role.name)}`
        $("#info").html(userinfo_v1)
    }
});


/*создаем массив из значений полученных с селектора при создании нового пользователя*/
function convertToRoleSet(Array) {
    let roleArray = [];

    if (Array.indexOf("USER") !== -1) {
        roleArray.unshift({id: 2, name: "USER"});
    }
    if (Array.indexOf("ADMIN") !== -1) {
        roleArray.unshift({id: 1, name: "ADMIN"});
    }
    return roleArray;
}

/*создание нового пользователя*/
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
            refreshUsersTable();
        }
    })
}


