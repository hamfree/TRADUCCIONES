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

The HonKit format supports right to left, and bi-directional writing. To enable it, you either need to specify a language (ex: `ar`), or force HonKit to use RTL in your `book.json`:

``` json
{
    "language": "ar",
    "direction": "rtl"
}
```

With version 3.0 of HonKit, it's automatically detected according to the content.
_Note that, while the output book will indeed respect RTL, the Editor doesn't support RTL writing yet_.

## ¿Debo utilizar extensiones `.html` o `.md` en mis enlaces?

You should always use paths and the `.md` extensions when linking to your files, HonKit will automatically replace these paths by the appropriate link when the pointing file is referenced in the Table of Contents.

## ¿Puedo crear un HonKit en un subdirectorio de mi repositorio?

Yes, HonKits can be created in [sub-directories](structure.md#subdirectory).  

## ¿HonKit admite idiomas RTL?

Yes, HonKit automatically detect the direction in your pages (`rtl` or `ltr`) and adjust the layout accordingly. The direction can also be specified globally in the [book.json](config.md).

---

## ¿HonKit admite ecuaciones matemáticas?

See following issues.

- [Mathjax plugin does not build · Issue #325 · honkit/honkit](https://github.com/honkit/honkit/issues/325)
- [KaTeX plugin · Issue #217 · honkit/honkit](https://github.com/honkit/honkit/issues/217)

## ¿Puedo personalizar/tematizar el resultado?

Yes, both the website and ebook outputs can be customized using [themes](themes/README.md).

## ¿Puedo agregar contenido interactivo (videos, etc)?

HonKit is very [extensible](plugins/README.md). You can use [existing plugins](https://plugins.honkit.com) or create your own!
