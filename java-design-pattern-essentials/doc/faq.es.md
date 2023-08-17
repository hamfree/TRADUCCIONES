[Página de inicio de Plantilla HTML5](https://html5boilerplate.com/) | [Índice de la documentación](TOC.md)

# Preguntas Frecuentes

* [¿Por qué el código de Google Analytics está en la parte inferior? Google recomienda colocarlo en `<head>`.](#why-is-the-google-analytics-code-at-the-bottom-google-recommends-it-be-placed-in-the-head)
* [¿Necesito actualizar mi sitio cada vez que se lanza una nueva versión de Plantilla HTML5?](#do-i-need-to-upgrade-my-site-each-time-a-new-version-of-html5-boilerplate-is-released)
* [¿Dónde puedo encontrar ayuda con las preguntas de soporte?](#where-can-i-get-help-with-support-questions)

---

## ¿Por qué el código de Google Analytics está en la parte inferior? Google recomienda colocarlo en `<head>`.

La ventaja principal de colocarlo en el `<head>` es que harás un seguimiento de la
`vista de página` del usuario incluso si abandona la página antes de que esta se haya cargado completamente.

Aquí hay una cita útil de [Mathias Bynens](https://mathiasbynens.be/notes/async-analytics-snippet#comment-50) sobre
nuestra elección de colocación.
>Debo señalar que es Google, no yo, quien recomienda colocar este script antes que todos los demás scripts en el
documento. La única ventaja real es recibir una llamada de vista de página si su página no se carga por completo (por
ejemplo, si el usuario cancela la carga, o cierra rápidamente la página, etc.). Personalmente, no contaría eso como
una vista de página, por lo que en realidad prefiero colocar esta secuencia de comandos en la parte inferior, después de
todas las demás secuencias de comandos. Esto mantiene todas las secuencias de comandos juntas y refuerza que las
secuencias de comandos en la parte inferior son el movimiento correcto (por lo general, concateno y minimizo todas mis
secuencias de comandos en un archivo .js, siendo el fragmento GA el sufijo).

## ¿Necesito actualizar mi sitio cada vez que se lanza una nueva versión de Plantilla HTML5?

No, del mismo modo que normalmente no se reemplazan los cimientos de una casa una vez construida. Sin embargo, no hay
nada que le impida intentar trabajar en los últimos cambios, pero tendrá que evaluar los costos/beneficios de hacerlo.

## ¿Dónde puedo encontrar ayuda con las preguntas de soporte?

Por favor pida ayuda en [StackOverflow](https://stackoverflow.com/questions/tagged/html5boilerplate).
