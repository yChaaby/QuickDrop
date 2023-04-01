create table produit
(
    id_produit  int auto_increment
        primary key,
    nom         varchar(100) null,
    prix        float        null,
    description text         null
);

INSERT INTO glovo.produit (id_produit, nom, prix, description) VALUES (137, 
'Produit1', 200, 'rien');
INSERT INTO glovo.produit (id_produit, nom, prix, description) VALUES (138, 
'Produit2', 210, '');

create table livreur
(
    id_livreur int auto_increment
        primary key,
    nom        varchar(255) null,
    telephone  varchar(255) null
);

INSERT INTO glovo.livreur (nom) VALUES ('Livreur1');

create table Commande
(
    id_commande    int auto_increment
        primary key,
    id_livreur     int   not null,
    prix           int   not null,
    date_livraison date  null,
    id_produit     int   not null,
    Distance       float null,
    client         text  not null,
    panier         int   not null,
    Qte            int   not null
);
ALTER table Commande 
add constraint id_livreur
        foreign key (id_livreur) references livreur (id_livreur),
add constraint id_produit
        foreign key (id_produit) references produit (id_produit);

INSERT INTO glovo.Commande (id_commande, id_livreur, prix, date_livraison, 
id_produit, Distance, client, panier, Qte) VALUES (38, 94, 200, '2023-04-08', 
137, 2, 'Client1', 38, 22);
INSERT INTO glovo.Commande (id_commande, id_livreur, prix, date_livraison, 
id_produit, Distance, client, panier, Qte) VALUES (39, 94, 200, '2023-04-02', 
137, 2, 'Client2', 39, 1);
INSERT INTO glovo.Commande (id_commande, id_livreur, prix, date_livraison, 
id_produit, Distance, client, panier, Qte) VALUES (42, 94, 210, '2023-04-13', 
138, 3, 'TEST2', 42, 1);
INSERT INTO glovo.Commande (id_commande, id_livreur, prix, date_livraison, 
id_produit, Distance, client, panier, Qte) VALUES (43, 94, 210, '2023-04-21', 
138, 3, 'TEST', 43, 3);

