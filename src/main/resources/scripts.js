function userList() {
    $.ajax({
        url: 'http://localhost:8080/users',
        type: 'GET',
        dataType: 'json',
        success: function (users) {
            userListSuccess(users);
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });
}

function userListSuccess(users) {
    $.each(users, function (index, user) {
        userAddRow(user);
    });
    clearMessage();
    addRowHandlers();
}

function userAddRow(user) {
    if ($("#userTable tbody").length == 0) {
        $("#userTable").append("<tbody></tbody>");
    }
    $("#userTable tbody").append(
        userBuildTableRow(user));
}

function userBuildTableRow(user) {
    return "<tr>" +
        "<td>" + user.name + "</td>" +
        "<td>" + user.age + "</td>" +
        "</tr>";
}

function tableClear() {
    $("#userTable tbody").remove();
}

function handleException(request, message, error) {
    let msg = "";
    // msg += "Code: " + request.status + "\n";
    msg += request.responseText + "\n";
    console.log(request);
    if (request.responseJSON != null) {
        msg += "Message" + request.responseJSON.Message + "\n";
    }
    addMessage(msg);
    setTimeout(clearMessage, 5000);
}

function formClear() {
    $("#name").val("");
    $("#age").val("");
    $("#password").val("");
    $("#repeatPassword").val("");
}

function updateClick() {
    const User = {};
    User.name = $("#name").val();
    User.age = $("#age").val();
    User.password = $("#password").val();
    User.repeatPassword = $("#repeatPassword").val();
    userAdd(User);
}

function userAdd(user) {
    $.ajax({
        url: "http://localhost:8080/users",
        type: 'POST',
        contentType:
            "application/json;charset=utf-8",
        data: JSON.stringify(user),
        success: function (_user) {
            userAddSuccess(_user);
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });
}

function userAddSuccess(user) {
    userAddRow(user);
    clearMessage();
    addRowHandlers();
    formClear();
}

function deleteClick() {
    const User = {};
    User.name = $("#name").val();
    User.age = $("#age").val();
    User.password = $("#password").val();
    User.repeatPassword = $("#repeatPassword").val();
    userDelete(User);
}

function userDelete(user) {
    $.ajax({
        url: "http://localhost:8080/users",
        type: 'DELETE',
        contentType:
            "application/json;charset=utf-8",
        data: JSON.stringify(user),
        success: function (_user) {
            tableClear();
            userList();
        },
        error: function (request, message, error) {
            handleException(request, message, error);
        }
    });
}

function addRowHandlers() {
    var table = document.getElementById("userTable");
    var rows = table.getElementsByTagName("tr");
    for (i = 0; i < rows.length; i++) {
        var currentRow = table.rows[i];
        var createClickHandler = function (row) {
            return function () {
                var cell = row.getElementsByTagName("td");
                var name = cell[0].innerHTML;
                var age = cell[1].innerHTML;
                $("#name").val(name);
                $("#age").val(age);
            };
        };
        currentRow.onclick = createClickHandler(currentRow);
    }
}

function addMessage(text) {
    document.getElementById('info').innerHTML = text;
}

function clearMessage() {
    document.getElementById('info').innerHTML = "";
}