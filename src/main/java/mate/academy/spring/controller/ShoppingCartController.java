package mate.academy.spring.controller;

import mate.academy.spring.mapper.DtoResponseMapper;
import mate.academy.spring.model.ShoppingCart;
import mate.academy.spring.model.User;
import mate.academy.spring.model.dto.response.ShoppingCartResponseDto;
import mate.academy.spring.service.MovieSessionService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private UserService userService;
    private MovieSessionService movieSessionService;
    private DtoResponseMapper<ShoppingCartResponseDto, ShoppingCart> mapper;
    private ShoppingCartService shoppingCartService;

    public ShoppingCartController(UserService userService,
                                  MovieSessionService movieSessionService,
                                  DtoResponseMapper<ShoppingCartResponseDto, ShoppingCart> mapper,
                                  ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.movieSessionService = movieSessionService;
        this.mapper = mapper;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUser(@RequestParam Long userId) {
        return mapper.toDto(shoppingCartService.getByUser(userService.get(userId)));
    }

    @PostMapping("/movie-sessions")
    public ShoppingCartResponseDto addMovieSession(@RequestParam Long userId,
                                                   @RequestParam Long movieSessionId) {
        User user = userService.get(userId);
        shoppingCartService.addSession(
                movieSessionService.get(movieSessionId), user);
        return mapper.toDto(shoppingCartService.getByUser(user));
    }
}
