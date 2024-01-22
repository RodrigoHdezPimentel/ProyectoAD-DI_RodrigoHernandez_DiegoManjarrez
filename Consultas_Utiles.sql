select * from Chat where idUsuarioDestino = IdUsuarioOrigen;


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

