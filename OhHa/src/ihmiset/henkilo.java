
package ihmiset;


public class Henkilo {

    private int x;
    private int y;
    private boolean onkoSankari;
    private String ulkomuoto;
    
    public Henkilo(int x, int y, boolean sankaruus, String ulkomuoto) {
        this.x = x;
        this.y = y;
        this.onkoSankari = sankaruus;
        this.ulkomuoto = ulkomuoto;
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
    
    public boolean onkoSankari() {
        return this.onkoSankari;
    }
    
    public String getUlkomuoto() {
        return this.ulkomuoto;
    }
    
    public void asetaSijainti(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Henkilo other = (Henkilo) obj;
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