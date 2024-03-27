select * from Chat;
select * from Usuarios;
select * from Temas;
select * from publicaciones where idPublicacion > 1000;
select * from Likes;



select * from usuarios where (2024 - anioNacimiento) < 18;

select count(*) from publicaciones where idPubliRefer is null;



DROP PROCEDURE IF EXISTS _tmp_update_numLikes;
DELIMITER $$
CREATE PROCEDURE _tmp_update_numLikes()
BEGIN
   DECLARE cursor_List_isdone BOOLEAN DEFAULT FALSE;
   DECLARE cur_numLikes, id INT;

   DECLARE cursor_List CURSOR FOR 
      select count(l.idPublicacion) 
      from Likes l right join Publicaciones p 
      on p.idPublicacion = l.idPublicacion 
      group by p.idPublicacion;

   DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_List_isdone = TRUE;

   OPEN cursor_List;
	SET id = 0;
   loop_List: LOOP
      FETCH cursor_List INTO cur_numLikes;
      IF cursor_List_isdone THEN
         LEAVE loop_List;
      END IF;
		SET id = id+1;
		update publicaciones set numLikes = cur_numLikes where idPublicacion = id;

   END LOOP loop_List;

   CLOSE cursor_List;
END $$
DELIMITER ;
CALL _tmp_update_numLikes();



select p.*, count(l.idPublicacion) as "numberLikes" 
	from publicaciones p join likes l on p.idPublicacion = l.idPublicacion 
		where p.numLikes != "numberLikes"
			group by l.idPublicacion 
				order by l.idPublicacion;
                



#Las fechas de comentarios es previa a las de las publicaciones    
Select p1.fecha as "FechaComent", p2.fecha as "fechaPublicacion" from publicaciones p1 join publicaciones p2 on p1.idPublicacion = p2.idPubliRefer where p1.fecha < p2.fecha;
    
    
    
#Corregir el caso en el que la fecha de comentario es previa a la de la publicacion    
UPDATE publicaciones AS p1
	JOIN publicaciones AS p2 ON p1.idPublicacion = p2.idPubliRefer
		SET p1.fecha = p2.fecha
			WHERE p1.fecha < p2.fecha;
    
    
  
#Comprobar que ningun menor de edad vea contenido inapropiado
select us.idUsuario, te.idTema, te.Titulo from usuarios us 
	Join usuario_tema ut on ut.idUsuario = us.idUsuario 
		join temas te on te.idTema = ut.idTema where te.edadMinima = 18 and (2024 - anioNacimiento < 18);
        
        
        
#Borrar los registros de users menores de edad viendo contenido inapropiado
delete from usuario_tema 
	where (idUsuario, idTema) in (
		select us.idUsuario, te.idTema from usuarios us 
			Join usuario_tema ut on ut.idUsuario = us.idUsuario 
				join temas te on te.idTema = ut.idTema where te.edadMinima = 18 and (2024 - anioNacimiento < 18));




#Conversacion de una persona con otra
select * from Chat where (idUsuarioOrigen = 1 && IdUsuarioDestino = 25) || (IdUsuarioDestino = 1 && idUsuarioOrigen = 25) order by fecha;



#Encontrar chats de una persona
select  distinct(IdUsuarioDestino) as "recibido", idUsuarioOrigen as "mandado" 
	from Chat where idUsuarioOrigen = 1 
union
select  distinct(idUsuarioOrigen) as "recibido", IdUsuarioDestino as "mandado" 
	from Chat where IdUsuarioDestino = 1;
    
/*select * from Chat a join Chat b
	on a.idUsuarioOrigen = b.IdUsuarioDestino and a.IdUsuarioDestino = b.idUsuarioOrigen 
		where a.idUsuarioOrigen = 1 or a.IdUsuarioDestino = 1;*/

/*Quitar tuitulo a comentarios*/
update publicaciones set titulo = null where idPubliRefer is not null;






select g.*, u.Us_Nombre from grupo_usuario g join usuarios u on u.idUsuario = g.idUsuario
where g.idGrupo in (select idGrupo from grupo_usuario group by idGrupo having count(idGrupo) = 2)
order by g.idGrupo asc;



select idGrupo from grupo_usuario group by idGrupo having count(idGrupo) = 2;

update grupos set codigo = null where idGrupo in (select idGrupo from grupo_usuario group by idGrupo having count(idGrupo) = 2);
update grupos set foto = null;



WITH UpdateData AS (
    SELECT g1.idGrupoUsuario AS idToUpdate1,
           (
               SELECT u2.Us_Nombre
               FROM grupo_usuario AS g2
                        JOIN usuarios AS u2 ON g2.idUsuario = u2.idUsuario
               WHERE g2.idGrupo = subquery.idGrupo
               ORDER BY g2.idGrupoUsuario DESC
               LIMIT 1
           ) AS nuevoNombre1,
           (
               SELECT u2.Us_Nombre
               FROM grupo_usuario AS g2
                        JOIN usuarios AS u2 ON g2.idUsuario = u2.idUsuario
               WHERE g2.idGrupo = subquery.idGrupo
               ORDER BY g2.idGrupoUsuario asc
               LIMIT 1
           ) AS nuevoNombre2
    FROM grupo_usuario AS g1
             JOIN usuarios AS u1 ON g1.idUsuario = u1.idUsuario
             JOIN (
        SELECT idGrupo
        FROM grupo_usuario
        WHERE nombre = 'Nombre Grupo'
        GROUP BY idGrupo
        HAVING COUNT(idGrupo) = 2
        LIMIT 1
    ) AS subquery ON g1.idGrupo = subquery.idGrupo
             JOIN grupo_usuario AS g3 ON g3.idGrupo = subquery.idGrupo
    ORDER BY g3.idGrupoUsuario ASC
    LIMIT 1
)
UPDATE grupo_usuario
set nombre = 
	case idGrupoUsuario
    when (SELECT idToUpdate1 FROM UpdateData) then (SELECT nuevoNombre1 FROM UpdateData)
    when (SELECT idToUpdate1 FROM UpdateData)+1 then (SELECT nuevoNombre2 FROM UpdateData)
    else nombre
end;









