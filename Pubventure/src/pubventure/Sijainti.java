
package pubventure;

/**
 * 
 * Sijainti-luokka tarjoaa olion, joka pitää kirjaa sijainnista ja tarjoaa
 * sen aksessorit.
 * 
 * Sille voidaan reitinhakua varten antaa myös tieto edellisestä sijainnista.
 */

public class Sijainti {
    
    private int x;
    private int y;
    
    public Sijainti(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    
    //RUKATTAVA!
    /**
     * Tarkistaa kahden Sijanti-olion samuuden luokan osalta.
     * @param object
     * @return palauttaa totuusarvon
     */
    @Override
    public boolean equals(Object object) {
        Sijainti sijainti = (Sijainti) object;
        
        if (object == null) {
            System.out.println("Sijainti-luokassa nullpointer");
            return false;
        } else if (this.getClass() != sijainti.getClass()) {
            System.out.println("Väärän tyyppisen luokan vertaileminen Sijainti-luokassa");
            return false;
        } else if (this.x == sijainti.getX() && this.y == sijainti.getY()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }
}
