select * from Chat;
select * from Usuarios;
select * from Temas;

#Borrar los registros de users menores de edad viendo contenido inapropiado
delete from usuario_tema 
	where (idUsuario, idTema) in (
		select us.idUsuario, te.idTema from usuarios us 
			Join usuario_tema ut on ut.idUsuario = us.idUsuario 
				join temas te on te.idTema = ut.idTema where te.edadMinima = 18 and us.edad < 18);

#Comprobar que ningun menor de edad vea contenido inapropiado
select us.idUsuario, te.idTema from usuarios us 
	Join usuario_tema ut on ut.idUsuario = us.idUsuario 
		join temas te on te.idTema = ut.idTema where te.edadMinima = 18 and us.edad < 18;


select * from Chat where (idUsuarioOrigen = 1 && IdUsuarioDestino = 25) || (IdUsuarioDestino = 1 && idUsuarioOrigen = 25) order by fecha;


select * from Chat a join Chat b
	on a.idUsuarioOrigen = b.IdUsuarioDestino and a.IdUsuarioDestino = b.idUsuarioOrigen 
		where a.idUsuarioOrigen = 1 or a.IdUsuarioDestino = 1;

#Encontrar chats de una persona
select  distinct(IdUsuarioDestino) as "recibido", idUsuarioOrigen as "mandado" 
	from Chat where idUsuarioOrigen = 1 
union
select  distinct(idUsuarioOrigen) as "recibido", IdUsuarioDestino as "mandado" 
	from Chat where IdUsuarioDestino = 1;

