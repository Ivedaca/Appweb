package com.AppWeb.AppWeb.controllers;

import com.AppWeb.AppWeb.dao.UsuarioDao;
import com.AppWeb.AppWeb.models.Usuario;
import com.AppWeb.AppWeb.utils.JwtUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JwtUtil jwtUtil;


    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token) {

        if (!validarToken(token)) {
            return null;
        }
        return usuarioDao.getUsuarios();

    }

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
         return usuarioId != null;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario) {

       Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);  // Hash
       String hash = argon2.hash(1, 1024, 1, usuario.getContrasenha());
       usuario.setContrasenha(hash);

       usuarioDao.registrar(usuario);

    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token,
                         @PathVariable int id){
        if (!validarToken(token)) {
            return;
        }
       usuarioDao.eliminar(id);
    }


}
