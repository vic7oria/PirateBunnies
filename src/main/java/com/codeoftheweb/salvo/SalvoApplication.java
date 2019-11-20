package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.Location;
import java.util.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository	shipRepository,
									  SalvoRepository salvoRepository,
									  ScoreRepository scoreRepository) {
		return (args) -> {
			Player p1= new Player("j.bauer@ctu.gov", passwordEncoder().encode("24"));
			Player p2= new Player("c.obrian@ctu.gov", passwordEncoder().encode("42"));
			Player p3= new Player("kim_bauer@gmail.com", passwordEncoder().encode("kb"));
			Player p4= new Player("t.almeida@ctu.gov", passwordEncoder().encode("mole"));

			List<String> bunnies	= new ArrayList<>();
			bunnies.add("Anne Bunny");
			bunnies.add("Blacktail");
			bunnies.add("Jackrabbit Rackham");
			bunnies.add("Hare Morgan");

			p1.setPirateBunny(bunnies.get(0));
			p2.setPirateBunny(bunnies.get(1));
			p3.setPirateBunny(bunnies.get(2));
			p4.setPirateBunny(bunnies.get(3));

			playerRepository.save(p1);
			playerRepository.save(p2);
			playerRepository.save(p3);
			playerRepository.save(p4);

			Date date = new Date();

			Game g1= new Game ();
			Game g2= new Game ();
			Game g3= new Game ();
			Game g4= new Game ();
			Game g5= new Game ();
			Game g6= new Game ();
			Game g7= new Game ();
			Game g8= new Game ();

			gameRepository.save(g1);
			gameRepository.save(g2);
			gameRepository.save(g3);
			gameRepository.save(g4);
			gameRepository.save(g5);
			gameRepository.save(g6);
			gameRepository.save(g7);
			gameRepository.save(g8);

			//game1
			GamePlayer gp1= new GamePlayer(g1, p1);
			GamePlayer gp2= new GamePlayer(g1, p2);
			//game2
			GamePlayer gp3= new GamePlayer(g2, p1);
			GamePlayer gp4= new GamePlayer(g2, p2);
			//game3
			GamePlayer gp5= new GamePlayer(g3, p2);
			GamePlayer gp6= new GamePlayer(g3, p4);
			//game4
			GamePlayer gp7= new GamePlayer(g4, p2);
			GamePlayer gp8= new GamePlayer(g4, p1);
			//game5
			GamePlayer gp9= new GamePlayer(g5, p4);
			GamePlayer gp10= new GamePlayer(g5, p1);
			//game6
			GamePlayer gp11= new GamePlayer(g6, p3);
			//game7
			GamePlayer gp12= new GamePlayer(g7, p4);
			//game8
			GamePlayer gp13= new GamePlayer(g8, p3);
			GamePlayer gp14= new GamePlayer(g8, p4);

			gamePlayerRepository.save(gp1);
			gamePlayerRepository.save(gp2);
			gamePlayerRepository.save(gp3);
			gamePlayerRepository.save(gp4);
			gamePlayerRepository.save(gp5);
			gamePlayerRepository.save(gp6);
			gamePlayerRepository.save(gp7);
			gamePlayerRepository.save(gp8);
			gamePlayerRepository.save(gp9);
			gamePlayerRepository.save(gp10);
			gamePlayerRepository.save(gp11);
			gamePlayerRepository.save(gp12);
			gamePlayerRepository.save(gp13);
			gamePlayerRepository.save(gp14);

			Set<String> locations1	= new LinkedHashSet<>();
			locations1.add("H1");
			locations1.add("H2");
			locations1.add("H3");

			Set<String> locations2	= new LinkedHashSet<>();
			locations2.add("E1");
			locations2.add("F1");
			locations2.add("G1");

			Set<String> locations3	= new LinkedHashSet<>();
			locations3.add("B4");
			locations3.add("B5");

			Set<String> locations4	= new LinkedHashSet<>();
			locations4.add("B5");
			locations4.add("C5");
			locations4.add("D5");

			Set<String> locations5	= new LinkedHashSet<>();
			locations5.add("F1");
			locations5.add("F2");

			Set<String> locations6	= new LinkedHashSet<>();
			locations6.add("B5");
			locations6.add("C5");
			locations6.add("D5");

			Set<String> locations7	= new LinkedHashSet<>();
			locations7.add("C6");
			locations7.add("C7");

			Set<String> locations8	= new LinkedHashSet<>();
			locations8.add("A2");
			locations8.add("A3");
			locations8.add("A4");

			Set<String> locations9	= new LinkedHashSet<>();
			locations9.add("G6");
			locations9.add("H6");

			Set<String> locations10	= new LinkedHashSet<>();
			locations10.add("B5");
			locations10.add("C5");
			locations10.add("D5");

			Set<String> locations11 = new HashSet<>(Arrays.asList("C6","C7"));
			Set<String> locations12 = new HashSet<>(Arrays.asList("A2","A3","A4"));
			Set<String> locations13 = new HashSet<>(Arrays.asList("G6","H6"));
			Set<String> locations14 = new HashSet<>(Arrays.asList("B5","C5","D5"));
			Set<String> locations15 = new HashSet<>(Arrays.asList("C6","C7"));
			Set<String> locations16 = new HashSet<>(Arrays.asList("A2","A3","A4"));
			Set<String> locations17 = new HashSet<>(Arrays.asList("G6","H6"));
			Set<String> locations18 = new HashSet<>(Arrays.asList("B5","C5","D5"));
			Set<String> locations19 = new HashSet<>(Arrays.asList("C6","C7"));
			Set<String> locations20 = new HashSet<>(Arrays.asList("A2","A3","A4"));
			Set<String> locations21 = new HashSet<>(Arrays.asList("G6","H6"));
			Set<String> locations22 = new HashSet<>(Arrays.asList("B5","C5","D5"));
			Set<String> locations23 = new HashSet<>(Arrays.asList("C6","C7"));
			Set<String> locations24 = new HashSet<>(Arrays.asList("B5","C5","D5"));
			Set<String> locations25 = new HashSet<>(Arrays.asList("C6","C7"));
			Set<String> locations26 = new HashSet<>(Arrays.asList("A2","A3","A4"));
			Set<String> locations27 = new HashSet<>(Arrays.asList("G6","H6"));

			String shipTypeSubmarine = "Submarine";
			String shipTypeDestroyer = "Destroyer";
			String shipTypePatrolBoat = "Patrol Boat";
			String shipTypeBattleship = "Battleship";
			String shipTypeCarrier = "Carrier";

			Ship ship1 = new Ship(gp1, shipTypeDestroyer, locations1);
			Ship ship2 = new Ship(gp1, shipTypeSubmarine, locations2);
			Ship ship3 = new Ship(gp1, shipTypePatrolBoat, locations3);
			Ship ship4 = new Ship(gp2, shipTypeDestroyer, locations4);
			Ship ship5 = new Ship(gp2, shipTypePatrolBoat, locations5);
			Ship ship6 = new Ship(gp3, shipTypeDestroyer, locations6);
			Ship ship7 = new Ship(gp3, shipTypePatrolBoat, locations7);
			Ship ship8 = new Ship(gp4, shipTypeSubmarine, locations8);
			Ship ship9 = new Ship(gp4, shipTypePatrolBoat, locations9);
			Ship ship10 = new Ship(gp5, shipTypeDestroyer, locations10);
			Ship ship11 = new Ship(gp5, shipTypePatrolBoat, locations11);
			Ship ship12 = new Ship(gp6, shipTypeSubmarine, locations12);
			Ship ship13 = new Ship(gp6, shipTypePatrolBoat, locations13);
			Ship ship14 = new Ship(gp7, shipTypeDestroyer, locations14);
			Ship ship15 = new Ship(gp7, shipTypePatrolBoat, locations15);
			Ship ship16 = new Ship(gp8, shipTypeSubmarine, locations16);
			Ship ship17 = new Ship(gp8, shipTypePatrolBoat, locations17);
			Ship ship18 = new Ship(gp9, shipTypeDestroyer, locations18);
			Ship ship19 = new Ship(gp9, shipTypePatrolBoat, locations19);
			Ship ship20 = new Ship(gp10, shipTypeSubmarine, locations20);
			Ship ship21 = new Ship(gp10, shipTypePatrolBoat, locations21);
			Ship ship22 = new Ship(gp11, shipTypeDestroyer, locations22);
			Ship ship23 = new Ship(gp11, shipTypePatrolBoat, locations23);
			Ship ship24 = new Ship(gp13, shipTypeDestroyer, locations24);
			Ship ship25 = new Ship(gp13, shipTypePatrolBoat, locations25);
			Ship ship26 = new Ship(gp14, shipTypeSubmarine, locations26);
			Ship ship27 = new Ship(gp14, shipTypePatrolBoat, locations27);

			shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);
			shipRepository.saveAll(Arrays.asList(ship6,ship7,ship8,ship9,ship10,ship11,ship12,ship13,ship14,ship15,ship16,ship17,ship18,ship19,ship20,ship21,ship22,ship23,ship24,ship25,ship26,ship27));

			Salvo salvo1 = new Salvo(gp1, new HashSet<>(Arrays.asList("B5","C5","F1")),1);
			Salvo salvo2 = new Salvo(gp2, new HashSet<>(Arrays.asList("B4","B5","B6")),1);
			Salvo salvo3 = new Salvo(gp1, new HashSet<>(Arrays.asList("F2","D5")),2);
			Salvo salvo4 = new Salvo(gp2, new HashSet<>(Arrays.asList("E1","H3","A2")),2);
			Salvo salvo5 = new Salvo(gp3, new HashSet<>(Arrays.asList("A2","A4","G6")),1);
			Salvo salvo6 = new Salvo(gp4, new HashSet<>(Arrays.asList("B5","D5","C7")),1);
			Salvo salvo7 = new Salvo(gp3, new HashSet<>(Arrays.asList("A3","H6")),2);
			Salvo salvo8 = new Salvo(gp4, new HashSet<>(Arrays.asList("C5","C6")),2);
			Salvo salvo9 = new Salvo(gp5, new HashSet<>(Arrays.asList("G6","H6","A4")),1);
			Salvo salvo10 = new Salvo(gp6, new HashSet<>(Arrays.asList("H1","H2","H3")),1);
			Salvo salvo11 = new Salvo(gp5, new HashSet<>(Arrays.asList("A2","A3","D8")),2);
			Salvo salvo12 = new Salvo(gp6, new HashSet<>(Arrays.asList("E1","F2","G3")),2);
			Salvo salvo13 = new Salvo(gp7, new HashSet<>(Arrays.asList("A3","A4","F7")),1);
			Salvo salvo14 = new Salvo(gp8, new HashSet<>(Arrays.asList("B5","C6","H1")),1);
			Salvo salvo15 = new Salvo(gp7, new HashSet<>(Arrays.asList("A2","G6","H6")),2);
			Salvo salvo16 = new Salvo(gp8, new HashSet<>(Arrays.asList("C5","C7","D5")),2);
			Salvo salvo17 = new Salvo(gp9, new HashSet<>(Arrays.asList("A1","A2","A3")),1);
			Salvo salvo18 = new Salvo(gp10, new HashSet<>(Arrays.asList("B5","B6","C7")),1);
			Salvo salvo19 = new Salvo(gp9, new HashSet<>(Arrays.asList("G6","G7","G8")),2);
			Salvo salvo20 = new Salvo(gp10, new HashSet<>(Arrays.asList("C6","D6","E6")),2);
			Salvo salvo21 = new Salvo(gp9, new HashSet<>(Arrays.asList("H1","H8")),3);

			salvoRepository.saveAll(Arrays.asList(salvo1,salvo2,salvo3,salvo4,salvo5,salvo6,salvo7,salvo8,salvo9,salvo10,salvo11,salvo12,salvo13,salvo14,salvo15,salvo16,salvo17,salvo18,salvo19,salvo20,salvo21));

			Score score1 = new Score(1,date, p1,g1);
			Score score2 = new Score(0,date,p2,g1);
			Score score3 = new Score(0.5,date,p1,g2);
			Score score4 = new Score(0.5,date,p2,g2);
			Score score5 = new Score(1,date,p2,g3);
			Score score6 = new Score(0,date,p4,g3);
			Score score7 = new Score(0.5,date,p2,g4);
			Score score8 = new Score(0.5,date,p1,g4);
			Score score9 = new Score(1,date,p4,g8);

			scoreRepository.saveAll(Arrays.asList(score1,score2,score3,score4,score5,score6,score7,score8,score9));

		};
	}
}
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByUserName(inputName).get();
			if (player != null) {
				return new User(player.getUserName(), player.getPassword(),
								AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}
@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
						.antMatchers("/web/games.html").permitAll()
						.antMatchers("/web/**").permitAll()
						.antMatchers("/api/games").permitAll()
						.antMatchers("/api/players").permitAll()
						.antMatchers("/api/game_view/{idGP}").hasAuthority("USER")
						.antMatchers("/rest").denyAll()
						.anyRequest().permitAll();

		http.formLogin()
						.usernameParameter("username")
						.passwordParameter("password")
						.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

	}
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}

}