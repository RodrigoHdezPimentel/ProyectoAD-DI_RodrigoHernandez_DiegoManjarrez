select * from Chat;
select * from Usuarios;
select * from Temas;
select * from publicaciones;
select * from Likes;




select count(*) from publicaciones where idPubliRefer is null;




#Las fechas de comentarios es previa a las de las publicaciones    
Select p1.fecha as "FechaComent", p2.fecha as "fechaPublicacion" from publicaciones p1 join publicaciones p2 on p1.idPublicacion = p2.idPubliRefer where p1.fecha < p2.fecha;
    
    
    
#Corregir el caso en el que la fecha de comentario es previa a la de la publicacion    
UPDATE publicaciones AS p1
	JOIN publicaciones AS p2 ON p1.idPublicacion = p2.idPubliRefer
		SET p1.fecha = p2.fecha
			WHERE p1.fecha < p2.fecha;
    
    
  
#Comprobar que ningun menor de edad vea contenido inapropiado
select us.idUsuario, te.idTema from usuarios us 
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

