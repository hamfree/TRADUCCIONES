# AsciiDoc

Desde la versión `2.0.0`, HonKit también puede aceptar AsciiDoc como formato de entrada.

Consulte la [Referencia rápida de sintaxis de AsciiDoc](<http://asciidoctor.org/docs/asciidoc-syntax-quick-reference/>) para obtener más información sobre el formato.

Al igual que para Markdown, HonKit utiliza algunos archivos especiales para extraer estructuras: `README.adoc`, `SUMMARY.adoc`, `LANGS.adoc` y `GLOSSARY.adoc`.

## README.adoc

Esta es la entrada principal de su libro: la introducción. Este archivo es **obligatorio**.

## SUMMARY.adoc

Este archivo define la lista de capítulos y subcapítulos. Al igual que en Markdown, el formato `SUMMARY.adoc` es simplemente una lista de enlaces, el nombre del enlace se utiliza como el nombre del capítulo y el destino es una ruta al archivo de ese capítulo.

Los subcapítulos se definen simplemente agregando una lista anidada a un capítulo principal.

```asciidoc
= Sumario

. link:capitulo-1/README.adoc[Capítulo 1]
.. link:capitulo-1/ARTICLE1.adoc[Artículo 1]
.. link:capitulo-1/ARTICLE2.adoc[Artículo 2]
... link:capitulo-1/ARTICLE-1-2-1.adoc[Artículo 1.2.1]
. link:capitulo-2/README.adoc[Capítulo 2]
. link:capitulo-3/README.adoc[Capítulo 3]
. link:capitulo-4/README.adoc[Capítulo 4]
.. Artículo inconcluso
. Capítulo inconcluso
```

## LANGS.adoc

Para libros [plurilingües] (./languages.md), este archivo se utiliza para definir los diferentes idiomas y traducciones admitidos.

Este archivo sigue la misma sintaxis que `SUMMARY.adoc`:

```asciidoc
= Idiomas

. link:en/[Inglés]
. link:fr/[Francés]
```

## GLOSSARY.adoc

Este archivo se utiliza para definir términos. [Ver la sección del glosario](../lexicon.md).

```asciidoc
= Glosario

== Magia

Tecnología suficientemente avanzada, más allá de la comprensión del observador que produce 
una sensación de asombro.

== PHP

Un lenguaje de programación web popular, utilizado por muchos sitios web grandes como Facebook. 
Rasmus Lerdorf creó originalmente PHP en 1994 para potenciar su página de inicio personal 
(PHP originalmente significaba "Página de inicio personal", pero ahora significa "PHP: 
preprocesador de hipertexto"). ```
