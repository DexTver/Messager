function userList() {
    // Call Web API to get a list of user
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
    // Iterate over the collection of data
    $.each(users, function (index, user) {
        // Add a row to the user table
        userAddRow(user);
    });
}

function userAddRow(user) {
    // Check if <tbody> tag exists, add one if not
    if ($("#userTable tbody").length == 0) {
        $("#userTable").append("<tbody></tbody>");
    }
    // Append row to <table>
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
    msg += "Code: " + request.status + "\n";
    msg += "Text: " + request.responseText + "\n";
    console.log(request);
    if (request.responseJSON != null) {
        msg += "Message" + request.responseJSON.Message + "\n";
    }
    alert(msg);
}

function formClear() {
    $("#name").val("");
    $("#age").val("");
    $("#password").val("");
    $("#repeatPassword").val("");
}

function updateClick() {
    // Build user object from inputs
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
    formClear();
}