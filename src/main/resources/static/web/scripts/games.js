showTableGames();
leaderBoard();

function showTableGames() {
    fetch('/api/games')
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            if (data.player == "guest") {
                $("#login-form").show();
                $("#logout-form").hide();
                $("#newGame-form").hide();
            } else {
                $("#Hello").append("Welcome, " + data.player.email + "!");
                $("#login-form").hide();
                $("#logout-form").show();
                $("#newGame-form").show();

            }
            $('#gameList').empty();
            if (data.player != "guest") {
                for (let index = 0; index < data.games.length; index++) {
                    if (data.games[index].gamePlayers.length == 1) {
                        console.log("tiene un solo jugador");
                        if (data.games[index].gamePlayers[0].player.email != data.player.email) {
                            console.log("no es el jugador logeado y se puede unir a un juego: ");
                            console.log(data);
                            $("#gameList").append('<tr>' + '<td>' + "Game: " + data.games[index].id + '</td>' + '<td>' + "Created " + new Date(data.games[index].created).toLocaleString() + '</td>' + '<td>' + "Player 1: " + data.games[index].gamePlayers[0].player.email + '</td>' + '<td>' + "Player 2 waiting..." + '<td><form class="text-center"><button onclick="join(' + data.games[index].id + ')">Join</button></form>' + '</td>' + '</tr>');

                        } else {
                            console.log("es el jugador logeado y no se puede unir a un juego de un jugador: " + data);
                            $("#gameList").append('<tr>' + '<td>' + "Game: " + data.games[index].id + '</td>' + '<td>' + "Created " + new Date(data.games[index].created).toLocaleString() + '</td>' + '<td>' + "Player 1: " + data.games[index].gamePlayers[0].player.email + '</td>' + '<td>' + "Player 2 waiting..." + '</tr>');
                        }
                    } else if (data.games[index].gamePlayers.length == 2) {
                        console.log(data.player.email)
                        if (data.games[index].gamePlayers[0].player.email == data.player.email || data.games[index].gamePlayers[1].player.email == data.player.email) {
                            $("#gameList").append('<tr>' + '<td>' + "Game: " + data.games[index].id + '</td>' + '<td>' + "Created " + new Date(data.games[index].created).toLocaleString() + '</td>' + '<td>' + "Player 1: " + data.games[index].gamePlayers[0].player.email + '</td>' + '<td>' + "Player 2: " + data.games[index].gamePlayers[1].player.email + '</td>' + '<td><form class="text-center"><button onclick="reJoin(' + data.games[index].gamePlayers.map(gp=>{console.log(gp);if(gp.player.email==data.player.email){console.log(gp.id);return gp.gpId}else{return}}) + ')">Rejoin</button></form>' + '</td>' + '</tr>');
                        } else {
                            $("#gameList").append('<tr>' + '<td>' + "Game: " + data.games[index].id + '</td>' + '<td>' + "Created " + new Date(data.games[index].created).toLocaleString() + '</td>' + '<td>' + "Player 1: " + data.games[index].gamePlayers[0].player.email + '</td>' + '<td>' + "Player 2: " + data.games[index].gamePlayers[1].player.email + '</td>' + '</tr>');
                        }
                    }
                }
            }
        })
}

function logIn() {
    event.preventDefault();

    $.post("/api/login", {
            username: $("#username").val(),
            password: $("#password").val()
        })
        .done(function () {
            showTableGames();
            $("#login-form").hide(),
                $("#logout-form").show(),
                $("#password").val("")
        })
        .fail(function () {
            console.log("Failed to LogIn");
            alert("User not registered")
        });
}

function signUp() {
    event.preventDefault();

    $.post("/api/players", {
            username: $("#username").val(),
            password: $("#password").val()
        })
        .done(function () {
            console.log("data");
            logIn();
            $("#login-form").hide(),
                $("#logout-form").show(),
                $("#password").val("")
        })
        .fail(function () {
            console.log("Failed to LogIn");
            alert("User not registered")
        });
};

function logout() {
    event.preventDefault();

    $.post("/api/logout")
        .done(function () {
            console.log("bye");
            $("#logout-form").hide();
            $("#login-form").show()
        })
        .fail(function () {
            console.log("Failed to LogOut")
        });
};

function newGame() {
    event.preventDefault();
    url = '/api/games/';
    $.post(url)
        .done(function (data) {
            return location.href = "/web/game.html?gp=" + data.gpid;
        })
}

function join(gameId) {
    event.preventDefault();
    url = '/api/games/' + gameId + '/players';
    $.post(url)
        .done(function (data) {
            return location.href = "/web/game.html?gp=" + data.gpid;
        })
}

function reJoin(gpId) {
    event.preventDefault();
    location.href = "/web/game.html?gp=" + gpId;
}


function leaderBoard() {
    fetch('/api/leaderBoard')
        .then(function (response) {
            return response.json();
        })
        .then(function (score) {
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
}