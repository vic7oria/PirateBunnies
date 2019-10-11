fetch('/api/games')
    .then(function(response) {
        return response.json();
    })
    .then(function(data) {
        if (data.player == "guest") {
            $("#login-form").show();
            $("#logout-form").hide();
        } else {
            $("#Hello").append("Welcome, " + data.player.email + "!");
            $("#login-form").hide();
            $("#logout-form").show();

        }
        console.log(data);
        for (let index = 0; index < data.games.length; index++) {
            if (data.games[index].gamePlayers.length == 1) {
                $("#gameList").append('<li>' + 'Game: ' + data.games[index].id + ' Created ' + new Date(data.games[index].created).toLocaleString() + ' Player 1 ' + data.games[index].gamePlayers[0].player.email + ' Player 2 waiting... ' + '</li>');
            } else {
                $("#gameList").append('<li>' + 'Game: ' + data.games[index].id + ' Created ' + new Date(data.games[index].created).toLocaleString() + ' Player 1 ' + data.games[index].gamePlayers[0].player.email + ' Player 2 ' + data.games[index].gamePlayers[1].player.email + '</li>');
            }
        }
    })

function logIn() {

    $.post("/api/login", {
            username: $("#username").val(),
            password: $("#password").val()
        })
        .done(function() {
            console.log("data");
            $("#login-form").hide(),
                $("#logout-form").show(),
                $("#password").val("")
        })
        .fail(function() {
            console.log("Failed to LogIn");
            alert("User not registered")
        });
}

function signUp() {

    $.post("/api/players", {
            username: $("#username").val(),
            password: $("#password").val()
        })
        .done(function() {
            console.log("data");
            logIn();
            location.reload();
            $("#login-form").hide(),
                $("#logout-form").show(),
                $("#password").val("")
        })
        .fail(function() {
            console.log("Failed to LogIn");
            alert("User not registered")
        });
}

function logout() {

    $.post("/api/logout")
        .done(function() {
            console.log("bye");
            $("#logout-form").hide();
            $("#login-form").show()
        })
        .fail(function() {
            console.log("Failed to LogOut")
        });
};

function leaderBoard() {
    fetch('/api/leaderBoard')
        .then(function(response) {
            return response.json();
        })
        .then(function(score) {
            console.log("los scores:" + score)

            score.sort((a, b) => {
                return b.score.totalScore - a.score.totalScore
            })

            console.log("los scores ordenados:" + score)

            $("#table-glance").append('<tr class="thead-dark"><th>Name</th>' +
                '<th>Total</th>' + '<th>Won</th>' + '<th>Lost</th>' + '<th>Tied</th></tr>');
            score.forEach(index => {
                $("#table-glance").append('<tr>' + '<td>' + index.userName + '</td>' + '<td>' + index.score.totalScore + '</td>' + '<td>' + index.score.gamesWon + '</td>' +
                    '<td>' + index.score.gamesLost + '</td>' + '<td>' + index.score.gamesDraw + '</td>' + '</tr>');
            })
        })
};

leaderBoard();