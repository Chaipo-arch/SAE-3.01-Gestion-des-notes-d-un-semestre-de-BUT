public class VotreController {

    @FXML
    private void handleSelectFolderButtonClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            System.out.println("Dossier sélectionné : " + selectedDirectory.getAbsolutePath());
            // Vous pouvez faire quelque chose avec le dossier sélectionné
        }
    }
}