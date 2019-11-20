$(function () {
    loadData();
});

//es una expresion regular para quitar los caracteres especiales menos los números,l obteniendo e player

function getParameterByName(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}

function loadData() {
    $.get('/api/game_view/' + getParameterByName('gp'))
        .done(function (data) {
            console.log(data);
            var playerInfo;
            if (data.gamePlayers.length == 2) {
                if (data.gamePlayers[0].id == getParameterByName('gp'))
                    playerInfo = [data.gamePlayers[0].player.email, data.gamePlayers[1].player.email];
                else
                    playerInfo = [data.gamePlayers[1].player.email, data.gamePlayers[0].player.email];
                $('#playerInfo').text(playerInfo[0] + ' vs ' + playerInfo[1]);
            } else {
                playerInfo = [data.gamePlayers[0].player.email]
                $('#playerInfo').text(playerInfo[0] + ' vs Waiting for player 2...');
            }
            data.ships.forEach(function (shipPiece) {
                shipPiece.locations.forEach(function (location) {
                    console.log("ship location: " + location)
                    $('#myShips' + changeCase(location[0])+ (parseInt(location.substring(1))-1)).addClass(' ship-piece');
                }) 
            });
            showSalvoes(data);
            if (data.ships.length > 0) {
                console.log(data.ships);
                $("#placeShips").hide();
                $("#shipPosition").hide();
            } else {
                $("#placeShips").show();
            };

        })
        .fail(function (jqXHR, textStatus) {
            alert("Failed: " + textStatus);
        });
};

function showSalvoes(data) {
    console.log(data);
    let turnoAux = data.salvoes;
    let playerOne = turnoAux.filter(x => (x.player === Number(getParameterByName('gp'))));
    console.log(playerOne);
    let playerTwo = turnoAux.filter(x => (x.player !== Number(getParameterByName('gp'))));
    console.log(playerTwo);
    playerOne.forEach(turno => {
        turno.salvoLocations.forEach(bomb => {
            $('#enemyShips'  + changeCase(bomb[0])+ (parseInt(bomb.substring(1))-1)).addClass('carrotBomb').append(turno);
            console.log(turno);
        })
    })
    playerTwo.forEach(turno => {
        turno.salvoLocations.forEach(bomb => {
            $('#myShips' + changeCase(bomb[0])+ (parseInt(bomb.substring(1))-1)).addClass('carrotBomb').append(turno);
            console.log(turno);
        })
    })
};

function changeCase(letter) {
    switch (letter) {
        case "A": return 0;
        case "B": return 1;
        case "C": return 2;
        case "D": return 3;
        case "E": return 4;
        case "F": return 5;
        case "G": return 6;
        case "H": return 7;
        case "I": return 8;
        case "J": return 9;
    }
}

function getPosition(ship) {
    var ship1 = new Object();
    ship1["name"] = $("#" + ship).attr('id');
    ship1["x"] = $("#" + ship).attr('data-gs-x');
    ship1["y"] = $("#" + ship).attr('data-gs-y');
    ship1["width"] = $("#" + ship).attr('data-gs-width');
    ship1["height"] = $("#" + ship).attr('data-gs-height');
    ship1["positions"] = [];
    if (ship1.height == 1) {
        for (i = 1; i <= ship1.width; i++) {
            ship1.positions.push(String.fromCharCode(parseInt(ship1.y) + 65) + (parseInt(ship1.x) + i))
        }
    } else {
        for (i = 0; i < ship1.height; i++) {
            ship1.positions.push(String.fromCharCode(parseInt(ship1.y) + 65 + i) + (parseInt(ship1.x) + 1))
        }
    }
    var objShip = new Object();
    objShip["type"] = ship1.name;
    objShip["locations"] = ship1.positions;

    return objShip;
}

function placeShips() {

    var carrier = getPosition("carrier")
    var patrol = getPosition("patrol_boat")
    var battleship = getPosition("battleship")
    var submarine = getPosition("submarine")
    var destroyer = getPosition("destroyer")
    console.log(carrier);
    console.log(getParameterByName('gp'));

    $.post({
        url: "/api/games/players/" + getParameterByName('gp') + "/ships",
        data: JSON.stringify([carrier, patrol, battleship, submarine, destroyer]),
        dataType: "text",
        contentType: "application/json"
    })
        .done(function (response, status, jqXHR) {

            alert("Ship added: " + response);
        })
        .fail(function (jqXHR, status, httpError) {
            alert("Failed to place ship: " + status + " " + httpError);
        })
}

// // myShips=mine enemyShips=enemy
// function displayMagic(hit, board) {
//     let type;
//     if (board === "mine") {
//         type = "B_";
//     } else if (board === "enemy") {
//         type = "S_";
//     }

//     let bombId = document.getElementById(type + hit)
//     console.log(bombId)
//     bombId.classList.add('carrotBomb')

// }