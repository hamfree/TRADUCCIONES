# Plurilingüe

HonKit admite la creación de libros escritos en varios idiomas. Cada idioma debe ser un subdirectorio que siga el formato normal de HonKit y un archivo llamado `LANGS.md` debe estar presente en la raíz del repositorio con el siguiente formato:

```markdown
# Idiomas

* [Inglés](en/)
* [Francés](fr/)
* [Español](es/)
```

## Configuración para cada idioma

Cuando un libro de idioma (por ejemplo, `en`) tiene un `book.json`, su configuración ampliará la configuración principal.

La única excepción son los complementos, los complementos se especifican globalmente y no se pueden especificar complementos específicos del idioma.
