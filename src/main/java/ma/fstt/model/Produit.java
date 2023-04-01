package ma.fstt.model;

public class Produit {
    private long id_Produit;
    private String nom;
    private float prix;
    private String description;

    public Produit(){

    }

    public Produit(long id_Produit,String nom, float prix, String description) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.id_Produit=id_Produit;
    }

    public long getId_Produit() {
        return id_Produit;
    }

    public void setId_Produit(long id_Produit) {
        this.id_Produit = id_Produit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_Produit=" + id_Produit +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                '}';
    }
}
