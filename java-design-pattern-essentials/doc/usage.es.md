[Página de inicio de Plantilla HTML5](https://html5boilerplate.com/) | [Índice de la documentación](TOC.es.md)

# Uso

El uso más básico de la Plantilla HTML5 es crear un sitio estático o una aplicación simple. Una vez que haya descargado
o clonado el proyecto, el proceso se verá así:

1. Establece la estructura básica del sitio.
2. Agrega algún contenido, estilo, y funcionalidad.
3. Ejecuta tu sitio localmente para ver cómo resulta.
4. Implementa tu sitio.

Genial, ¿verdad? _Lo es_ Dicho esto, los valores predeterminados inteligentes, los elementos de referencia, los valores
de atributos predeterminados y varias otras utilidades que ofrece la Plantilla HTML5 pueden servir como base para
cualquier cosa que esté interesado en construir.

Incluso el caso de uso básico de un sitio estático simple se puede mejorar mediante la manipulación del código a través
de un proceso de compilación automatizado. Avanzando en complejidad, la Plantilla HTML5 se puede integrar con cualquier
marco front-end, CMS o plataforma de comercio electrónico en el que esté trabajando. Mezcla y combina según tus gustos.
Usa lo que necesites (tíralo en una licuadora si es necesario) y desecha el resto. La Plantilla HTML5 es un punto de
partida, no un destino.

## Estructura básica

Un sitio básico de Plantilla HTML5 inicialmente se parece a esto:

```
.
├── css
│   ├── main.css
│   └── normalize.css
├── doc
├── img
├── js
│   ├── main.js
│   ├── plugins.js
│   └── vendor
│       └── modernizr.min.js
├── .editorconfig
├── .htaccess
├── 404.html
├── browserconfig.xml
├── favicon.ico
├── humans.txt
├── icon.png
├── index.html
├── package.json
├── robots.txt
├── site.webmanifest
├── tile.png
└── tile-wide.png
```

Lo que sigue es una descripción general de cada parte principal y cómo usarlas.

### css

Este directorio debe contener todos los archivos CSS de su proyecto e incluye
algunos CSS iniciales para ayudarte a comenzar desde una base sólida. [Acerca del CSS](css.es.md).

### doc

Este directorio contiene toda la documentación estándar de HTML 5. Puedes usarlo
como ubicación y base para la documentación de tu propio proyecto.

### js

Este directorio debe contener todos los archivos JS de tu proyecto. Aquí se pueden incluir bibliotecas, complementos
y código personalizado. Incluye algunos JS iniciales para ayudarte a comenzar. [Acerca de JavaScript](js.es.md).

### .htaccess

Las configuraciones predeterminadas del servidor web son para Apache. Para obtener más información,
consulta el
[Repositorio de Configuraciones del Servidor Apache](https://github.com/h5bp/server-configs/blob/master/README.md).

¿Alojas tu sitio en un servidor que no sea Apache? Es probable que encuentres el proyecto de configuración del servidor
correspondiente en nuestro repositorio
[Configuraciones del servidor](https://github.com/h5bp/server-configs/blob/master/README.md).

### 404.html

Una página útil 404 personalizada para comenzar.

### browserconfig.xml

Este archivo contiene todas las configuraciones relacionadas con mosaicos personalizados para IE11 y Edge.

Para obtener más información sobre este tema, consulta
[Microsoft's Docs](https://docs.microsoft.com/en-us/previous-versions/windows/internet-explorer/ie-developer/platform-apis/dn320426(v=vs.85)).

### .editorconfig

El archivo `.editorconfig` se proporciona para alentarte y ayudarte a ti y a tu equipo a mantener estilos de
codificación consistentes entre diferentes editores y EIDs.

[Lea más sobre el archivo `.editorconfig`](misc.md#editorconfig).

### index.html

Este es el esqueleto HTML predeterminado que debe formar la base de todas las páginas de tu sitio. Si estás utilizando
un marco de trabajo de plantillas del lado del servidor, deberás integrar este HTML inicial con su configuración.

Asegúrate de actualizar las URL para el CSS y el JavaScript a los que se hace referencia si modificas la estructura del
directorio.

Si estás utilizando Google Universal Analytics, asegúrate de editar el fragmento correspondiente en la parte inferior
para incluir tu ID de análisis.

### humans.txt

Edita este archivo para incluir el equipo que trabajó en su sitio/aplicación, y la
tecnología que lo impulsa.


### package.json

Edita este archivo para describir tu aplicación, agrega dependencias, scripts y
otras propiedades relacionadas con el desarrollo basado en node y el registro npm.


### robots.txt

Edita este archivo para incluir cualquier página que necesites ocultar de los motores de búsqueda.


### Iconos

Reemplaza los iconos `favicon.ico`, `tile.png`, `tile-wide.png` y el icono Apple Touch predeterminados con los tuyos
propios.

Si quieres usar diferentes iconos Apple Touch para diferentes resoluciones consulte la
[documentación correspondiente](extend.md#apple-touch-icons).
