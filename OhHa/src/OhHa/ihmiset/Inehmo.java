package OhHa.ihmiset;

public class Inehmo {

    public int x;
    public int y;
    public String ulkomuoto;
    public String tyyppi;
    public boolean liikkuvuus;
    

    public Inehmo(int x, int y, String ulkomuoto, String tyyppi, boolean liikkuvuus) {
        this.x = x;
        this.y = y;
        this.ulkomuoto = ulkomuoto;
        this.tyyppi = tyyppi;
        this.liikkuvuus = liikkuvuus;
    }
    
    public void liiku(String suunta) {
        if (liikkuvuus) {
            
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public boolean getSankaruus() {
        if (this.tyyppi.equals("sankari")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean getLiikkuva() {
        return this.liikkuvuus;
    }
    
    public String getTyyppi() {
        return this.tyyppi;
    }
    
    public String getUlkomuoto() {
        return this.ulkomuoto;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setSijainti(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setUlkomuoto(String ulkomuoto) {
        this.ulkomuoto = ulkomuoto;
    }
    
    public void setLiikkuva(boolean liikkuva) {
        this.liikkuvuus = liikkuva;
    }
    
    public void setTyyppi(String tyyppi) {
        this.tyyppi = tyyppi;
    }
    
    
    // tarkastamatta
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (obj.getClass() == Asiakas.class) {
            return false;
        }
        
        if (obj.getClass() == Sankari.class) {
            return false;
        }
        
        if (obj.getClass() == Portsari.class) {
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
    
    //tarkastamatta
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }
}
