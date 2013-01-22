
package OhHa.ihmiset;


public class Asiakas extends Inehmo {

    public Asiakas(int x, int y, String ulkomuoto, String tyyppi, boolean liikkuva) {
        super(x, y, ulkomuoto, tyyppi, liikkuva);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inehmo other = (Inehmo) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }
    
}
