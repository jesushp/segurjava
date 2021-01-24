package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.Usuario;

public interface DaoUsuarios extends JpaRepository<Usuario,String>{

		Usuario findByUsuarioAndPassword(String user, String pwd);
		Usuario findByUsuario(String user);

		@Query("Select u From Usuario u Where u.rol='ROLE_USER'")
		List<Usuario> listaUsuarios();
}