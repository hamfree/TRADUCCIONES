# LÉEME #

> Este LÉEME describe todos aquellos pasos que son necesarios para tener el "Tutorial de Oracle Java en español" funcionando.


### ¿Para qué es este repositorio? ###

* Sumario rápido:

> Es la traducción al español del Tutorial Oficial de Java. Es un trabajo en progreso.

  **Importante**:

> La versión oficial del Tutorial Oficial de Java es en **inglés**. Esta es una traducción particular de un desarrollador
> de java. Allá donde surjan dudas sobre la exactitud de un concepto, ejercicio, etc. por favor, use la versión en inglés
> del tutorial en la dirección web https://docs.oracle.com/javase/tutorial/ .
>
> La versión oficial de Oracle será **la válida**.

* Versión:

> La versión traducida al español es del fichero zip descargado de la web de Oracle correspondiente al día **3 de Marzo de 2015**
> correspondiente a la versión de **Java JDK 8u40**.

### ¿Cómo realizo la configuración? ###

* Sumario de la configuración:

1. Descargue el fichero zip desde la página <https://bitbucket.org/hamfree/tutorialjava/downloads>
2. Una vez descargado el fichero zip descomprímalo en una carpeta de su disco duro.
3. Inicie su navegador favorito y abra el fichero "(SuCarpetaDeDescarga)/TutorialJava/web/index.html".
4. Si quiere que funcionen bien los applets del tutorial deberá generar un WAR del proyecto Netbeans que contiene el
5. Tutorial de Oracle Java en Español y desplegarlo en su servidor de aplicaciones (Tomcat, Glassfish, Weblogic, etc.)

* Configuración:

1. En el caso de ejecución de los applets seguramente deberá permitir la ejecución de applets en el navegador. Para ello
    abra el panel de control de Java, diríjase a la pestaña de "Seguridad". Si no está activada la casilla "Activar el contenido
    de Java en el explorador" actívela y reinicie su navegador. También deberá elegir el nivel de seguridad de las
    aplicaciones "Alta" y después añadir a la lista de excepciones de sitios la URL "<http://localhost:8080"> si ésa es la URL en
    donde está sirviendo páginas su servidor de aplicaciones (normalmente es así).

* Dependencias:

1. Para poder ejecutar los ejemplos hace falta que tenga instalado el JDK de Java 8u40 o superior. En el caso de que quiera
   ver los applets en el navegador deberá tener instalado Mozilla Firefox o Internet Explorer ( Ni Chrome ni Chromium soportan
   actualmente el plugin de Java ), y además, si su sistema operativo es Windows,  tener instalada la versión de 32 bits de Java
   del JDK o JRE que puede descargar en el sitio de Oracle:

    <http://www.oracle.com/technetwork/java/javase/downloads/index.html>

2. También se recomienda instalar un Entorno Integrado de Desarrollo (IDE en inglés) como Netbeans o Eclipse, aunque no es
   estrictamente necesario y un buen editor de texto como Atom, SublimeText, Notepad++, PSPad, etc.

* Configuración de la Base de datos:

1. Este proyecto no requiere del uso de una base de datos.

* Cómo ejecutar las pruebas:

1. No aplicable

* Instrucciones de implementación
  
1. No aplicable

### Normas para contribuir ###

* Escribiendo pruebas
* Revisar código

### ¿A quién me dirijo para preguntar o contribuir? ###

* Vía correo electrónico a la dirección juanfco.ruiz AT gmail.com (sustituir AT por @)