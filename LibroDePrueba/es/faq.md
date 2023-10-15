# Preguntas Frecuentes de HonKit

Esta página recopila preguntas y respuestas comunes sobre el formato HonKit y la cadena de herramientas.

## ¿Cómo puedo alojar/publicar mi libro?

Publique en [Páginas de GitHub](https://pages.github.com/) o [Netlify](https://www.netlify.com/), y más.

## ¿No recarga los complementos?

HonKit usa caché de archivos por archivo de contenido de texto de forma predeterminada.

Este archivo caché verifica los cambios del archivo y lo recarga automáticamente.
En algunos casos, HonKit no puede detectar los cambios de complementos.

Si desea forzar el refresco, por favor use la opción `--reload`.

```bash
 honkit build --reload
```

## ¿HonKit admite texto RTL/bidireccional?

El formato HonKit admite escritura bidireccional y de derecha a izquierda. Para habilitarlo, debe especificar un idioma (por ejemplo, `ar`) o forzar a HonKit a usar RTL en su `book.json`:

``` json
{
    "language": "ar",
    "direction": "rtl"
}
```

Con la versión 3.0 de HonKit, se detecta automáticamente según el contenido.
_Tenga en cuenta que, si bien el libro resultante respetará RTL, el Editor aún no admite la escritura RTL_.

## ¿Debo utilizar extensiones `.html` o `.md` en mis enlaces?

Siempre debe usar rutas y las extensiones `.md` al vincular sus archivos; HonKit reemplazará automáticamente estas rutas por el enlace apropiado cuando se haga referencia al archivo señalador en la Tabla de contenido.

## ¿Puedo crear un HonKit en un subdirectorio de mi repositorio?

Sí, HonKits se puede crear en [subdirectorios](structure.md#subdirectory).

## ¿HonKit admite idiomas RTL?

Sí, HonKit detecta automáticamente la dirección en sus páginas (`rtl` o `ltr`) y ajusta el diseño en consecuencia. La dirección también se puede especificar globalmente en [book.json](config.md).

---

## ¿HonKit admite ecuaciones matemáticas?

Consulte los siguientes problemas.

- [El complemento Mathjax no se compila · Problema 325 · honkit/honkit](https://github.com/honkit/honkit/issues/325)
- [Complemento KaTeX · Problema 217 · honkit/honkit](https://github.com/honkit/honkit/issues/217)

## ¿Puedo personalizar/tematizar el resultado?

Sí, tanto el sitio web como los resultados de los libros electrónicos se pueden personalizar usando [temas](themes/README.md).

## ¿Puedo agregar contenido interactivo (videos, etc)?

HonKit es muy [extensible](plugins/README.md). ¡Puedes usar [complementos existentes](https://plugins.honkit.com) o crear los tuyos propios!
