# Referencias de contenido

La referencia de contenido (conref) es un mecanismo conveniente para reutilizar contenido de otros archivos o libros.

## Importando archivos locales

Importar el contenido de otro archivo es fácil usando la etiqueta `include`:

```twig
{% include "./test.md" %}
```

## Importar archivo de otro libro

HonKit también puede resolver la ruta de inclusión usando git:

```twig
{% include "git+https://github.com/GitbookIO/documentation.git/README.md#0.0.1" %}
```

El formato de la URL de git es:

```twig
git+https://user@hostname/owner/project.git/file#commit-ish
```

La parte real de la URL de git debe terminar con `.git`, el nombre del archivo a importar se extrae después de `.git` hasta el fragmento de la URL.

El `commit-ish` puede ser cualquier etiqueta, sha o rama que pueda proporcionarse como argumento para `git checkout`. El valor predeterminado es "maestro".

## Herencia

La herencia de plantillas es una forma de facilitar la reutilización de plantillas. Al escribir una plantilla, puede definir "bloques" que las plantillas secundarias pueden anular. La cadena de herencia puede ser tan larga como quieras.

`block` define una sección en la plantilla y la identifica con un nombre. Las plantillas base pueden especificar bloques y las plantillas secundarias pueden anularlos con contenido nuevo.

```twig
{% extends "./mypage.md" %}

{% block pageContent %}
# Este es el contenido de mi página 
{% endblock %}
```

En el archivo `mypage.md`, debes especificar los bloques que se pueden ampliar:

```twig
{% block pageContent %}
Este es el contenido predeterminado.
{% endblock %}

# Licencia

{% include "./LICENSE" %}
```
