fetch('/api/games')
  .then(function (response) {
    return response.json();
  })
  .then(function (games) {

    if(games.player ==  "guest"){
      $("#login-form").show();
      $("#logout-form").hide();
    }else{
      $("#login-form").hide();
      $("#logout-form").show();

    }
    console.log(games);
    for (let index = 0; index < games.length; index++) {
      if (games[index].gamePlayers.length == 1) {
        $("#gameList").append('<li>' + 'Game: ' + games[index].id + ' Created ' + new Date(games[index].created).toLocaleString() + ' Player 1 ' + games[index].gamePlayers[0].player.email + ' Player 2 waiting... ' + '</li>');
      } else {
        $("#gameList").append('<li>' + 'Game: ' + games[index].id + ' Created ' + new Date(games[index].created).toLocaleString() + ' Player 1 ' + games[index].gamePlayers[0].player.email + ' Player 2 ' + games[index].gamePlayers[1].player.email + '</li>');
      }
    }
  })

function login() {

  $.post("/api/login", {
      username: $("#username").val(),
      password: $("#password").val()
    })
    .done(function(){console.log("data");
    $("#login-form").hide(),
    $("#logout-form").show(),
    $("#password").val("")})
    .fail(function(){console.log("Failed to LogIn");
    alert("User not registered")});
}

function logout() {
 
  $.post("/api/logout")
    .done(function(){console.log("bye");
    $("#logout-form").hide();
    $("#login-form").show()
  })
    .fail(function(){console.log("Failed to LogOut")});
};

fetch('/api/leaderBoard')
  .then(function (response) {
    return response.json();
  })
  .then(function (scores) {
    console.log("los scores:" + scores)

    scores.sort((a, b) => {
      return b.score.totalScore - a.score.totalScore
    })
    $("#table-glance").append('<tr class="thead-dark"><th>Name</th>' +
      '<th>Total</th>' + '<th>Won</th>' + '<th>Lost</th>' + '<th>Tied</th></tr>');
    scores.forEach(index => {
      $("#table-glance").append('<tr>' + '<td>' + index.userName + '</td>' + '<td>' + index.score.totalScore + '</td>' + '<td>' + index.score.gamesWon + '</td>' +
        '<td>' + index.score.gamesLost + '</td>' + '<td>' + index.score.gamesDraw + '</td>' + '</tr>');
    })
  })