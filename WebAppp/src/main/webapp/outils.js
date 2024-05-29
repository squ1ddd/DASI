
function remplirModalProfilIntervenant(donnees) {
    $('#photoIntervenant').attr('src', donnees.photo);
    $('#nomIntervenant').text(donnees.nom);
    $('#emailIntervenant').text(donnees.email);
    $('#niveauIntervenant').text(donnees.niveau);
    $('#codeEtablissementIntervenant').text(donnees.codeEtablissement);
    $('#nomEtablissementIntervenant').text(donnees.nomEtablissement);
}

function remplirModalProfilEtudiant(donnees) {
    $('#photoUtilisateur').attr('src', donnees.photo);
    $('#nomUtilisateur').text(donnees.nom);
    $('#emailUtilisateur').text(donnees.email);
    $('#niveauMinUtilisateur').text(donnees.niveauMin);
    $('#niveauMaxUtilisateur').text(donnees.niveauMax);
    $('#activiteUtilisateur').text(donnees.activite);
    $('#typeEtablissement').text(donnees.typeEtablissement);
}

function chargerModalProfil(isEtudiant, profilData, nomModal, buttonBalise) {
    var modalPath = isEtudiant ? 'modalProfilEtudiant.html' : 'modalProfilIntervenant.html';

    $(nomModal).load(modalPath, function () {
        if (isEtudiant) {
            remplirModalProfilEtudiant(profilData);
        } else {
            remplirModalProfilIntervenant(profilData);
        }
    });

    var lienProfil = $(buttonBalise);

    var nouveauDataTarget = isEtudiant ? '#profilEtudiantModal' : "#profilIntervenantModal";
    // Modifier la valeur de l'attribut data-target
    lienProfil.attr('data-target', nouveauDataTarget);
}

var seanceDataExemple = {
    date: "00h00 le 10/10/2024",
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    matiere: "Francais",
    nomIntervenant: "Nadine Hajj-Hassan",
    photoIntervenant: "https://via.placeholder.com/150",
    lienVisio: "/WebAppp/Appel.html"
};

var intervenantDataExemple  = {
    photo: "https://via.placeholder.com/150",
    nom: "Prénom Nom",
    email: "user@example.com",
    niveau: "Niveau 3",
    codeEtablissement: "123456",
    nomEtablissement: "Université Exemple"
};

var etudiantDataExemple  = {
        photo: "https://via.placeholder.com/150",
        nom: "Prénom Nom",
        email: "user@example.com",
        niveauMin: "Niveau 1",
        niveauMax: "Niveau 5",
        activite: "Étudiant",
        typeEtablissement: "Université"
    };