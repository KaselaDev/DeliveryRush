# Entorno — qué agrega la librería

El proyecto depende de `entorno.jar` (declarado en `.classpath`). Es un entorno de
desarrollo 2D para Java (Programación I - UNGS, versión 2.01) que trae todo el
andamiaje de ventana, bucle de juego, dibujo, teclado, mouse y sonido, para que
el juego sólo tenga que definir las reglas.

El jar incluye además los `.java` fuentes (`entorno/Entorno.java`,
`entorno/Board.java`, `entorno/Herramientas.java`, `entorno/InterfaceJuego.java`),
así que se puede leer el código de la librería desde el propio jar.

## Paquete `entorno`: 4 clases

| Clase | Rol |
|---|---|
| `Entorno` | La ventana y la API pública con la que trabaja el juego (dibujo, input, tiempo). |
| `InterfaceJuego` | Clase base con el método `tick()` que el juego debe sobreescribir. |
| `Board` | `JPanel` interno: bucle, render y captura de eventos. No se usa directamente. |
| `Herramientas` | Utilidades estáticas: imágenes, sonidos y conversión de ángulos. |

---

## 1. Ciclo de vida del juego

```java
Entorno entorno = new Entorno(this, "Título", 800, 600);  // crea la ventana
...
entorno.iniciar();                                         // arranca el bucle
```

- **`new Entorno(InterfaceJuego juego, String titulo, int ancho, int alto)`**
  Crea la ventana (`JFrame`) con un `Board` del tamaño pedido. Configura:
  cierre al terminar el proceso (`EXIT_ON_CLOSE`), centrada en pantalla,
  **no redimensionable**, fondo negro, doble buffer, foco para teclado, y visible
  de inmediato. Carga en `fontDisponibles` las familias de fuentes del sistema e
  imprime por consola el banner de versión.
- **`iniciar()`** Arranca un `javax.swing.Timer` de **10 ms** (~100 ticks por
  segundo). A partir de ahí, en cada repinta se llama a `juego.tick()` y se
  redibuja la pantalla. **Todo el juego vive dentro de `tick()`.**
- El render ocurre *después* de ejecutar `tick()`, dentro del mismo repintado:
  todo lo que se dibuja en `tick()` se ve en ese mismo cuadro. Las llamadas a
  dibujo **fuera** de `tick()` no hacen nada (el `Graphics2D` sólo existe
  durante el repintado y se anula al terminar).
- Si `tick()` lanza una excepción, se imprime el stack trace y el bucle sigue.

## 2. Dimensiones y tiempo

| Método | Qué devuelve |
|---|---|
| `ancho()` | Ancho en píxeles del área de dibujo. |
| `alto()` | Alto en píxeles del área de dibujo. |
| `numeroDeTick()` | Cantidad de ticks transcurridos desde `iniciar()`. |
| `tiempo()` | Milisegundos transcurridos desde `iniciar()`. |

Útiles para sincronizar sin depender de la máquina: `tick() * 10 ≈ tiempo()`.

## 3. Dibujo

Todos dibujan sobre el `Graphics2D` del `Board`.

| Método | Qué agrega |
|---|---|
| `dibujarImagen(Image, x, y, angulo)` | Imagen con centro de rotación en su centro geométrico. |
| `dibujarImagen(Image, x, y, angulo, escala)` | Igual, con factor de escala (1.0 = tamaño original). |
| `dibujarImagenConCentro(Image, x, y, centro_x, centro_y, angulo, escala)` | Elige qué punto de la imagen es el centro de rotación/escala. |
| `dibujarCirculo(x, y, diametro, color)` | Círculo relleno. `(x,y)` es el **centro**. |
| `dibujarRectangulo(x, y, ancho, alto, angulo, color)` | Rectángulo relleno rotado. `(x,y)` es el **centro**. |
| `dibujarTriangulo(x, y, altura, base, angulo, color)` | Triángulo relleno rotado; la punta apunta hacia **+x** (derecha). |
| `escribirTexto(texto, x, y)` | Texto en la posición dada. `(x,y)` es la **línea base, esquina izquierda**. |
| `cambiarFont(fuente, tamaño, color)` | Fija la fuente/color para los `escribirTexto` siguientes. |
| `cambiarFont(fuente, tamaño, color, tipo)` | Ídem con estilo (`NEGRITA`, `ITALICA`, `NORMAL`). |
| `colorFondo(Color)` | Cambia el color de fondo de la pantalla (por defecto negro). |

Convenciones importantes:

- **Los ángulos van en radianes.** Usar `Herramientas.radianes()` si se piensa en grados.
- `(x,y)` es siempre el **centro** de la figura (y de la imagen), no la esquina
  superior izquierda. Conviene dibujar las posiciones del juego como centros.
- En `dibujarTriangulo`, `altura` extiende el triángulo en **X** y `base` en **Y**
  (nombres confusos, pero es lo que hace el código).
- No hay API de textura de fondo: para un fondo de imagen se la dibuja primero
  cada tick con `dibujarImagen`.

## 4. Teclado

Constantes públicas de `Entorno` (códigos `KeyEvent`):

```
TECLA_ARRIBA=38   TECLA_ABAJO=40   TECLA_DERECHA=39   TECLA_IZQUIERDA=37
TECLA_ENTER=10    TECLA_ESPACIO=32 TECLA_CTRL=17      TECLA_ALT=18
TECLA_SHIFT=16    TECLA_INSERT=155 TECLA_DELETE=127   TECLA_INICIO=36
TECLA_FIN=35      TECLA_ESCAPE=27
```

