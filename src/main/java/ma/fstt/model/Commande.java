package ma.fstt.model;
import java.time.LocalDate;
import java.sql.*;
import java.util.List;

public class Commande {
    private long id_commande;
    private String client;
    private String nomLivreur;
    private String lesProduits="";
    private float prixTotal;
    private float distance;
    private LocalDate dateLivraison;
    private int Qte;
    private int panier;

    public int getQte() {
        return Qte;
    }

    public void setQte(int qte) {
        Qte = qte;
    }

    public long getId_commande() {
        return id_commande;
    }

    public void setId_commande(long id_commande) {
        this.id_commande = id_commande;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getNomLivreur() {
        return nomLivreur;
    }
    public void LesStringProduits(ResultSet resultSet) throws SQLException {

        if(resultSet.getInt(8)==1){
            lesProduits+=(resultSet.getString(4)+"\n");
        } else{
            lesProduits+=(resultSet.getString(8)+"x "+resultSet.getString(4)+"\n ");
        }
    }
    public Commande(){}

    public Commande(long id_commande, String client, String nomLivreur, String lesProduits, float prixTotal, float distance, LocalDate dateLivraison, int Qte, int panier) {
        this.id_commande = id_commande;
        this.client = client;
        this.nomLivreur = nomLivreur;
        this.lesProduits = lesProduits;
        this.prixTotal = prixTotal;
        this.distance = distance;
        this.dateLivraison = dateLivraison;
        this.panier=panier;
        this.Qte=Qte;
    }

    public void setNomLivreur(String nomLivreur) {
        this.nomLivreur = nomLivreur;
    }

    public String getLesProduits() {
        return lesProduits;
    }

    public void setLesProduits(String lesProduits) {
        this.lesProduits = lesProduits;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }



    public int getPanier() {
        return panier;
    }

    public void setPanier(int panier) {
        this.panier = panier;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id_commande=" + id_commande +
                ", client='" + client + '\'' +
                ", nomLivreur='" + nomLivreur + '\'' +
                ", lesProduits='" + lesProduits + '\'' +
                ", prixTotal=" + prixTotal +
                ", distance=" + distance +
                ", dateLivraison=" + dateLivraison +
                ", Qte=" + Qte +
                ", panier=" + panier +
                '}';
    }
}
