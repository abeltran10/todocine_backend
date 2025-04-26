package com.todocine;

import com.todocine.dao.FavoritosDAO;
import com.todocine.dao.MovieDAO;
import com.todocine.dao.UsuarioDAO;
import com.todocine.dto.FavoritosDTO;
import com.todocine.dto.MovieDTO;
import com.todocine.dto.UsuarioDTO;
import com.todocine.entities.Favoritos;
import com.todocine.entities.FavoritosId;
import com.todocine.entities.Movie;
import com.todocine.entities.Usuario;
import com.todocine.service.FavoritosService;
import com.todocine.service.impl.BaseServiceImpl;
import com.todocine.service.impl.FavoritosServiceImpl;
import com.todocine.service.impl.UserServiceImpl;
import com.todocine.utils.Paginator;
import com.todocine.utils.mapper.MovieMapper;
import com.todocine.utils.mapper.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CheckFavoritosUnitTest {
    public static Logger LOG = LoggerFactory.getLogger(CheckFavoritosUnitTest.class);

    @Mock
    private FavoritosDAO favoritosDAO;

    @Mock
    private MovieDAO movieDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private FavoritosServiceImpl favoritosService;

    private static UsuarioDTO usuarioDTO;

    private static MovieDTO movieDTO;

    @BeforeAll
    static void setUp() {
        LOG.info("setUp");

        usuarioDTO = new UsuarioDTO("test", "1234");
        usuarioDTO.setId(9876L);

        movieDTO = new MovieDTO("906126","La sociedad de la nieve"
                , "La sociedad de la nieve", "/9tkJPQb4X4VoU3S5nqLDohZijPj.jpg"
                , "El 13 de octubre de 1972, el vuelo 571 de la Fuerza Aérea Uruguaya, fl…", "2023-12-13"
                , 1284.858, 467, 8.158, new ArrayList<>(), "es"
                , new ArrayList<>(), new ArrayList<>(), 0, 0D);

        List<FavoritosDTO> favoritosDTOList = new ArrayList<>();
        favoritosDTOList.add(new FavoritosDTO(usuarioDTO.getId(), movieDTO.getId()));

        usuarioDTO.setFavoritos(favoritosDTOList);
    }

    @Test
    void findUserFavs() {
        LOG.info("findUserFavs");

        Movie movie = MovieMapper.toEntity(movieDTO);
        Pageable pageable = PageRequest.of(0, 21);
        Page<Favoritos> favoritosPage = new PageImpl<>(Arrays.asList(
                new Favoritos(new FavoritosId(new Usuario(usuarioDTO.getId()), movie))));

        Mockito.when(favoritosDAO.findByIdUsuarioId(9876L, pageable)).thenReturn(favoritosPage);

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(UserMapper.toEntity(usuarioDTO));

        // Mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Setear el contexto de seguridad
        SecurityContextHolder.setContext(securityContext);

        Paginator<MovieDTO> paginator = favoritosService.getUsuarioFavs(1);
        assertTrue(paginator != null);
        assertTrue(paginator.getResults().size() == 1);
        assertEquals("906126", paginator.getResults().get(0).getId());
    }

    @Test
    void addFavoritosByUserId() {
        LOG.info("addFavoritosByUserId");

        MovieDTO movieDTO = new MovieDTO("572802", "Aquaman and the Lost Kingdom", "Aquaman y el reino perdido"
                , "/d9Hv3b37ZErby79f4iqTZ8doaTp.jpg"
                , "Al no poder derrotar a Aquaman la primera vez, Black Manta, todavía impulsado por la necesidad de vengar " +
                "la muerte de su padre, no se detendrá ante nada para derrotar a Aquaman de una vez por todas. " +
                "Esta vez Black Manta es más formidable que nunca y ejerce el poder del mítico Tridente Negro, que desata " +
                "una fuerza antigua y malévola.  Para derrotarlo, Aquaman recurrirá a su hermano encarcelado Orm, " +
                "el ex rey de la Atlántida, para forjar una alianza improbable. Juntos, deben dejar de lado sus diferencias" +
                " para proteger su reino y salvar a la familia de Aquaman, y al mundo, de una destrucción irreversible."
                , "2023-12-20",1112.367,449, 6.482, new ArrayList<>(), "en"
                , new ArrayList<>(), new ArrayList<>(), 0, 0D);
        Usuario usuario = UserMapper.toEntity(usuarioDTO);
        Movie movie = MovieMapper.toEntity(movieDTO);

        Mockito.when(movieDAO.findById("572802")).thenReturn(Optional.of(movie));
        Mockito.when(usuarioDAO.findById(9876L)).thenReturn(Optional.of(usuario));

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(usuario);

        // Mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Setear el contexto de seguridad
        SecurityContextHolder.setContext(securityContext);

        MovieDTO movieDTO1 = favoritosService.addFavoritos(movieDTO);

        assertEquals("572802", movieDTO1.getId());

    }

    @Test
    void deleteUserFavs() {
        LOG.info("deleteUserFavs");
        Usuario usuario = UserMapper.toEntity(usuarioDTO);
        Movie movie = MovieMapper.toEntity(movieDTO);

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(usuario);

        // Mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Setear el contexto de seguridad
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(movieDAO.findById("906126")).thenReturn(Optional.of(movie));
        Mockito.when(usuarioDAO.findById(9876L)).thenReturn(Optional.of(usuario));
        
        favoritosService.deleteFavoritos(movieDTO.getId());

        assertTrue(usuario.getFavoritos().isEmpty());
    }
}