Métodos:

| Método | Semántica |
|---|---|
| `estaPresionada(char tecla)` | La tecla está **mantenida** ahora mismo (true mientras se sostiene). |
| `sePresiono(char tecla)` | La tecla se **acaba de apretar** en este tick (un solo tick). |
| `seLevanto(char tecla)` | La tecla se **acaba de soltar** en este tick (un solo tick). |

Detalles:

- Las letras minúsculas se convierten a mayúsculas: `'a'` y `'A'` son la misma
  consulta. Para letras, alcanza con escribir la letra tal cual.
- `estaPresionada` sirve para movimiento continuo (mantener pulsado);
  `sePresiono`/`seLevanto` para acciones discretas (disparo, salto, menú).
- El índice debe estar en rango 0–255, si no lanza `RuntimeException` con mensaje
  de error.
- Las banderas "de momento" se limpian solas al terminar cada tick.

## 5. Mouse

Constantes: `BOTON_IZQUIERDO` (1), `BOTON_CENTRAL` (2), `BOTON_DERECHO` (3).

| Método | Semántica |
|---|---|
| `mouseX()` | Coordenada X del puntero dentro de la ventana. |
| `mouseY()` | Coordenada Y del puntero dentro de la ventana. |
| `mousePresente()` | `true` si el puntero está dentro de la ventana. |
| `estaPresionado(int boton)` | El botón está mantenido presionado. |
| `sePresionoBoton(int boton)` | El botón se apretó en este tick. |
| `seLevantoBoton(int boton)` | El botón se soltó en este tick. |

Misma semántica de "mantenido / recién apretado / recién soltado" que el
teclado. Las coordenadas se actualizan también al arrastrar (drag).

## 6. `InterfaceJuego`

Clase con un solo método vacío `public void tick()`. El juego la extiende:

```java
public class Juego extends InterfaceJuego {
    public void tick() { /* un instante de tiempo */ }
}
```

No hay que registrar el juego en ningún lado: el `Entorno` recibe la instancia
en el constructor y la llama en cada repintado.

## 7. `Herramientas` (utilidades estáticas)

| Método | Qué agrega |
|---|---|
| `radianes(double grados)` | Grados → radianes. |
| `grados(double radianes)` | Radianes → grados. |
| `cargarImagen(String archivo)` | Carga una `Image` desde el classpath. |
| `cargarSonido(String archivo)` | Devuelve un `Clip` de sonido listo para usar. |
| `play(String archivo)` | Reproduce el sonido **una vez**. |
| `loop(String archivo)` | Reproduce el sonido **en bucle infinito**. |

Ejemplo:

```java
Image fondo = Herramientas.cargarImagen("fondo.png");
entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2, 0);

Herramientas.loop("musica.wav");   // música de fondo
Herramientas.play("choque.wav");   // efecto puntual
```

Ojo con esto:

- `cargarImagen` y `cargarSonido` usan `ClassLoader.getSystemResource(...)`, o sea
  **buscan en el classpath**, no en una ruta libre. Los recursos deben estar en
  la raíz del proyecto o en `bin/` (donde Eclipse compila), y referenciarse sin
  barra inicial: `"imagen.png"`, `"subcarpeta/sonido.wav"`.
- Si el archivo no existe, `cargarImagen` devuelve una imagen inválida y
  `cargarSonido` devuelve `null` (y `play`/`loop` lanzan `NullPointerException`).
  Conviene verificar que los archivos estén bien ubicados.
- Los formatos soportados son los de la JVM: PNG, GIF, JPG; WAV para sonido.

## 8. Lo que el entorno **no** trae

- No hay físicas, colisiones ni detección de choques: eso se implementa en el
  juego (por ejemplo con `Rectangle.intersects` o distancias entre centros).
- No hay cámara ni desplazamiento del mundo: sólo hay una pantalla fija de
  `ancho() x alto()`.
- No se puede cambiar la velocidad del bucle (fija en 10 ms).
- No hay API de texto centrado ni medición de ancho de string; hay que
  posicionarlo manualmente (o usar `FontMetrics` desde el juego).
- No hay evento de cerrar la ventana con lógica propia: `EXIT_ON_CLOSE` mata el
  proceso.

## Esquema rápido de uso

```java
package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    private Entorno entorno;
    private Image fondo = Herramientas.cargarImagen("fondo.png");

    Juego() {
        this.entorno = new Entorno(this, "Delivery Rush", 800, 600);
        this.entorno.colorFondo(Color.DARK_GRAY);
        this.entorno.iniciar();
    }

    public void tick() {
        // 1. leer input
        if (entorno.sePresiono(entorno.TECLA_ESPACIO)) { /* acción única */ }
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) { /* movimiento */ }

        // 2. actualizar estado

        // 3. dibujar todo
        entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2, 0);
        entorno.dibujarCirculo(entorno.mouseX(), entorno.mouseY(), 20, Color.RED);
        entorno.cambiarFont("SansSerif", 20, Color.WHITE, entorno.NEGRITA);
        entorno.escribirTexto("Puntaje: 0", 10, 30);
    }

    public static void main(String[] args) { new Juego(); }
}
```