# FOROEX
## ¿Qué es foroex?
Foroex pretende ser una app para la comunidad cuyo proposito es permitir a los usuarios hacer publicaciones sobre diferentes temas permitiendo ser respondidos por otros usuarios.
Tambien incluye chats entre dos usuarios.

### ¿Quienes son nuestros usuarios?
Esta aplicación va dirigida a edades desde los 16 hasta los 50 años debido a la cantidad de diferentes temas que hay en el foro.

## El Diseño
- ### Colores
    El color dominante de la aplicacion es el azul, con presencia de verdes. La decision frente a este color radica en la expresibidad de dicho color, que nos aporta entre otras sensaciones paz, tranquilidad, proteccion, creatividad.\
Sin embargo cada tema tendrá una paleta de colores personalizada para dicho contenido.\
Aquí algunas de paletas de colores de los para los 32 temas:
    - Tecnología: 
<span style="color:#212529">█</span>
<span style="color:#023e8a">█</span>
<span style="color:#864AF9">█</span>
<span style="color:#00b4d8">█</span>
    - Ciencia : 
<span style="color:#52b69a">█</span>
<span style="color:#1AACAC">█</span>
<span style="color:#1a759f">█</span>
<span style="color:#435585">█</span>
    - Programación : 
<span style="color:#212529">█</span>
<span style="color:#7D7C7C ">█</span>
<span style="color:#CCC8AA">█</span>
<span style="color:#ced4da ">█</span>
    - Desarrollo Web : 
<span style="color:#00b4d8">█</span>
<span style="color:#ACFADF ">█</span>
<span style="color:#CEE6F3">█</span>
<span style="color:#F4F2DE ">█</span>
    - Redes Sociales : 
<span style="color:#FFFFF">█</span>
<span style="color:#7E2553">█</span>
<span style="color:#FF004D">█</span>
<span style="color:#FAEF5D">█</span>
    - CiberSeguridad : 
<span style="color:#780000 ">█</span>
<span style="color:#FC6736 ">█</span>
<span style="color:#FFB0B0">█</span>
<span style="color:#003049">█</span>

- ### Fuente
    La fuente elegida es la "Roboto", una letra clara, sin decoracion y perfecta para ser leida, debido a que al ser una app que involucra mucho texto, es necesaria una letra poco serifada.
- ### Paradigma de Material
    Como he mencionado previamente, vamos a usar una paleta con dynamic color como la predeterminada, y luego para cada tema otra paleta diferente de dynamic color.

## Casos de uso
- ### Usuarios
    1. Los usuarios podrán registrarse.
    2. Los usuarios podrán elegir temas de preferencia.
    3. Los usuarios podrán hacer publicaciones sobre temas.
    4. Los usuarios podrán comentar publiaciones y otros comentarios.
    5. Los usuarios podrán dar "like" a publicaciones.
    6. Los usuarios podrán ver los los likes que han dado.
    7. Los usuarios podrán sus publicaciones en su perfil.
    8. Actualizar su perfil.
    9. Los usuarios podrán Chatear con otros usuarios.
- ### Publicaciones o comentarios
    1. Las publicaciones tendrán un tema asignado.
    2. Las publicaciones tendrán un numero de likes.
    3. Las publicaciones se posicionarán en funcion de los likes.
    4. Las publicaciones pueden ser eliminadas por su creador.
- ### Temas
    1. Cada usuario puede asignarse temas para que le sugiera publicaciones con ese tema.
    2. Un tema esta atribuido a cada publicacion.
 
## Activities
- La aplicacion inicia con una pagina de registro para usuarios donde ademas elegirán sus temas de preferencia.

![Activity Register](https://github.com/RodrigoHdezPimentel/ProyectoAD-DI_RodrigoHernandez_DiegoManjarrez/blob/PRD_DisenoPrototipado/imgs/RegisterActivity.png)


- Cuando te registras, te llevará a una activity de publicaciones, donde mediante un fragment, elegirás si ver las tendencias o sugerencias en funcion de los temas que elegistes.

![Activity Mian](https://github.com/RodrigoHdezPimentel/ProyectoAD-DI_RodrigoHernandez_DiegoManjarrez/blob/PRD_DisenoPrototipado/imgs/PublishesActivity.png)

- Al clicar una publicacion tendrás acceso a sus comentarios.

![Activity Coments](https://github.com/RodrigoHdezPimentel/ProyectoAD-DI_RodrigoHernandez_DiegoManjarrez/blob/PRD_DisenoPrototipado/imgs/ComentActivity.png)

- Cuando cliques en tu perfil, tendrás la opcion de modificarlo, ver los likes que has dado y tus publicaicones. 

![Activity Profile](https://github.com/RodrigoHdezPimentel/ProyectoAD-DI_RodrigoHernandez_DiegoManjarrez/blob/PRD_DisenoPrototipado/imgs/EditProfileActivity.png)\
![Activity edit Profile](https://github.com/RodrigoHdezPimentel/ProyectoAD-DI_RodrigoHernandez_DiegoManjarrez/blob/PRD_DisenoPrototipado/imgs/ProfileActivity.png)

- En la parte de publicar aparecerán los campos necesarios para realizar una publicacion.

![Activity Chat](https://github.com/RodrigoHdezPimentel/ProyectoAD-DI_RodrigoHernandez_DiegoManjarrez/blob/PRD_DisenoPrototipado/imgs/PublicarActivity.png)

- En el aprtado de chat se nos abrirá un listado de chats para hablar con otros usuarios.

![Activity ThemesActivity.png](https://github.com/RodrigoHdezPimentel/ProyectoAD-DI_RodrigoHernandez_DiegoManjarrez/blob/PRD_DisenoPrototipado/imgs/ChatActivity.png)
