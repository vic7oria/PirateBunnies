package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Salvo {
  //Atributos
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "gamePlayer_id")
  private GamePlayer gamePlayer;

  @ElementCollection
  @Column(name = "salvoLocations")
  private Set<String> salvoLocations;

  private int turn;

  //Constructores

  public Salvo() {
  }

  public Salvo(GamePlayer gamePlayer, Set<String> salvoLocations, int turn) {
    this.gamePlayer = gamePlayer;
    this.salvoLocations = salvoLocations;
    this.turn = turn;
  }

    //G&S+métodos

  public GamePlayer getGamePlayer() {
    return gamePlayer;
  }

  public void setGamePlayer(GamePlayer gamePlayer) {
    this.gamePlayer = gamePlayer;
  }

  public Set<String> getSalvoLocations() {
    return salvoLocations;
  }

  public void setSalvoLocations(Set<String> salvoLocations) {
    this.salvoLocations = salvoLocations;
  }

  public int getTurn() {
    return turn;
  }

  public long getId() {
    return id;
  }

  public Map<String, Object> makeSalvoDTO(){
    Map<String, Object> dto = new LinkedHashMap<String, Object>();
    dto.put("player", this.getGamePlayer().getId());
    dto.put("turn", this.getTurn());
    dto.put("salvoLocations", this.getSalvoLocations());
    return dto;
  }

  }
