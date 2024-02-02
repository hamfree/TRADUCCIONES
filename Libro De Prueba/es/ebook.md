# Generar libros electrónicos y archivos PDF

HonKit puede generar un sitio web, pero también puede generar contenido como libro electrónico (ePub, Mobi, PDF).

```bash
# Generar un archivo PDF
$ honkit pdf ./ ./mybook.pdf

# Generar un archivo ePub
$ honkit epub ./ ./mybook.epub

# Generar un archivo Mobi
$ honkit mobi ./ ./mybook.mobi
```

## Instalación de ebook-convert

Se requiere `ebook-convert` para generar libros electrónicos (epub, mobi, pdf).

### OS X

Descargue la [aplicación Calibre](https://calibre-ebook.com/download). Después de mover `calibre.app` a su carpeta de Aplicaciones, cree un enlace simbólico a la herramienta de conversión de libros electrónicos:

```bash
sudo ln -s /Applications/calibre.app/Contents/MacOS/ebook-convert /usr/local/bin
```

Puede reemplazar `/usr/bin` con cualquier directorio que esté en su $PATH.

### Portadas

Las portadas se utilizan para todos los formatos de libros electrónicos.

Para proporcionar una portada, coloque un archivo **`cover.jpg`** en el directorio raíz de su libro. Agregar un **`cover_small.jpg`** especificará una versión más pequeña de la portada. La portada debe ser un archivo **JPEG**.

Una buena portada debe respetar las siguientes pautas:

* Tamaño de 1800x2360 píxeles para `cover.jpg`, 200x262 para `cover_small.jpg`
* Sin bordes
* Título del libro claramente visible.
* Cualquier texto importante debe ser visible en la versión pequeña.
