$(function () {
    loadData();
});

const magicData = {
    "id": 1,
    "created": 1568153977434,
    "gamePlayers": [{
        "id": 1,
        "player": {
            "id": 1,
            "email": "j.bauer@ctu.gov"
        }
    }, {
        "id": 2,
        "player": {
            "id": 2,
            "email": "c.obrian@ctu.gov"
        }
    }],
    "ships": [{
        "type": "Destroyer",
        "locations": ["H1", "H2", "H3"]
    }, {
        "type": "Submarine",
        "locations": ["G1", "F1", "E1"]
    }, {
        "type": "Patrol Boat",
        "locations": ["B4", "B5"]
    }],
    "salvoes": [{
        "player": 1,
        "turn": 1,
        "salvoLocations": ["C5", "B5", "F1"]
    }, {
        "player": 1,
        "turn": 2,
        "salvoLocations": ["D5", "F2"]
    }, {
        "player": 2,
        "turn": 1,
        "salvoLocations": ["B4", "B5", "B6"]
    }, {
        "player": 2,
        "turn": 2,
        "salvoLocations": ["A2", "H3", "E1"]
    }]
}

function getParameterByName(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}

function loadData() {
    $.get('/api/game_view/' + getParameterByName('gp'))
        .done(function (data) {
            console.log(data);
            let playerInfo;
            if (data.gamePlayers[0].id == getParameterByName('gp'))
                playerInfo = [data.gamePlayers[0].player.email, data.gamePlayers[1].player.email];
            else
                playerInfo = [data.gamePlayers[1].player.email, data.gamePlayers[0].player.email];
            $('#playerInfo').text(playerInfo[0] + '(you) vs ' + playerInfo[1]);
            data.ships.forEach(function (shipPiece) {
                shipPiece.locations.forEach(function (location) {
                    $('#' + location).addClass('ship-piece');
                })
            });
            showSalvoes(data);
        })
        .fail(function (jqXHR, textStatus) {
            alert("Failed: " + textStatus);
        });
};

function showSalvoes(data) {

    let turnoAux = data.salvoes;
    let playerOne = turnoAux.filter(x => (x.player === Number(getParameterByName('gp'))));
    console.log(playerOne);
    let playerTwo = turnoAux.filter(x => (x.player !== Number(getParameterByName('gp'))));

    playerOne.forEach(turno => {
        turno.salvoLocations.forEach(bomb => {
            displayMagic(bomb, "enemy")
        })
    })
    playerTwo.forEach(turno => {
        turno.salvoLocations.forEach(bomb => {
            displayMagic(bomb, "mine")
        })
    })
};
// myShips=mine enemyShips=enemy
function displayMagic(hit, board) {
    let type;
    if (board === "mine") {
        type = "B_";
    } else if (board === "enemy") {
        type = "S_";
    }

    let bombId = document.getElementById(type + hit)
    console.log(bombId)
    bombId.classList.add('carrotBomb')

}