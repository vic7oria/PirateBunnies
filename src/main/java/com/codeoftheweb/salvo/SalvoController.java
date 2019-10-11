package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping ("/api")
public class SalvoController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/games")
    public Map<String, Object> bringGames(Authentication authentication){
        Map<String, Object> dto = new HashMap<>();
            if (isGuest(authentication)){
                dto.put("player", "guest");
            }else{
            Player player = playerRepository.findByUserName(authentication.getName()).get();
            dto.put("player", player.makePlayerDTO());
        }
        dto.put("games", gameRepository.findAll()
                .stream()
                .map(Game -> makeGameDTO(Game))
                .collect(Collectors.toList()));
        return dto;
    }
    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
    @RequestMapping("/leaderBoard")
    public List<Map<String,Object>> makeLeaderBoard(){
        return playerRepository.findAll()
                .stream()
                .map(player -> player.playerLeaderBoardDTO())
                .collect(Collectors.toList());
    }

    public Map<String, Object> makeGameDTO(Game game){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate().getTime());
        dto.put("gamePlayers", getAllGamePlayers(game.getGamePlayers()));
        dto.put("score",game.getScores()
                .stream()
                .map(scores-> scores.makeScoreDTO())
                .collect(Collectors.toList()));
        return dto;
    }
    public List<Map<String, Object>> getAllGamePlayers(Set<GamePlayer> gamePlayers){
        return gamePlayers
                .stream()
                .map(GamePlayer -> makeGamePlayerDTO(GamePlayer))
                .collect(Collectors.toList());
    }
    public Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", gamePlayer.getPlayer().makePlayerDTO());
        return dto;
    }
    @RequestMapping("/game_view/{idGP}")
    public ResponseEntity<Map<String, Object>> showMyGameView(@PathVariable Long idGP, Authentication authentication) {

        if ( isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error","Unathorized"), HttpStatus.UNAUTHORIZED);
        }

        Player  player  =   playerRepository.findByUserName(authentication.getName()).orElse(null);
        GamePlayer gamePlayer = gamePlayerRepository.findById(idGP).get();

        if(gamePlayer.getPlayer().getId() == player.getId()){

            Map<String, Object> dto = new LinkedHashMap<String, Object>();
            dto.put("id", gamePlayer.getGame().getId());
            dto.put("created", gamePlayer.getGame().getCreationDate().getTime());
            dto.put("gamePlayers", getAllGamePlayers(gamePlayer.getGame().getGamePlayers()));
            dto.put("ships", gamePlayer.getShips()
              .stream()
              .map(ship -> ship.makeShipDTO())
              .collect(Collectors.toList()));
            dto.put("salvoes", gamePlayer.getGame().getGamePlayers()
              .stream()
              .flatMap(gamePlayer1 -> gamePlayer1.getSalvoes()
                .stream()
                .map(salvo->salvo.makeSalvoDTO()))
              .collect(Collectors.toList()));
            return new ResponseEntity<>(dto,HttpStatus.ACCEPTED);

        }

        return new ResponseEntity<>(makeMap("error","Not autorized"),HttpStatus.UNAUTHORIZED);

        }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String username, @RequestParam String password) {

        if ( username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(username).orElse(null) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}