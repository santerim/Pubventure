
package Pubventure;

/**
 * 
 * Sijainti-luokka tarjoaa olion, joka pitää kirjaa sijainnista ja tarjoaa
 * sen kysymis- ja asetusmetodit.
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
}
