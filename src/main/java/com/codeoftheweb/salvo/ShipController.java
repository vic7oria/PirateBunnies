package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ShipController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @RequestMapping("/games/players/{idGP}/ships")
    public ResponseEntity<Map> addShip(@PathVariable long idGP, @RequestBody Set<Ship> ships, Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        Player player = playerRepository.findByUserName(authentication.getName()).orElse(null);
        GamePlayer gamePlayer = gamePlayerRepository.getOne(idGP);

        if(player == null){
            return new ResponseEntity<>(makeMap("error", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer == null){
            return new ResponseEntity<>(makeMap("error", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer.getPlayer().getId() != player.getId()){
            return new ResponseEntity<>(makeMap("error", "Players don't match"), HttpStatus.FORBIDDEN);
        }

        if(!gamePlayer.getShips().isEmpty()){
            return new ResponseEntity<>(makeMap("error", "Unauthorized, ships already placed"), HttpStatus.UNAUTHORIZED);
        }

        ships.forEach(ship->{
            ship.setGamePlayer(gamePlayer);
            shipRepository.save(ship);
        });

        return new ResponseEntity<>(makeMap("OK", "Ship created"), HttpStatus.CREATED);
    }
    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
}
}