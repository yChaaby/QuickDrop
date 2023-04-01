package ma.fstt.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandeDAO extends BaseDAO<Commande>{

    public CommandeDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Commande object) throws SQLException {
        String request = "insert into Commande (id_livreur, prix, date_livraison, id_produit, Distance, client, panier, Qte) values (?,?,?,?,?,?,?,?)";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery("select id_livreur from livreur where nom='"+object.getNomLivreur()+"'");
        this.resultSet.next();
        System.out.println(this.resultSet.getInt(1));
        this.statement = this.connection.createStatement();
        this.preparedStatement.setInt(1,this.resultSet.getInt(1));
        this.resultSet = this.statement.executeQuery("select id_produit,prix from produit where nom='"+object.getLesProduits()+"'");
        this.resultSet.next();
        this.preparedStatement.setInt(4,this.resultSet.getInt(1));
        this.preparedStatement.setFloat(2,this.resultSet.getInt(2));
        this.preparedStatement.setDate(3,java.sql.Date.valueOf(object.getDateLivraison()));
        this.preparedStatement.setFloat(5,object.getDistance());
        this.preparedStatement.setString(6,object.getClient());
        this.preparedStatement.setInt(8,object.getQte());
        this.preparedStatement.setInt(7,object.getPanier());
        this.preparedStatement.execute();

    }

    @Override
    public void update(Commande object) throws SQLException {

    }

    @Override
    public void delete(Commande object) throws SQLException {
        String requeste = "delete from Commande where panier =?";
        System.out.println(object.getPanier());
        this.preparedStatement = this.connection.prepareStatement(requeste);
        this.preparedStatement.setInt(1,object.getPanier());
        this.preparedStatement.execute();

    }
    public int setPanier(Commande cmd) throws SQLException {
        save(cmd);
        String requeste = "select id_commande from Commande where ABS(Commande.Distance-?) < 0.001 and Qte=? and client=? and date_livraison=?";
        this.preparedStatement = this.connection.prepareStatement(requeste);
        this.preparedStatement.setFloat(1,cmd.getDistance());
        this.preparedStatement.setInt(2,cmd.getQte());
        this.preparedStatement.setString(3,cmd.getClient());
        this.preparedStatement.setDate(4,java.sql.Date.valueOf(cmd.getDateLivraison()));
        this.resultSet = this.preparedStatement.executeQuery();
        this.resultSet.next();
        int panier = this.resultSet.getInt(1);
        System.out.println("le panier est :"+panier);
        requeste = "update Commande set panier=? where ABS(Commande.Distance-?) < 0.001 and Qte=? and client=? and date_livraison=?"; // update le panier recoit l'id
        this.preparedStatement = this.connection.prepareStatement(requeste);
        this.preparedStatement.setInt(1,panier);
        this.preparedStatement.setFloat(2,cmd.getDistance());
        this.preparedStatement.setInt(3,cmd.getQte());
        this.preparedStatement.setString(4,cmd.getClient());
        this.preparedStatement.setDate(5,java.sql.Date.valueOf(cmd.getDateLivraison()));
        this.preparedStatement.execute();


        return panier;
    }
    public List<Commande> createPanierListe(List<Commande> mylist ,List<Integer> monPanier) throws SQLException {
        List<Commande> myfinalList = new ArrayList<Commande>();
        for(int unPanier: monPanier) {
            Commande temp = getOne((long) unPanier);
            temp.setPanier(unPanier);
            myfinalList.add(temp);
        }
        for (Commande cmd:
             myfinalList) {
            System.out.println(cmd);

        }
        return myfinalList;
    }

    public ResultSet DataParLivreur() throws SQLException{
        String request="select livreur.nom , COUNT(*) from Commande,livreur,produit where  Commande.id_produit=produit.id_produit and livreur.id_livreur=Commande.id_livreur GROUP BY livreur.nom";
        this.statement=this.connection.createStatement();
        return this.statement.executeQuery(request);
    }
    public ResultSet DataParClient() throws SQLException{
        String request="select Commande.client , COUNT(*) from Commande,livreur,produit where Commande.id_produit=produit.id_produit and livreur.id_livreur=Commande.id_livreur GROUP BY Commande.client";
        this.statement=this.connection.createStatement();
        return this.statement.executeQuery(request);
    }

    @Override
    public List<Commande> getAll() throws SQLException {
        String request = "select Commande.id_commande,Commande.client,livreur.nom, produit.nom, Commande.prix, Commande.Distance, Commande.date_livraison ,Commande.Qte,Commande.panier from Commande,livreur,produit where  Commande.id_produit=produit.id_produit and livreur.id_livreur=Commande.id_livreur ";
        List<Commande> mylist = new ArrayList<Commande>();
        this.statement=this.connection.createStatement();
        this.resultSet=this.statement.executeQuery(request);
        while ( this.resultSet.next()){
            // mapping table objet
            mylist.add(new Commande(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) ,
                    this.resultSet.getString(3),
                    this.resultSet.getString(4),this.resultSet.getFloat(5),
                    this.resultSet.getFloat(6), this.resultSet.getDate(7).toLocalDate(),
                    this.resultSet.getInt(8),this.resultSet.getInt(9)));
        }
        request = "select DISTINCT panier from Commande where 1";
        List<Integer> monPanier = new ArrayList<Integer>();
        this.statement=this.connection.createStatement();
        this.resultSet=this.statement.executeQuery(request);
        while (this.resultSet.next()){
            monPanier.add(this.resultSet.getInt(1));
        }


        return createPanierListe(mylist,monPanier);
    }

    @Override
    public Commande getOne(Long id) throws SQLException {
        String request = "select Commande.id_commande,Commande.client,livreur.nom, produit.nom, produit.prix, Commande.Distance, Commande.date_livraison ,Commande.Qte from Commande,livreur,produit where  Commande.id_produit=produit.id_produit and livreur.id_livreur=Commande.id_livreur and panier = ?";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1 , id);
        // System.out.println(this.preparedStatement.toString()); un petit test
        this.resultSet = this.preparedStatement.executeQuery();
        String lesProduits = "";
        float Prixtotal=0;
        Commande cmd = new Commande();
        while(this.resultSet.next()){
                Prixtotal+=(this.resultSet.getFloat(5)*this.resultSet.getInt(8));
                cmd.LesStringProduits(this.resultSet);
                cmd.setClient(this.resultSet.getString(2));
                cmd.setId_commande(this.resultSet.getInt(1));
                cmd.setDateLivraison(this.resultSet.getDate(7).toLocalDate());
                cmd.setDistance(this.resultSet.getFloat(6));
                cmd.setNomLivreur(this.resultSet.getString(3));
        }
        cmd.setPrixTotal(Prixtotal);


        return cmd;
    }
}
