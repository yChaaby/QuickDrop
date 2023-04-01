package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends BaseDAO<Produit>{
    public ProduitDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Produit object) throws SQLException {
        String request = "insert into produit (nom , prix, description) values (? , ?, ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1 , object.getNom());

        this.preparedStatement.setFloat(2 , object.getPrix());
        this.preparedStatement.setString(3,object.getDescription());

        this.preparedStatement.execute();
    }

    @Override
    public void update(Produit object) throws SQLException {
        String request = "update produit set nom=? , prix=?, description=? where id_produit=?";

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1 , object.getNom());
        this.preparedStatement.setFloat(2 , object.getPrix());
        this.preparedStatement.setString(3,object.getDescription());
        this.preparedStatement.setLong(4,object.getId_Produit());
        System.out.println(this.preparedStatement);

        this.preparedStatement.execute();
    }

    @Override
    public void delete(Produit object) throws SQLException {
        String request = "delete from Commande where id_produit=?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1 , object.getId_Produit());
        this.preparedStatement.execute();
         request = "delete from produit where id_produit=?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1 , object.getId_Produit());
        if (!this.preparedStatement.execute()){
            System.out.println("bien supprimé");
        }else{
            System.out.println("error frere");
        }
    }

    @Override
    public List<Produit> getAll() throws SQLException {
        String request = "select * from produit where 1";
        this.statement=this.connection.createStatement();
        this.resultSet=this.statement.executeQuery(request);
        List<Produit> mylist = new ArrayList<Produit>();
        while(this.resultSet.next()){
            mylist.add(new Produit(this.resultSet.getLong(1),this.resultSet.getString(2),
                    this.resultSet.getFloat(3),this.resultSet.getString(4)));
        }
        return mylist;
    }

    @Override
    public Produit getOne(Long id) throws SQLException {
        String request = "select * from produit where id_produit=?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1 , id);
        // System.out.println(this.preparedStatement.toString());
        this.resultSet = this.preparedStatement.executeQuery();
        this.resultSet.next();
        return new Produit(this.resultSet.getLong(1),this.resultSet.getString(2),
                this.resultSet.getFloat(3),this.resultSet.getString(4));
    }
}
