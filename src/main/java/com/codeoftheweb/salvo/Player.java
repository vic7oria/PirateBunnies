package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    @JsonIgnore

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> scores;

    private String password;
    private String pirateBunny;

    public double Wins;
    public double Losses;
    public double Draws;
    public double totalScore;

    //Constructores
    public Player() {
    }

    public String getPirateBunny() {
        return pirateBunny;
    }

    public void setPirateBunny(String pirateBunny) {
        this.pirateBunny = pirateBunny;
    }

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //G&S

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public float getWins(Set<Score> scores) {
        return scores.stream().filter(score -> score.getScore() == 1).count();
    }

    public float getLosses(Set<Score> scores) {
        return scores.stream().filter(score -> score.getScore() == 0).count();
    }

    public float getDraws(Set<Score> scores) {
        return scores.stream().filter(score -> score.getScore() == 0.5).count();
    }

    public float getTotalScore() {
        float wins = getWins(this.getScores()) * 1;
        float draws = getDraws(this.getScores()) * (float) 0.5;
        float losses = getLosses(this.getScores()) * 0;

        return wins + draws + losses;
    }


    public Map<String, Object> makePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        return dto;
    }

    public Map<String, Object> playerLeaderBoardDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("userName", this.getUserName());
        dto.put("score", getScoreList());
        return dto;
    }

    private Map<String, Object> getScoreList() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("gamesWon", this.getWins(this.getScores()));
        dto.put("gamesLost", this.getLosses(this.getScores()));
        dto.put("gamesDraw", this.getDraws(this.getScores()));
        dto.put("totalScore", getTotalScore());
        return dto;
    }
}
