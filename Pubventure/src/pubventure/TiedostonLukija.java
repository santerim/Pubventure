
package pubventure;

/**
 *
 * @author Santeri
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * TiedostonLukija-luokka hoitaa nimensä mukaisesti tiedoston lukemisen
 * kovakoodatusta kohteesta.
 */
public class TiedostonLukija {

    private String merkkijono = "";
    String sijainti = "resurssit/pubi.txt";

    public TiedostonLukija() {
    }

    /**
     * Lukee tiedoston annetusta sijainnista
     * 
     * @return palauttaa merkkijonon rivinvaihtoineen
     * @throws IOException mikäli tiedoston lukeminen epäonnistui
     */
    public String lueTiedosto() throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream is = this.getClass().getResourceAsStream(sijainti);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        
        if (is != null) {
            while ((merkkijono = buf.readLine()) != null) {
                sb.append(merkkijono).append("\n");
            }
        } else {
            System.out.println("InputSreamReader null");
        }
        return sb.toString();
    }
}
