# Instalación y configuración de Honkit

Instalar HonKit y dejarlo listo solo debería llevar unos minutos.

## Instalación local

### Requisitos

Instalar HonKit es fácil y sencillo. Su sistema sólo necesita cumplir estos dos requisitos:

* NodeJS (Se recomienda v10.0.0 y superior)
* Windows, Linux, Unix, o Mac OS X

### Instalar con NPM

La mejor manera de instalar HonKit es a través de **NPM** o **Yarn**. En el símbolo del terminal, simplemente ejecute el siguiente comando para instalar HonKit:

```bash
$ npm install honkit --save-dev
# or
$ yarn add honkit --dev
```

⚠️ Advertencia:

* Si ha instalado `honkit` globalmente (`--global`) también debes instalar cada regla de complementos globalmente (`--global`)

* Si ha instalado `honkit` localmente, también debe instalar cada complemento localmente.

Recomendamos instalar `honkit` localmente.

### Crear un libro

HonKit puede configurar un libro con lo mínimo para empezar:

```bash
npx honkit init
```

Si desea crear el libro en un nuevo directorio, puede hacerlo ejecutando  
`honkit init ./directory`

Obtenga una vista previa y publique su libro usando:

```bash
npx honkit serve
```

O cree el sitio web estático usando:

```bash
npx honkit build
```

También puede definir los comandos `build` y `serve` en `package.json` como [npm-run-scripts](https://docs.npmjs.com/cli/run-script).

```json
  "scripts": {
    "build": "honkit build",
    "serve": "honkit serve"
  },
```

Después de esta configuración, puede usar el comando `npm run`.

```bash
# Build 
npm run build
# Start to server
npm run serve
```

### Depuración

Puede usar las opciones `--log=debug` y `--debug` para obtener mejores mensajes de error (con seguimiento de pila). Por ejemplo:

```bash
honkit build ./ --log=debug --debug
```
